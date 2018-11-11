package com.w00tmast3r.skquery.elements.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import com.w00tmast3r.skquery.elements.events.lang.MethodEvent;
import com.w00tmast3r.skquery.elements.expressions.ExprCustomPropertyExpression;
import com.w00tmast3r.skquery.util.BiValue;
import org.bukkit.event.Event;

public class EvtCustomProperties extends SkriptEvent {

    private String execute;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Literal<?>[] args, int matchedPattern, SkriptParser.ParseResult parseResult) {
        String s = ((Literal<String>) args[0]).getSingle();
        String r = ((Literal<String>) args[1]).getSingle();
        if (!ExprCustomPropertyExpression.getEntries().contains(new BiValue<>(s, r))) {
            Skript.error(s + " of " + r + " is not a registered custom expression. Not defined in any .skqc files.");
            return false;
        }
        execute = s;
        return true;
    }

    @Override
    public boolean check(Event e) {
        return ((MethodEvent) e).getMatch().equals(execute);
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "command logic";
    }
}
