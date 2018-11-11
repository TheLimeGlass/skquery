package com.w00tmast3r.skquery.elements.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.w00tmast3r.skquery.api.Description;
import com.w00tmast3r.skquery.api.Examples;
import com.w00tmast3r.skquery.api.Name;
import com.w00tmast3r.skquery.api.Patterns;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.event.Event;
import org.bukkit.inventory.meta.FireworkMeta;

@Name("Launch Fireworks")
@Description("Launch a firework with any number of firework effects at a given location and flight duration. Use (../expressions/(ExprFireworkEffect)this expression) to create firework effects.")
@Examples("on death:;->launch creeper firework colored red at victim timed 1")
@Patterns("(launch|deploy) %fireworkeffects% at %locations% (with duration|timed) %number%")
public class EffLaunch extends Effect {

    private Expression<FireworkEffect> effects;
    private Expression<Location> loc;
    private Expression<Number> lifetime;

    @Override
    protected void execute(Event event) {
        Number n = lifetime.getSingle(event);
        if (n == null) return;
        for (Location l : loc.getAll(event)) {
            Firework firework = l.getWorld().spawn(l, Firework.class);
            FireworkMeta fm = firework.getFireworkMeta();
            fm.setPower(n.intValue());
            fm.addEffects(effects.getAll(event));
            firework.setFireworkMeta(fm);
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return "launch launch";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        effects = (Expression<FireworkEffect>) expressions[0];
        loc = (Expression<Location>) expressions[1];
        lifetime = (Expression<Number>) expressions[2];
        return true;
    }
}
