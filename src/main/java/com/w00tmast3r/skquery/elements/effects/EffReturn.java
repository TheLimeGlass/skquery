package com.w00tmast3r.skquery.elements.effects;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.TriggerItem;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.util.Kleenean;
import com.w00tmast3r.skquery.api.Description;
import com.w00tmast3r.skquery.api.Name;
import com.w00tmast3r.skquery.api.Patterns;
import com.w00tmast3r.skquery.elements.events.lang.FunctionEvent;
import com.w00tmast3r.skquery.elements.events.lang.ReturnEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

@Name("Return")
@Description("Allows the code that called a function to continue. Works as a \"stop\", preventing code after it from executing. If you want to let the following code execute, use ((EffSoftReturn)soft return)")
@Patterns("return")
public class EffReturn extends Effect {

    @Override
    protected void execute(Event event) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected TriggerItem walk(Event e) {
        Bukkit.getPluginManager().callEvent(new ReturnEvent(((FunctionEvent) e).getInvoker()));
        return null;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "return";
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        if (!ScriptLoader.isCurrentEvent(FunctionEvent.class)) {
            Skript.error("Return effects can only be used inside functions", ErrorQuality.SEMANTIC_ERROR);
            return false;
        }
        return true;
    }
}
