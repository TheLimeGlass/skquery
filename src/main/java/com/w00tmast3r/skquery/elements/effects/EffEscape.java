package com.w00tmast3r.skquery.elements.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.TriggerItem;
import ch.njol.util.Kleenean;
import com.w00tmast3r.skquery.api.Description;
import com.w00tmast3r.skquery.api.Examples;
import com.w00tmast3r.skquery.api.Name;
import com.w00tmast3r.skquery.api.Patterns;
import org.bukkit.event.Event;

@Name("Escape Lines")
@Description("Skip the execution of a certain number of lines.")
@Examples("on script load:;->escape 1;->stop;->message \"Stop avoided!\" to console")
@Patterns("escape %number% [(level[s]|line[s])]")
public class EffEscape extends Effect {

    private Expression<Number> esc;

    @Override
    protected void execute(Event event) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected TriggerItem walk(Event e) {
        debug(e, false);
        Number eli = esc.getSingle(e);
        if (eli == null) return null;
        int el = eli.intValue();
        if (el <= 0) return getNext();
        TriggerItem item = getNext();
        for (int i = 0; i < el; i++) {
            if (item == null) return null;
            item = item.getNext();
        }
        return item;
    }


    @Override
    public String toString(Event event, boolean b) {
        return "escape";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        esc = (Expression<Number>) expressions[0];
        return true;
    }
}
