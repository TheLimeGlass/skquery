package com.w00tmast3r.skquery.elements.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.w00tmast3r.skquery.api.Description;
import com.w00tmast3r.skquery.api.Examples;
import com.w00tmast3r.skquery.api.Name;
import com.w00tmast3r.skquery.api.Patterns;
import com.w00tmast3r.skquery.util.minecraft.FireworkFactory;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.event.Event;

@Name("Pop Firework")
@Description("Instantly detonate a firework at a given location. Having multiple effects will cause them to stack.")
@Examples("on death:;->pop ball large firework colored red at victim")
@Patterns("(detonate|pop) %fireworkeffects% at %locations%")
public class EffPop extends Effect {

    private Expression<FireworkEffect> effects;
    private Expression<Location> loc;

    @Override
    protected void execute(Event event) {
        for (Location l : loc.getAll(event)) {
            new FireworkFactory().location(l).effects(effects.getAll(event)).play();
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return "pop firework";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        effects = (Expression<FireworkEffect>) expressions[0];
        loc = (Expression<Location>) expressions[1];
        return true;
    }
}
