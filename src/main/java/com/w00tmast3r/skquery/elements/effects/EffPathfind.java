package com.w00tmast3r.skquery.elements.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.w00tmast3r.skquery.api.Description;
import com.w00tmast3r.skquery.api.Examples;
import com.w00tmast3r.skquery.api.Name;
import com.w00tmast3r.skquery.api.Patterns;
import com.w00tmast3r.skquery.util.Reflection;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.lang.reflect.InvocationTargetException;

@Name("Pathfind")
@Description("Invoke the entity pathfinder. Allows entities to pathfind to a location unless they are distracted. Not all entities have pathfinders.")
@Examples("on click on cow:;->while clicked entity exists:;->->make clicked entity pathfind to player with speed;->->wait 1 tick")
@Patterns("make %livingentities% pathfind to %location% (with|at) speed %number%")
public class EffPathfind extends Effect {

    private Expression<LivingEntity> entity;
    private Expression<Location> loc;
    private Expression<Number> speed;

    @Override
    protected void execute(Event event) {
        Location l = loc.getSingle(event);
        Number s = speed.getSingle(event);
        if (l == null || s == null) return;
        for (LivingEntity e : entity.getAll(event)) {
            if (e instanceof Player) continue;
            try {
                Object entityInsentient = Reflection.getOBCClass("entity.CraftLivingEntity").getMethod("getHandle").invoke(e);
                Object navigation = Reflection.getNMSClass("EntityInsentient").getMethod("getNavigation").invoke(entityInsentient);
                navigation.getClass().getMethod("a", double.class, double.class, double.class, double.class).invoke(navigation, l.getX(), l.getY(), l.getZ(), s.doubleValue());
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return "pathfind";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        entity = (Expression<LivingEntity>) expressions[0];
        loc = (Expression<Location>) expressions[1];
        speed = (Expression<Number>) expressions[2];
        return true;
    }
}
