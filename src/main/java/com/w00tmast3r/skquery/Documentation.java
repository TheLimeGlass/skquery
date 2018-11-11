package com.w00tmast3r.skquery;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import com.w00tmast3r.skquery.api.*;
import com.w00tmast3r.skquery.util.Reflection;
import com.w00tmast3r.skquery.util.SkQueryInternalException;
import org.bukkit.event.Event;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Documentation {

    private static HashMap<Class<?>, String[]> events = new HashMap<>();
    private static Set<Class<?>> expressions = new HashSet<>();
    private static Set<Class<?>> conditions = new HashSet<>();
    private static Set<Class<?>> effects = new HashSet<>();

    public static void generateDocs() {
        if (Reflection.getCaller().getProtectionDomain().getCodeSource().getLocation().sameFile(Documentation.class.getProtectionDomain().getCodeSource().getLocation())) {
            try {
                generateDocsFor(conditions, "conditions");
                generateDocsFor(effects, "effects");
                generateDocsFor(expressions, "expressions");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new SkQueryInternalException("Documentation could not be generated externally.");
        }
    }

    private static void generateDocsFor(Set<Class<?>> classes, String filename) throws IOException {
        File html = new File(SkQuery.getInstance().getDataFolder().getAbsolutePath() + File.separator + filename + ".html");
        html.createNewFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(html))) {
            for (Class<?> e : classes) {
                if (e.isAnnotationPresent(DocumentationHidden.class)) continue;
                writer.write("<div class=\"column\">");
                writer.newLine();
                writer.write("<div id=\"" + e.getSimpleName() + "\" class=\"ui segment\">");
                writer.newLine();
                if (e.isAnnotationPresent(Deprecated.class)) {
                    writer.write("<span class=\"ui red ribbon label deprecated\">Deprecated</span><br/>");
                    writer.newLine();
                }
                writer.write("<h3 class=\"ui header\">" + (e.isAnnotationPresent(Name.class) ? e.getAnnotation(Name.class).value() : (Effect.class.isAssignableFrom(e) ? e.getSimpleName().substring(3) : e.getSimpleName().substring(4))) + "</h3>");
                writer.newLine();
                writer.write("<div class=\"ui two column grid\">");
                writer.newLine();
                writer.write("<div class=\"column\">");
                writer.newLine();
                writer.write("<p>");
                writer.newLine();
                if (e.isAnnotationPresent(Description.class)) {
                    writer.write(e.getAnnotation(Description.class).value().replaceAll("\\(([^\\(\\)]+)?\\(([^\\(\\)]+)\\)([^\\(\\)]+)\\)", "<a href=\"$1#$2\">$3</a>"));
                    writer.newLine();
                }
                writer.write("</p>");
                writer.newLine();
                writer.write("</div>");
                writer.newLine();
                writer.write("<div class=\"column\">");
                writer.newLine();
                writer.write("<div class=\"ui basic accordion\">");
                writer.newLine();
                writer.write("<div class=\"active title\">");
                writer.newLine();
                writer.write("<i class=\"dropdown icon\"></i>Patterns");
                writer.newLine();
                writer.write("</div>");
                writer.newLine();
                writer.write("<div class=\"active content\">");
                writer.newLine();
                writer.write("<code class=\"ui content inverted segment\">");
                writer.newLine();
                if (e.isAnnotationPresent(UsePropertyPatterns.class)) {
                    writer.write("%" + e.getAnnotation(PropertyFrom.class).value() + "%'s " + e.getAnnotation(PropertyTo.class).value() + "<br/>");
                    writer.write("[the] " + e.getAnnotation(PropertyTo.class).value() + " of %" + e.getAnnotation(PropertyFrom.class).value() + "%");
                } else {
                    for (String s : e.getAnnotation(Patterns.class).value()) {
                        writer.write("" + s + "<br/>");
                    }
                }
                writer.newLine();
                writer.write("</code>");
                writer.newLine();
                writer.write("</div>");
                writer.write("<div class=\"title\">");
                writer.newLine();
                writer.write("<i class=\"dropdown icon\"></i>Examples");
                writer.newLine();
                writer.write("</div>");
                writer.newLine();
                writer.write("<div class=\"content\">");
                writer.newLine();
                writer.write("<code class=\"ui content inverted segment\">");
                writer.newLine();
                if (e.isAnnotationPresent(Examples.class)) {
                    writer.write("<code>");
                    writer.newLine();
                    for (String s : e.getAnnotation(Examples.class).value()) {
                        writer.write(s.replace(";", "</br>\n") + "<br/>");
                    }
                    writer.newLine();
                    writer.write("</code>");
                } else {
                    writer.write("<span>&nbsp;</span>");
                }
                writer.newLine();
                writer.write("</code>");
                writer.newLine();
                writer.write("</div>");
                writer.newLine();
                writer.write("</div>");
                writer.newLine();
                writer.write("</div>");
                writer.newLine();
                writer.write("</div>");
                writer.newLine();
                writer.write("</div>");
                writer.newLine();
                writer.write("</div>");
                writer.newLine();
            }
            writer.newLine();
            for (Class<?> e : classes) {
                if (!e.isAnnotationPresent(DocumentationHidden.class)) {
                    writer.newLine();
                    writer.write("<a href=\"#" + e.getSimpleName() + "\" class=\"item\">" + (e.isAnnotationPresent(Name.class) ? e.getAnnotation(Name.class).value() : (e.getSimpleName().startsWith("Expr") ? e.getSimpleName().substring(4) : e.getSimpleName())) + "</a>");
                }
            }
        }
    }

    public static void addEvent(Class<? extends Event> event, String... patterns) {
        events.put(event, patterns);
    }

    public static void addCondition(Class<? extends Condition> condition) {
        conditions.add(condition);
    }

    public static void addEffect(Class<? extends Effect> effect) {
        effects.add(effect);
    }

    public static void addExpression(Class<? extends Expression<?>> expression) {
        expressions.add(expression);
    }

}