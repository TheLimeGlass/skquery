package com.w00tmast3r.skquery.elements.expressions;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.w00tmast3r.skquery.api.Patterns;
import com.w00tmast3r.skquery.elements.events.lang.CustomPropertyExpressionEvent;
import com.w00tmast3r.skquery.util.Collect;
import org.bukkit.event.Event;

@Patterns("%*classinfo% origin expression")
public class ExprOriginExpression extends SimpleExpression<Object> {

    private Class<?> returnType = Object.class;

    protected Object[] get(Event e) {
        Object[] out = Collect.newArray(returnType, 1);
        Expression<?>[] args = ((CustomPropertyExpressionEvent) e).getArgs();
        switch (((CustomPropertyExpressionEvent) e).getPattern()) {
            case TRIM_LAST:
                out[0] = args[args.length - 1].getSingle(((CustomPropertyExpressionEvent) e).getSuperEvent());
                break;
            case TRIM_FIRST:
                out[0] = args[0].getSingle(((CustomPropertyExpressionEvent) e).getSuperEvent());
                break;
        }
        return out;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<?> getReturnType() {
        return returnType;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "hi";
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        if (!ScriptLoader.isCurrentEvent(CustomPropertyExpressionEvent.class)) {
            Skript.error("Origin expression can only be read from custom properties.");
            return false;
        }
        returnType = ((Literal<ClassInfo>) exprs[0]).getSingle().getC();
        return true;
    }
}
