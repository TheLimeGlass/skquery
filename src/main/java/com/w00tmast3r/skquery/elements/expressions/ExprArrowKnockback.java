package com.w00tmast3r.skquery.elements.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.w00tmast3r.skquery.api.PropertyFrom;
import com.w00tmast3r.skquery.api.PropertyTo;
import com.w00tmast3r.skquery.api.UsePropertyPatterns;
import com.w00tmast3r.skquery.util.Collect;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;

@UsePropertyPatterns
@PropertyFrom("entities")
@PropertyTo("arrow knockback [amount]")
public class ExprArrowKnockback extends SimplePropertyExpression<Entity, Number> {

    @Override
    protected String getPropertyName() {
        return "arrow knockback";
    }

    @Override
    public Number convert(Entity entity) {
        return entity instanceof Arrow ? ((Arrow) entity).getKnockbackStrength() : null;
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
        Number n = delta[0] == null || ((Number) delta[0]).doubleValue() < 0 ? 1 : (Number) delta[0];
        switch (mode) {
            case SET:
                for (Entity p : getExpr().getAll(e)) {
                    if (p instanceof Arrow) ((Arrow) p).setKnockbackStrength(n.intValue());
                }
                break;
            case RESET:
                for (Entity p : getExpr().getAll(e)) {
                    if (p instanceof Arrow) ((Arrow) p).setKnockbackStrength(1);
                }
            case ADD:
            case REMOVE:
            case REMOVE_ALL:
            case DELETE:
                assert false;
        }
    }
}
