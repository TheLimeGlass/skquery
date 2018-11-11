package com.w00tmast3r.skquery.elements.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.w00tmast3r.skquery.api.Description;
import com.w00tmast3r.skquery.api.Examples;
import com.w00tmast3r.skquery.api.Name;
import com.w00tmast3r.skquery.api.Patterns;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;

@Name("Leash Entities")
@Description("Cause multiple entities to leash other entities.")
@Examples("on click:;->make player leash clicked entity")
@Patterns("make %entities% leash %livingentities%")
public class EffLeash extends Effect {

    private Expression<Entity> holder;
    private Expression<LivingEntity> target;

    @Override
    protected void execute(Event event) {
        for (Entity h : holder.getAll(event)) {
            for (LivingEntity t : target.getAll(event)) {
                t.setLeashHolder(h);
            }
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return "leash";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        holder = (Expression<Entity>) expressions[0];
        target = (Expression<LivingEntity>) expressions[1];
        return true;
    }
}
