package com.w00tmast3r.skquery.elements.expressions;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.util.Kleenean;
import com.w00tmast3r.skquery.api.Patterns;
import com.w00tmast3r.skquery.elements.events.lang.FunctionEvent;
import com.w00tmast3r.skquery.skript.TransEventObjects;
import com.w00tmast3r.skquery.util.Collect;
import org.bukkit.event.Event;

@Patterns("transient [object] %string%")
public class ExprTransientObject extends SimpleExpression<Object> {

    @SuppressWarnings("unused")
    private Expression<String> id;

    @Override
    protected Object[] get(Event e) {
        return Collect.asArray(TransEventObjects.retrieve(e));
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Object> getReturnType() {
        return Object.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "transient";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        if (!ScriptLoader.isCurrentEvent(FunctionEvent.class)) {
            Skript.error("Transient objects can only be  in functions", ErrorQuality.SEMANTIC_ERROR);
            return false;
        }
        id = (Expression<String>) exprs[0];
        return true;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) return Collect.asArray(Object.class);
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        Event key;
        if (e instanceof FunctionEvent) key = ((FunctionEvent) e).getInvoker();
        else key = e;
        TransEventObjects.store(key, delta[0]);
    }
}
