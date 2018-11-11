package com.w00tmast3r.skquery.elements.expressions;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.w00tmast3r.skquery.api.PropertyFrom;
import com.w00tmast3r.skquery.api.PropertyTo;
import com.w00tmast3r.skquery.api.UsePropertyPatterns;
import com.w00tmast3r.skquery.util.serialization.InventorySerialUtils;
import org.bukkit.entity.Player;

@UsePropertyPatterns
@PropertyFrom("player")
@PropertyTo("serialized inventory")
public class ExprSerial extends SimplePropertyExpression<Player, String> {
    @Override
    protected String getPropertyName() {
        return "serial";
    }

    @Override
    public String convert(Player player) {
        return InventorySerialUtils.toBase64(player.getInventory());
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}
