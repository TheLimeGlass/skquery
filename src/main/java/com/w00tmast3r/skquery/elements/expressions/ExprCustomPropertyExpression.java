package com.w00tmast3r.skquery.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.registrations.Classes;
import ch.njol.util.Kleenean;
import com.w00tmast3r.skquery.api.Disabled;
import com.w00tmast3r.skquery.elements.events.lang.CustomPropertyExpressionEvent;
import com.w00tmast3r.skquery.util.BiValue;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Disabled
public class ExprCustomPropertyExpression extends SimpleExpression<Object> {

    private static final ArrayList<String> register = new ArrayList<>();
    private static final ArrayList<BiValue<String, String>> entries = new ArrayList<>();
    private static final HashMap<Integer, BiValue<String, BiValue<Object, Boolean>>> custom = new HashMap<>();
    private Expression<?>[] expressions;
    private String execute;
    private BiValue<Integer, Pattern> matchedPattern;

    public static void addAll(Collection<Map.Entry<BiValue<String, String>, BiValue<Object, Boolean>>> effects) {
        for (Map.Entry<BiValue<String, String>, BiValue<Object, Boolean>> effect : effects) {
            if (!entries.contains(effect.getKey())) {
                register.add("[the] " + effect.getKey().getFirst() + " of %" + effect.getKey().getSecond() + "%");
                register.add("%" + effect.getKey().getSecond() + "%'[s] " + effect.getKey().getFirst());
                entries.add(effect.getKey());
                custom.put(custom.size(), new BiValue<>(effect.getKey().getFirst(), effect.getValue()));
            }
        }
    }

    public static ArrayList<BiValue<String, String>> getEntries() {
        return entries;
    }

    public static HashMap<Integer, BiValue<String, BiValue<Object, Boolean>>> getCustom() {
        return custom;
    }

    public static void register() {
        Skript.registerExpression(ExprCustomPropertyExpression.class, Object.class, ExpressionType.PROPERTY, register.toArray(new String[register.size()]));
    }

    private static BiValue<Integer, Pattern> getMeta(int matchedPattern) {
        if ((matchedPattern & 1) == 0) {
            return new BiValue<>(matchedPattern / 2, Pattern.TRIM_LAST);
        } else {
            return new BiValue<>((matchedPattern - 1) / 2, Pattern.TRIM_FIRST);
        }
    }

    @Override
    protected Object[] get(Event e) {
        CustomPropertyExpressionEvent expr = new CustomPropertyExpressionEvent(execute, expressions, getReturnType(), matchedPattern.getSecond(), e);
        Bukkit.getPluginManager().callEvent(expr);
        return expr.getReturn();
    }

    @Override
    public boolean isSingle() {
        return !custom.get(matchedPattern.getFirst()).getSecond().getSecond();
    }

    @Override
    public Class<?> getReturnType() {
        BiValue<Object, Boolean> val = custom.get(matchedPattern.getFirst()).getSecond();
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
        this.matchedPattern = getMeta(matchedPattern);
        expressions = exprs;
        execute = custom.get(this.matchedPattern.getFirst()).getFirst();
        return true;
    }

    public static enum Pattern {
        TRIM_LAST, TRIM_FIRST
    }
}
