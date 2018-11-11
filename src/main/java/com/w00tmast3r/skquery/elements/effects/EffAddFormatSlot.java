package com.w00tmast3r.skquery.elements.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.w00tmast3r.skquery.api.Description;
import com.w00tmast3r.skquery.api.Examples;
import com.w00tmast3r.skquery.api.Name;
import com.w00tmast3r.skquery.api.Patterns;
import com.w00tmast3r.skquery.util.custom.menus.v2_.FormattedSlotManager;
import com.w00tmast3r.skquery.util.custom.menus.v2_.SlotRule;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

@Name("Format Inventory Slot")
@Description("Formats a slot in the player's open inventory to do certain actions. This should be done directly after showing an inventory to the player.")
@Examples("command /construct:;->trigger:;->->open chest with 1 rows named \"&4My first test menu\" to player;->->format slot 0 of player with 5 of steak named \"Item 1\" to close then run \"say The first item was clicked! Menu Closed!\";->->format slot 2 of player with fire named \"Close Menu\" with lore \"I will close this menu.||Nothing more, nothing less.\" to close")
@Patterns({
        "format slot %number% of %players% with %itemstack% to close then run %string/lambda%",
        "format slot %number% of %players% with %itemstack% to run %string/lambda%",
        "format slot %number% of %players% with %itemstack% to close",
        "format slot %number% of %players% with %itemstack% to (be|act) unstealable",
        "unformat slot %number% of %players%"
})
public class EffAddFormatSlot extends Effect {

    private Expression<Number> slot;
    private Expression<Player> targets;
    private Expression<ItemStack> item;
    private Expression<?> callback;
    private int action;

    @Override
    protected void execute(Event event) {
        Number s = slot.getSingle(event);
        Object c;
        ItemStack i = null;
        if (s == null) return;
        SlotRule toClone;
        switch (action) {
            case 0:
                c = callback.getSingle(event);
                i = item.getSingle(event);
                if (c == null) return;
                toClone = new SlotRule(c, true);
                break;
            case 1:
                c = callback.getSingle(event);
                i = item.getSingle(event);
                if (c == null) return;
                toClone = new SlotRule(c, false);
                break;
            case 2:
                i = item.getSingle(event);
                if (i == null) return;
                toClone = new SlotRule(null, true);
                break;
            case 3:
                i = item.getSingle(event);
                toClone = new SlotRule(null, false);
                break;
            case 4:
                for (Player p : targets.getAll(event)) {
                    FormattedSlotManager.removeRule(p, s.intValue());
                }
                return;
            default:
                assert false;
                return;
        }
        if (i != null) {
            for (Player p : targets.getAll(event)) {
                if (p.getOpenInventory().getType() != InventoryType.CRAFTING)
                    p.getOpenInventory().setItem(s.intValue(), i);
            }
        }
        for (Player p : targets.getAll(event)) {
            if (p.getOpenInventory().getType() != InventoryType.CRAFTING)
                FormattedSlotManager.addRule(event, p, s.intValue(), toClone.getCopy());
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return "format";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        slot = (Expression<Number>) expressions[0];
        targets = (Expression<Player>) expressions[1];
        if (i <= 3) item = (Expression<ItemStack>) expressions[2];
        if (i <= 1) callback = expressions[3];
        action = i;
        return true;
    }
}
