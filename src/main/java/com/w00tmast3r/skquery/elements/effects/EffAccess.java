package com.w00tmast3r.skquery.elements.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.TriggerItem;
import ch.njol.util.Kleenean;
import com.w00tmast3r.skquery.SkQuery;
import com.w00tmast3r.skquery.api.Description;
import com.w00tmast3r.skquery.api.Name;
import com.w00tmast3r.skquery.api.Patterns;
import com.w00tmast3r.skquery.elements.events.lang.FunctionEvent;
import com.w00tmast3r.skquery.elements.events.lang.ReturnEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@Name("Invoke Cross Access")
@Description("Permits a function to retrieve and inject data from the current event. The current code block will not continue until the function calls ((EffReturn)return) or ((EffSoftReturn)soft return).")
@Patterns({"access %string%", "access %string% from %objects%"})
public class EffAccess extends Effect implements Listener {

    private Expression<String> cause;
    private Expression<Object> args = null;
    private Event storedEvent;
    private TriggerItem next;

    @Override
    protected void execute(Event event) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected TriggerItem walk(Event e) {
        String c = cause.getSingle(e);
        if (c == null) return null;
        Bukkit.getPluginManager().registerEvents(this, SkQuery.getInstance());
        storedEvent = e;
        next = getNext();
        Bukkit.getPluginManager().callEvent(new FunctionEvent(c, (args == null ? null : args.getAll(e)), storedEvent));
        return null;
    }

    @EventHandler
    public void onReturn(ReturnEvent event) {
        if (event.getInvoker() == storedEvent) {
            TriggerItem.walk(next, storedEvent);
            event.getHandlers().unregister(this);
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return "sync";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        if (i == 0) {
            cause = (Expression<String>) expressions[0];
        } else {
            cause = (Expression<String>) expressions[0];
            args = (Expression<Object>) expressions[1];
        }
        return true;
    }
}
