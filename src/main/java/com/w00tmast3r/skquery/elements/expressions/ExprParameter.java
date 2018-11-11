package com.w00tmast3r.skquery.elements.expressions;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.util.Kleenean;
import com.w00tmast3r.skquery.api.Patterns;
import com.w00tmast3r.skquery.elements.events.lang.FunctionEvent;
import com.w00tmast3r.skquery.elements.events.lang.RoutineEvent;
import org.bukkit.event.Event;


@Patterns("parameter(-| )%number%")
public class ExprParameter extends SimpleExpression<Object> {

    private Expression<Number> arg;

    @Override
    protected Object[] get(Event event) {
        Number a = arg.getSingle(event);
        if (a == null) return null;
        Object o = null;
        try {
            if (event instanceof RoutineEvent) o = ((RoutineEvent) event).getParams()[a.intValue() - 1];
            if (event instanceof FunctionEvent) o = ((FunctionEvent) event).getParams()[a.intValue() - 1];
        } catch (Exception e) {
            return null;
        }
        if (o == null) return null;
        return new Object[]{o};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<?> getReturnType() {
        return Object.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "parameter";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        if (!ScriptLoader.isCurrentEvent(RoutineEvent.class, FunctionEvent.class)) {
            Skript.error("Parameters can only be referenced inside a subroutine or function", ErrorQuality.SEMANTIC_ERROR);
            return false;
        }
        arg = (Expression<Number>) expressions[0];
        return true;
    }
}
