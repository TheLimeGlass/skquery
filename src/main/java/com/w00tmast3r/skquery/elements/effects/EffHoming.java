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
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.util.Vector;

@Name("Entity Homing")
@Description("Cause an entity to home towards a locations. Specifying 'normally' reduces bugs caused by varying distances, but makes it less accurate.")
@Examples("make targeted entity home towards player normally")
@Patterns({"make %entity% home towards %location%",
        "make %entity% home towards %location% normally"})
public class EffHoming extends Effect {

    private Expression<Entity> from;
    private Expression<Location> to;
    private boolean isNormal;

    @Override
    protected void execute(Event event) {
        Entity f = from.getSingle(event);
        Location t = to.getSingle(event);
        if (f == null || t == null) return;
        Vector v = t.toVector().subtract(f.getLocation().toVector());
        if (isNormal) f.setVelocity(v.normalize());
        else f.setVelocity(v);
    }

    @Override
    public String toString(Event event, boolean b) {
        return "home";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        from = (Expression<Entity>) expressions[0];
        to = (Expression<Location>) expressions[1];
        isNormal = i == 1;
        return true;
    }
}
