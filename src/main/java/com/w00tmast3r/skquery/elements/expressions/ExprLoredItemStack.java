package com.w00tmast3r.skquery.elements.expressions;

import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.expressions.base.PropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.w00tmast3r.skquery.api.Patterns;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

@Patterns("%itemtypes% with lore %string%")
public class ExprLoredItemStack extends PropertyExpression<ItemType, ItemType> {

    private Expression<String> lore;

    @Override
    protected ItemType[] get(Event event, ItemType[] itemTypes) {
        final String n = lore.getSingle(event);
        if (n == null)
            return new ItemType[0];
        final ItemType[] r = itemTypes.clone();
        for (int i = 0; i < r.length; i++) {
            r[i] = itemTypes[i].clone();
            final ItemMeta m = r[i].getItemMeta() == null ? Bukkit.getItemFactory().getItemMeta(Material.STONE) : (ItemMeta) r[i].getItemMeta();
            m.setLore(Arrays.asList(n.split("\\|\\|")));
            r[i].setItemMeta(m);
        }
        return r;
    }

    @Override
    public Class<? extends ItemType> getReturnType() {
        return ItemType.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "lore";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        setExpr((Expression<ItemType>) expressions[0]);
        lore = (Expression<String>) expressions[1];
        return true;
    }
}
