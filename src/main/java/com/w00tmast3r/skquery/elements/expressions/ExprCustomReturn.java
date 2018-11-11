package com.w00tmast3r.skquery.elements.expressions;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.w00tmast3r.skquery.api.Patterns;
import com.w00tmast3r.skquery.elements.events.lang.CustomExpressionEvent;
import com.w00tmast3r.skquery.elements.events.lang.CustomPropertyExpressionEvent;
import com.w00tmast3r.skquery.elements.events.lang.Returnable;
import com.w00tmast3r.skquery.util.Collect;
import org.bukkit.event.Event;

@Patterns("[custom] expression ([return] value|output)")
public class ExprCustomReturn extends SimpleExpression<Object> {

    @Override
    protected Object[] get(Event e) {
        Skript.error("You cannot read a custom return value.");
        return null;
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<?> getReturnType() {
        return Object.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "return value";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        if (!ScriptLoader.isCurrentEvent(CustomExpressionEvent.class, CustomPropertyExpressionEvent.class)) {
            Skript.error("The return value of a custom expression can only be used in expression logic");
            return false;
        }
        if (isDelayed.isTrue()) {
            Skript.error("The return value of a custom expression cannot be modified after a delay");
            return false;
        }
        return true;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) return Collect.asArray(Object[].class);
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        Object[] out = Collect.newArray(((Returnable) e).getExpectedOutput(), delta.length);
        System.arraycopy(delta, 0, out, 0, delta.length);
        ((Returnable) e).setReturn(out);
    }
}
