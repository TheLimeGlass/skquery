package com.w00tmast3r.skquery.elements.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.w00tmast3r.skquery.api.Patterns;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.event.Event;


@Patterns("tame %entities% to %player%")
public class EffTameTo extends Effect {

    private Expression<Entity> ent;
    private Expression<Player> tamer;

    @Override
    protected void execute(Event event) {
        Player p = tamer.getSingle(event);
        if (p == null) return;
        for (Entity e : ent.getAll(event)) {
            if (e instanceof Tameable) ((Tameable) e).setOwner(p);
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        ent = (Expression<Entity>) expressions[0];
        tamer = (Expression<Player>) expressions[1];
        return true;
    }
}
