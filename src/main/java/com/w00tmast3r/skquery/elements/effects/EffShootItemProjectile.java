package com.w00tmast3r.skquery.elements.effects;

import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.w00tmast3r.skquery.api.Patterns;
import com.w00tmast3r.skquery.util.custom.projectile.ItemProjectile;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;


@Patterns("make %livingentity% shoot [an] item projectile [of] %itemtype%[ at speed %number%]")
public class EffShootItemProjectile extends Effect {

    private Expression<ItemType> projectile;
    private Expression<LivingEntity> shooter;
    private Expression<Number> velocity;

    @Override
    protected void execute(Event event) {
        ItemType p = projectile.getSingle(event);
        LivingEntity s = shooter.getSingle(event);
        Number n;
        if (p == null || s == null) return;
        if (velocity == null) n = 1;
        else n = velocity.getSingle(event);
        float v = n.floatValue();
        new ItemProjectile(p.getRandom()).shoot(s, s.getLocation().getDirection().multiply(v));
    }

    @Override
    public String toString(Event event, boolean b) {
        return "item projectiles [happy funnygatt?]";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        shooter = (Expression<LivingEntity>) expressions[0];
        projectile = (Expression<ItemType>) expressions[1];
        velocity = (Expression<Number>) expressions[2];
        return true;
    }
}
