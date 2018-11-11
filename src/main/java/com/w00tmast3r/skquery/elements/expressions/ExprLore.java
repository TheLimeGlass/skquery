package com.w00tmast3r.skquery.elements.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import com.w00tmast3r.skquery.api.PropertyFrom;
import com.w00tmast3r.skquery.api.PropertyTo;
import com.w00tmast3r.skquery.api.UsePropertyPatterns;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

@UsePropertyPatterns
@PropertyFrom("itemstack")
@PropertyTo("lore")
public class ExprLore extends SimplePropertyExpression<ItemStack, String> {
    @Override
    protected String getPropertyName() {
        return "lore";
    }

    @Override
    public String convert(ItemStack itemStack) {
        String re = "";
        boolean fs = true;
        try {
            if (!itemStack.getItemMeta().hasLore()) return "";
            for (String s : itemStack.getItemMeta().getLore()) {
                if (fs) re = s;
                else re += "||" + s;
                fs = false;
            }
        } catch (NullPointerException e) {
            return "";
        }
        return re;
    }

    @SuppressWarnings("incomplete-switch")
    @Override
    public void change(final Event e, final Object[] delta, final Changer.ChangeMode mode) throws UnsupportedOperationException {
        String l = delta == null ? "" : (String) delta[0];
        ItemStack i = getExpr().getSingle(e);
        ItemMeta m = i.getItemMeta();
        if (i.getType() == Material.AIR) return;
        switch (mode) {
            case SET:
                m.setLore(Arrays.asList(l.split("\\|\\|")));
                i.setItemMeta(m);
                break;
            case RESET:
                i.getItemMeta().setLore(null);
                break;
        }
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET)
            return CollectionUtils.array(String.class);
        return null;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}
