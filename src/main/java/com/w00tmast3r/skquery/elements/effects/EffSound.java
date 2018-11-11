package com.w00tmast3r.skquery.elements.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.w00tmast3r.skquery.api.Patterns;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.event.Event;


@Patterns("play %sound% at %locations% with pitch %number%")
public class EffSound extends Effect {

    private Expression<Sound> sound;
    private Expression<Location> loc;
    private Expression<Number> pit;

    @Override
    protected void execute(Event event) {
        Sound s = sound.getSingle(event);
        Location[] l = loc.getAll(event);
        float p = pit.getSingle(event).floatValue();
        if (s == null || l == null) return;
        for (Location fl : l) {
            fl.getWorld().playSound(fl, s, 1, p);
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return "sound";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        sound = (Expression<Sound>) expressions[0];
        loc = (Expression<Location>) expressions[1];
        pit = (Expression<Number>) expressions[2];
        return true;
    }
}
