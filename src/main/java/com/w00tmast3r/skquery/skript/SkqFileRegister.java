package com.w00tmast3r.skquery.skript;

import ch.njol.skript.Skript;
import com.w00tmast3r.skquery.elements.effects.EffCustomEffect;
import com.w00tmast3r.skquery.elements.expressions.ExprCustomExpression;
import com.w00tmast3r.skquery.elements.expressions.ExprCustomPropertyExpression;
import com.w00tmast3r.skquery.util.Collect;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class SkqFileRegister {

    public static final Pattern COMMENT = Pattern.compile("--.*");

    public static final Pattern BL_TSTRUCT = Pattern.compile("define structure \"(.*)\"");
    public static final Pattern BL_END_TSTRUCT = Pattern.compile("end structure");

    public static final Pattern D_EFFECT = Pattern.compile("declare effect \"(.*)\"");
    public static final Pattern D_EXPRESSION = Pattern.compile("declare expression \"(.*)\"");
    public static final Pattern D_TYPED_EXPRESSION = Pattern.compile("declare expression \"(.*)\" as \"(.*)\"");
    public static final Pattern D_LEXPRESSION = Pattern.compile("declare loopable expression \"(.*)\"");
    public static final Pattern D_TYPED_LEXPRESSION = Pattern.compile("declare loopable expression \"(.*)\" as \"(.*)\"");

    public static final Pattern D_PROPERTY = Pattern.compile("declare property \"(.*)\" for \"(.*)\"");
    public static final Pattern D_TYPED_PROPERTY = Pattern.compile("declare property \"(.*)\" as \"(.*)\" for \"(.*)\"");
    public static final Pattern D_LPROPERTY = Pattern.compile("declare loopable property \"(.*)\" for \"(.*)\"");
    public static final Pattern D_TYPED_LPROPERTY = Pattern.compile("declare loopable property \"(.*)\" as \"(.*)\" for \"(.*)\"");

    public static final Pattern B_PROPERTY = Pattern.compile("bind property \"(.*)\"");
    public static final Pattern B_TYPED_PROPERTY = Pattern.compile("bind property \"(.*)\" as \"(.*)\"");
    public static final Pattern B_LPROPERTY = Pattern.compile("bind loopable property \"(.*)\"");
    public static final Pattern B_TYPED_LPROPERTY = Pattern.compile("bind loopable property \"(.*)\" as \"(.*)\"");

    public static void load() {
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".skq") && !name.startsWith("-");
            }
        };
        fileLoop:
        for (File f : Collect.getFiles(new File(Skript.getInstance().getDataFolder().getAbsolutePath() + File.separator + Skript.SCRIPTSFOLDER), filter)) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(f));
                String cTStruct = null;
                SerializableDefinitions definitions = new SerializableDefinitions();
                while (reader.ready()) {
                    String line = reader.readLine().trim();
                    if (line.isEmpty()) continue;
                    Matcher currentMatcher;
                    if (cTStruct != null) {
                        if (BL_END_TSTRUCT.matcher(line).matches()) {
                            cTStruct = null;
                        } else if ((currentMatcher = B_TYPED_PROPERTY.matcher(line)).matches()) {
                            definitions.bindProperty(cTStruct, currentMatcher.group(1), Class.forName(currentMatcher.group(2), false, SkqFileRegister.class.getClassLoader()), false);
                        } else if ((currentMatcher = B_PROPERTY.matcher(line)).matches()) {
                            definitions.bindProperty(cTStruct, currentMatcher.group(1), Object.class, false);
                        } else if ((currentMatcher = B_TYPED_LPROPERTY.matcher(line)).matches()) {
                            definitions.bindProperty(cTStruct, currentMatcher.group(1), Class.forName(currentMatcher.group(2), false, SkqFileRegister.class.getClassLoader()), true);
                        } else if ((currentMatcher = B_LPROPERTY.matcher(line)).matches()) {
                            definitions.bindProperty(cTStruct, currentMatcher.group(1), Object.class, true);
                        } else if (!COMMENT.matcher(line).matches()) {
                            Skript.error("Strange syntax error in line: " + line);
                            continue fileLoop;
                        }
                    } else {
                        if ((currentMatcher = BL_TSTRUCT.matcher(line)).matches()) {
                            cTStruct = currentMatcher.group(1);
                            definitions.declareStructure(cTStruct);
                        } else if ((currentMatcher = D_EFFECT.matcher(line)).matches()) {
                            definitions.declareEffect(currentMatcher.group(1));
                        } else if ((currentMatcher = D_TYPED_EXPRESSION.matcher(line)).matches()) {
                            definitions.declareExpression(currentMatcher.group(1), currentMatcher.group(2), false);
                        } else if ((currentMatcher = D_EXPRESSION.matcher(line)).matches()) {
                            definitions.declareExpression(currentMatcher.group(1), Object.class, false);
                        } else if ((currentMatcher = D_TYPED_LEXPRESSION.matcher(line)).matches()) {
                            definitions.declareExpression(currentMatcher.group(1), currentMatcher.group(2), true);
                        } else if ((currentMatcher = D_LEXPRESSION.matcher(line)).matches()) {
                            definitions.declareExpression(currentMatcher.group(1), Object.class, true);
                        } else if ((currentMatcher = D_TYPED_PROPERTY.matcher(line)).matches()) {
                            definitions.declareProperty(currentMatcher.group(3), currentMatcher.group(1), currentMatcher.group(2), false);
                        } else if ((currentMatcher = D_PROPERTY.matcher(line)).matches()) {
                            definitions.declareProperty(currentMatcher.group(2), currentMatcher.group(1), Object.class, false);
                        } else if ((currentMatcher = D_TYPED_LPROPERTY.matcher(line)).matches()) {
                            definitions.declareProperty(currentMatcher.group(3), currentMatcher.group(1), currentMatcher.group(2), true);
                        } else if ((currentMatcher = D_LPROPERTY.matcher(line)).matches()) {
                            definitions.declareProperty(currentMatcher.group(2), currentMatcher.group(1), Object.class, true);
                        } else if (!COMMENT.matcher(line).matches()) {
                            Skript.error("Strange syntax error in line: " + line);
                            continue fileLoop;
                        }
                    }
                }
                reader.close();
                if (!f.renameTo(new File(f.getParent() + File.separator + "-" + f.getName()))) {
                    Skript.warning("Failed to disable " + f.getName() + ". The file will still be be parsed when starting the server, even if it was not changed. Please insert a '-' before the file name to manually disable it.");
                }
                try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new GZIPOutputStream(new FileOutputStream(f.getParent() + File.separator + f.getName() + "c"))))) {
                    out.writeObject(definitions);
                    out.flush();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        readCompiledFiles();
        register();
    }

    private static void readCompiledFiles() {
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".skqc");
            }
        };
        for (File f : Collect.getFiles(new File(Skript.getInstance().getDataFolder().getAbsolutePath() + File.separator + Skript.SCRIPTSFOLDER), filter)) {
            try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new GZIPInputStream(new FileInputStream(f))))) {
                SerializableDefinitions definitions = (SerializableDefinitions) in.readObject();
                EffCustomEffect.addAll(definitions.getEffectList());
                ExprCustomExpression.addAll(definitions.getExpressionList().entrySet());
                ExprCustomPropertyExpression.addAll(definitions.getPropertyList().entrySet());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private static void register() {
        EffCustomEffect.register();
        ExprCustomExpression.register();
        ExprCustomPropertyExpression.register();
    }
}
