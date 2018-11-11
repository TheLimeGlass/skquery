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
import com.w00tmast3r.skquery.elements.events.lang.CustomEffectEvent;
import com.w00tmast3r.skquery.elements.events.lang.CustomExpressionEvent;
import com.w00tmast3r.skquery.elements.events.lang.CustomPropertyExpressionEvent;
import com.w00tmast3r.skquery.elements.events.lang.Pullable;
import org.bukkit.event.Event;

@Patterns({"%*classinfo% expression( |-)%integer%", "loopable %*classinfo% expression( |-)%integer%"})
public class ExprInnerExpression extends SimpleExpression<Object> {

    private Expression<Number> arg;
    private Class<?> returnType = Object.class;
    private boolean isSingle = false;

    protected Object[] get(Event e) {
        Number a = arg.getSingle(e);
        if (a == null) return null;
        int value = a.intValue() - 1;
        if (value < 0) return null;
        if (e instanceof CustomPropertyExpressionEvent) {
            switch (((CustomPropertyExpressionEvent) e).getPattern()) {
                case TRIM_LAST:
                    if (value == ((CustomPropertyExpressionEvent) e).getArgs().length) return null;
                    break;
                case TRIM_FIRST:
                    value++;
                    break;
            }
        }
        if (value >= ((Pullable) e).getArgs().length) return null;
        return ((Pullable) e).getArgs()[value].getAll(((Pullable) e).getSuperEvent());
    }

    @Override
    public boolean isSingle() {
        return isSingle;
    }

    @Override
    public Class<?> getReturnType() {
        return returnType;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "hi";
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        if (!ScriptLoader.isCurrentEvent(CustomEffectEvent.class, CustomExpressionEvent.class, CustomPropertyExpressionEvent.class)) {
            Skript.error("Expressions can only be read from custom effects/expressions.");
            return false;
        }
        arg = (Expression<Number>) exprs[1];
        returnType = ((Literal<ClassInfo>) exprs[0]).getSingle().getC();
        isSingle = matchedPattern == 0;
        return true;
    }
}
