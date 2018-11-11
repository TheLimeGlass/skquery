package com.w00tmast3r.skquery.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.registrations.Classes;
import ch.njol.util.Kleenean;
import com.w00tmast3r.skquery.api.Disabled;
import com.w00tmast3r.skquery.elements.events.lang.CustomExpressionEvent;
import com.w00tmast3r.skquery.util.BiValue;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Disabled
public class ExprCustomExpression extends SimpleExpression<Object> {

    private static final ArrayList<String> entries = new ArrayList<>();
    private static final HashMap<Integer, BiValue<String, BiValue<Object, Boolean>>> custom = new HashMap<>();
    private Expression<?>[] expressions;
    private String execute;
    private int matchedPattern;

    public static void addAll(Collection<Map.Entry<String, BiValue<Object, Boolean>>> effects) {
        for (Map.Entry<String, BiValue<Object, Boolean>> effect : effects) {
            if (!entries.contains(effect.getKey())) {
                entries.add(effect.getKey());
                custom.put(custom.size(), new BiValue<>(effect.getKey(), effect.getValue()));
            }
        }
    }

    public static ArrayList<String> getEntries() {
        return entries;
    }

    public static HashMap<Integer, BiValue<String, BiValue<Object, Boolean>>> getCustom() {
        return custom;
    }

    public static void register() {
        Skript.registerExpression(ExprCustomExpression.class, Object.class, ExpressionType.PROPERTY, entries.toArray(new String[entries.size()]));
    }

    @Override
    protected Object[] get(Event e) {
        CustomExpressionEvent expr = new CustomExpressionEvent(execute, expressions, getReturnType(), e);
        Bukkit.getPluginManager().callEvent(expr);
        return expr.getReturn();
    }

    @Override
    public boolean isSingle() {
        return !custom.get(matchedPattern).getSecond().getSecond();
    }

    @Override
    public Class<?> getReturnType() {
        BiValue<Object, Boolean> val = custom.get(matchedPattern).getSecond();
        if (val.getFirst() instanceof Class<?>) {
            return (Class<?>) val.getFirst();
        } else if (val.getFirst() instanceof String) {
            String codename = (String) val.getFirst();
            Class<?> c;
            if ((c = Classes.getClassFromUserInput(codename)) == null) c = Classes.getClass(codename);
            val.setFirst(c);
            return c;
        } else Skript.error("Invalid expression configuration");
        return null;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "custom expression";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.matchedPattern = matchedPattern;
        expressions = exprs;
        execute = custom.get(matchedPattern).getFirst();
        return true;
    }
}
