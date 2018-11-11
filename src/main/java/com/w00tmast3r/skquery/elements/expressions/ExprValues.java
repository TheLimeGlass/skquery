package com.w00tmast3r.skquery.elements.expressions;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.w00tmast3r.skquery.api.Examples;
import com.w00tmast3r.skquery.api.Patterns;
import com.w00tmast3r.skquery.util.Collect;
import org.bukkit.event.Event;


@Patterns("[all ]enum values of %classinfo%")
@Examples("message \"%all enum values of particle%\"")
public class ExprValues extends SimpleExpression<String> {

    @SuppressWarnings("rawtypes")
    Expression<ClassInfo> cInfo;

    @SuppressWarnings("rawtypes")
    @Override
    protected String[] get(Event event) {
        ClassInfo c = cInfo.getSingle(event);
        return c.getC().isEnum() ? Collect.toFriendlyStringArray(c.getC().getEnumConstants()) : null;
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "enum values";
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        cInfo = (Expression<ClassInfo>) expressions[0];
        return true;
    }
}
