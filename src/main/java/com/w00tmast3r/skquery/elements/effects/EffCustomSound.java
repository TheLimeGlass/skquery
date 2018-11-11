package com.w00tmast3r.skquery.elements.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.w00tmast3r.skquery.api.Description;
import com.w00tmast3r.skquery.api.Examples;
import com.w00tmast3r.skquery.api.Name;
import com.w00tmast3r.skquery.api.Patterns;
import org.bukkit.Location;
import org.bukkit.event.Event;

@Name("Play Raw Sound")
@Description("Imitates the functionality of the /playsound command, without the ability to specify target players.")
@Examples("on sneak toggle:;->play raw sound \"mob.bat.death\" at player with pitch 1 volume 10")
@Patterns("play raw sound %string% at %locations% with pitch %number% volume %number%")
public class EffCustomSound extends Effect {

    private Expression<String> sound;
    private Expression<Location> loc;
    private Expression<Number> pit, vol;

    @Override
    protected void execute(Event event) {
        String s = sound.getSingle(event);
        Location[] l = loc.getAll(event);
        float p = pit.getSingle(event).floatValue();
        float v = vol.getSingle(event).floatValue();
        if (s == null || l == null) return;

        for (Location fl : l) {
            fl.getWorld().playSound(fl, s, v, p);
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return "sound";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        sound = (Expression<String>) expressions[0];
        loc = (Expression<Location>) expressions[1];
        pit = (Expression<Number>) expressions[3];
        vol = (Expression<Number>) expressions[2];
        return true;
    }
}
