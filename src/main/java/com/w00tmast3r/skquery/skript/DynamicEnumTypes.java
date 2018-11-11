package com.w00tmast3r.skquery.skript;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.classes.Serializer;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import ch.njol.yggdrasil.Fields;

import java.io.*;
import java.util.ArrayList;

public class DynamicEnumTypes {

    private static int current = 1;

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void register() {
        try {
            File dir = new File(Skript.getInstance().getDataFolder().getAbsolutePath() + File.separator + Skript.SCRIPTSFOLDER);
            assert dir.exists();
            for (File file : dir.listFiles()) {
                if (file != null && !file.isDirectory() && file.getName().substring(file.getName().lastIndexOf('.') + 1).equalsIgnoreCase("skt")) {
                    Class c = Class.forName("com.w00tmast3r.skquery.skript.DummyClasses$_" + current++);
                    ArrayList<String> patterns = new ArrayList<>();
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    String typeName = null;
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (typeName == null) {
                            typeName = line.toLowerCase();
                        } else {
                            patterns.add(line.toLowerCase());
                        }
                    }
                    reader.close();
                    add(c, typeName, patterns);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static <T extends DummyClasses.DummyBase> void add(final Class<T> c, final String typeName, final ArrayList<String> patterns) {
        Classes.registerClass(new ClassInfo<>(c, typeName)
                .user(typeName + "s?")
                .parser(new Parser<T>() {
                    @Override
                    public T parse(String s, ParseContext parseContext) {
                        if (s.startsWith(typeName + ":")) {
                            s = s.substring(typeName.length() + 1, s.length());
                        }
                        if (!patterns.contains(s.toLowerCase())) return null;
                        try {
                            T instance = c.newInstance();
                            instance.setValue(s.toLowerCase());
                            return instance;
                        } catch (InstantiationException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    public String toString(T e, int i) {
                        return e.toString();
                    }

                    @Override
                    public String toVariableNameString(T e) {
                        return typeName + ':' + e.getValue();
                    }

                    @Override
                    public String getVariableNamePattern() {
                        return typeName + ":.+";
                    }
                })
                .serializer(new Serializer<T>() {

                    @Override
                    public Fields serialize(T o) throws NotSerializableException {
                        Fields f = new Fields();
                        f.putObject("name", o.getValue());
                        return f;
                    }

                    @Override
                    public void deserialize(T o, Fields f) throws StreamCorruptedException, NotSerializableException {
                        assert false;
                    }

                    @Override
                    protected T deserialize(Fields fields) throws StreamCorruptedException, NotSerializableException {
                        try {
                            T instance = c.newInstance();
                            instance.setValue((String) fields.getObject("name"));
                            return instance;
                        } catch (InstantiationException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    public boolean mustSyncDeserialization() {
                        return true;
                    }

                    @Override
                    public boolean canBeInstantiated(Class<? extends T> c) {
                        return false;
                    }

                    @Override
                    protected boolean canBeInstantiated() {
                        return false;
                    }
                }));
    }
}
