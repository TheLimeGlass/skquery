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
import com.w00tmast3r.skquery.elements.events.bukkit.AttachedTabCompleteEvent;
import com.w00tmast3r.skquery.util.Collect;
import org.bukkit.event.Event;

import java.util.List;


@Patterns("[tab] (completions|suggestions)")
public class ExprTabCompletions extends SimpleExpression<String> {

    protected String[] get(Event e) {
        List<String> completions = ((AttachedTabCompleteEvent) e).getResult();
        return completions.toArray(new String[completions.size()]);
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
    public String toString(Event e, boolean debug) {
        return "tab results";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        if (!ScriptLoader.isCurrentEvent(AttachedTabCompleteEvent.class)) {
            Skript.error("Tab completers can only be accessed from tab complete events.", ErrorQuality.SEMANTIC_ERROR);
            return false;
        }
        return true;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.ADD) return Collect.asArray(String.class);
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        String node = delta[0] == null ? "" : (String) delta[0];
        AttachedTabCompleteEvent event = ((AttachedTabCompleteEvent) e);
        if (node.startsWith(event.getArgs()[event.getArgs().length - 1])) {
            event.getResult().add(node);
        }
    }
}
