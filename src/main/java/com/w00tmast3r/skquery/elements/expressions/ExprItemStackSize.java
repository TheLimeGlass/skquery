package com.w00tmast3r.skquery.elements.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.w00tmast3r.skquery.api.PropertyFrom;
import com.w00tmast3r.skquery.api.PropertyTo;
import com.w00tmast3r.skquery.api.UsePropertyPatterns;
import com.w00tmast3r.skquery.util.Collect;
import org.bukkit.event.Event;
import org.bukkit.inventory.Inventory;

@UsePropertyPatterns
@PropertyFrom("inventories")
@PropertyTo("global max stack size")
public class ExprItemStackSize extends SimplePropertyExpression<Inventory, Number> {

    @Override
    protected String getPropertyName() {
        return "max stack";
    }

    @Override
    public Number convert(Inventory inventory) {
        return inventory.getMaxStackSize();
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.RESET || mode == Changer.ChangeMode.SET) return Collect.asArray(Number.class);
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        Number b = delta == null ? 64 : (Number) delta[0];
        switch (mode) {
            case SET:
                for (Inventory i : getExpr().getAll(e)) {
                    i.setMaxStackSize(b.intValue());
                }
                break;
            case RESET:
                for (Inventory i : getExpr().getAll(e)) {
                    i.setMaxStackSize(64);
                }
            case ADD:
            case REMOVE:
            case REMOVE_ALL:
            case DELETE:
                assert false;
        }
    }
}
