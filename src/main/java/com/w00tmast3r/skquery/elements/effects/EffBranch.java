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

@Name("Branch")
@Description("Execute the following code X times. Useful for testing probabilities without requiring a loop and indentation.")
@Examples("on script load:;->branch 10;->message \"This message will pop up 10 times\" to console")
@Patterns("branch %number%")
public class EffBranch extends Effect {

    private Expression<Number> br;

    @Override
    protected void execute(Event event) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected TriggerItem walk(Event e) {
        debug(e, false);
        Number bv = br.getSingle(e);
        if (bv == null) return null;
        int b = bv.intValue();
        for (int i = 0; i < b; i++) {
            TriggerItem.walk(getNext(), e);
        }
        return null;
    }


    @Override
    public String toString(Event event, boolean b) {
        return "escape";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        br = (Expression<Number>) expressions[0];
        return true;
    }
}
