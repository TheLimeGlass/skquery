package com.w00tmast3r.skquery.elements.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.w00tmast3r.skquery.api.PropertyFrom;
import com.w00tmast3r.skquery.api.PropertyTo;
import com.w00tmast3r.skquery.api.UsePropertyPatterns;
import com.w00tmast3r.skquery.util.Collect;
import com.w00tmast3r.skquery.util.custom.menus.v2_.FormattedSlotManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

@UsePropertyPatterns
@PropertyFrom("inventory")
@PropertyTo("inventory name")
public class ExprInventoryName extends SimplePropertyExpression<Inventory, String> {

    @Override
    protected String getPropertyName() {
        return "inventory name";
    }

    @Override
    public String convert(Inventory inventory) {
        return inventory.getName();
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        return mode == Changer.ChangeMode.SET ? Collect.asArray(String.class) : null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        String s = delta[0] == null ? "" : (String) delta[0];
        Inventory i = getExpr().getSingle(e);
        Inventory copy;
        if (i.getType() == InventoryType.CHEST) {
            copy = Bukkit.createInventory(i.getHolder(), i.getSize(), s);
        } else return;
        for (HumanEntity h : new ArrayList<>(i.getViewers())) {
            if (h instanceof Player) FormattedSlotManager.exemptNextClose((Player) h);
            h.openInventory(copy);
        }
        copy.setContents(i.getContents());

    }
}
