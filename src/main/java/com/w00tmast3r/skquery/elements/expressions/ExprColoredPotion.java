package com.w00tmast3r.skquery.elements.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.w00tmast3r.skquery.api.Patterns;
import com.w00tmast3r.skquery.util.Collect;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

@SuppressWarnings("deprecation")
@Patterns("blank %potioneffecttype% [colo[u]r[ed]] potion")
public class ExprColoredPotion extends SimpleExpression<ItemStack> {

    private Expression<PotionEffectType> effect;

    @Override
    protected ItemStack[] get(Event e) {
        PotionEffectType p = effect.getSingle(e);
        if (p == null) return null;
        ItemStack potion = new Potion(PotionType.getByEffect(p)).toItemStack(1);
        PotionMeta meta = ((PotionMeta) potion.getItemMeta());
        meta.addCustomEffect(new PotionEffect(p, 0, 0), true);
        potion.setItemMeta(meta);
        return Collect.asArray(potion);
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends ItemStack> getReturnType() {
        return ItemStack.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "potionless potion";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        effect = (Expression<PotionEffectType>) exprs[0];
        return true;
    }
}
