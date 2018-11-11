package com.w00tmast3r.skquery.elements.conditions;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import com.w00tmast3r.skquery.api.Description;
import com.w00tmast3r.skquery.api.Name;
import com.w00tmast3r.skquery.api.Patterns;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffectType;

@Name("Entity has Potion")
@Description("Checks whether or not an entity has a certain potion effect.")
@Patterns({"%livingentity% (has|have) %potioneffecttype%", "%livingentities% does(n't| not) (has|have) %potioneffecttype%"})
public class CondHasPotion extends Condition {

    private Expression<LivingEntity> livingEntity;
    private Expression<PotionEffectType> potion;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        livingEntity = (Expression<LivingEntity>) expressions[0];
        potion = (Expression<PotionEffectType>) expressions[1];
        setNegated(matchedPattern == 1);
        return true;
    }

    @Override
    public boolean check(Event event) {
        LivingEntity entity = livingEntity.getSingle(event);
        PotionEffectType effect = potion.getSingle(event);
        return !(entity == null || effect == null) && (isNegated() ? !entity.hasPotionEffect(effect) : entity.hasPotionEffect(effect));
    }

    @Override
    public String toString(Event event, boolean b) {
        return "entity has potion";
    }

}