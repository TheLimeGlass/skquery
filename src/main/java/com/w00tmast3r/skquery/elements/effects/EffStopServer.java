package com.w00tmast3r.skquery.elements.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.w00tmast3r.skquery.api.Patterns;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;


@Patterns("(stop|shutdown) server")
public class EffStopServer extends Effect {

    @Override
    protected void execute(Event event) {
        Bukkit.shutdown();
    }

    @Override
    public String toString(Event event, boolean b) {
        return "shutdown server";
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        return true;
    }
}
