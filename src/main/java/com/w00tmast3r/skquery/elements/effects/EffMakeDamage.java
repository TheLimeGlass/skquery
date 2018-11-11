package com.w00tmast3r.skquery.elements.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.w00tmast3r.skquery.api.Description;
import com.w00tmast3r.skquery.api.Examples;
import com.w00tmast3r.skquery.api.Name;
import com.w00tmast3r.skquery.api.Patterns;
import org.bukkit.entity.Creature;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;

@Name("Make Damage")
@Description("Cause entities to damage each other. If you cause hostile mobs to attack or be attacked, they will become aggroed on the last entity that they hit or got hit by.")
@Examples("command /massacre:;->trigger:;->->make all pigs damage all cows by 1")
@Patterns("make %livingentities% damage %livingentities% by %number%")
public class EffMakeDamage extends Effect {

    private Expression<LivingEntity> attacker, victim;
    private Expression<Number> num;

    @Override
    protected void execute(Event event) {
        Number n = num.getSingle(event);
        if (n == null) return;
        for (LivingEntity livingEntity : victim.getAll(event)) {
            for (LivingEntity attack : attacker.getAll(event)) {
                livingEntity.damage(n.doubleValue(), attack);
                if (livingEntity instanceof Creature) ((Creature) livingEntity).setTarget(attack);
                if (attack instanceof Creature) ((Creature) attack).setTarget(livingEntity);
            }
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return "execute";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        attacker = (Expression<LivingEntity>) expressions[0];
        victim = (Expression<LivingEntity>) expressions[1];
        num = (Expression<Number>) expressions[2];
        return true;
    }
}
