package com.w00tmast3r.skquery.elements.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.w00tmast3r.skquery.api.Patterns;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.Arrays;


@Patterns("book from %string%")
public class ExprBookOf extends SimpleExpression<ItemStack> {

    private Expression<String> type;

    @Override
    protected ItemStack[] get(Event event) {
        String t = type.getSingle(event);
        if (t == null) return null;
        String[] invStr = t.split(";", 4);
        if (invStr.length != 4) return null;
        ItemStack target = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) target.getItemMeta();
        meta.setDisplayName(invStr[0]);
        meta.setAuthor(invStr[1]);
        meta.setLore(Arrays.asList(invStr[2].split("\\|\\|")));
        meta.setPages(Arrays.asList(invStr[3].split("\\|\\|")));
        target.setItemMeta(meta);
        return new ItemStack[]{target};
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
    public String toString(Event event, boolean b) {
        return "itemstack";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        type = (Expression<String>) expressions[0];
        return true;
    }
}
