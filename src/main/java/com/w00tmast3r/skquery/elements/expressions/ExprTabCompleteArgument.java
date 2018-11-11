package com.w00tmast3r.skquery.elements.expressions;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.util.Kleenean;
import com.w00tmast3r.skquery.api.Patterns;
import com.w00tmast3r.skquery.elements.events.bukkit.AttachedTabCompleteEvent;
import com.w00tmast3r.skquery.util.Collect;
import org.bukkit.event.Event;


@Patterns("arg[ument] at %number%")
public class ExprTabCompleteArgument extends SimpleExpression<String> {

    private Expression<Number> num;

    protected String[] get(Event e) {
        Number n = num.getSingle(e);
        if (n == null) return null;
        try {
            return Collect.asArray(((AttachedTabCompleteEvent) e).getArgs()[n.intValue() - 1]);
        } catch (IndexOutOfBoundsException ex) {
            return null;
        }
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "tab results";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        if (!ScriptLoader.isCurrentEvent(AttachedTabCompleteEvent.class)) {
            Skript.error("Tab completers can only be accessed from tab complete events.", ErrorQuality.SEMANTIC_ERROR);
            return false;
        }
        num = (Expression<Number>) exprs[0];
        return true;
    }
}
