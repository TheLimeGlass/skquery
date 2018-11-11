package com.w00tmast3r.skquery.elements.effects;

import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.w00tmast3r.skquery.api.Description;
import com.w00tmast3r.skquery.api.Examples;
import com.w00tmast3r.skquery.api.Name;
import com.w00tmast3r.skquery.api.Patterns;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@Name("Client Block")
@Description("Make a player see a block as another type of block. If the client recieves an update to the block, it will revert.")
@Examples("on click:;->make player see clicked block as air")
@Patterns("make %players% see %block% as %itemtype%")
public class EffClientBlock extends Effect {

    private Expression<Player> player;
    private Expression<Block> block;
    private Expression<ItemType> material;

    @SuppressWarnings("deprecation")
    @Override
    protected void execute(Event event) {
        ItemType m = material.getSingle(event);
        if (m == null) return;
        Material mat = m.getRandom().getType();
        if (!mat.isBlock()) return;
        for (Player p : player.getAll(event)) {
            for (Block b : block.getAll(event)) {
                p.sendBlockChange(b.getLocation(), mat, (byte) m.getRandom().getDurability());
            }
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return "credits";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        player = (Expression<Player>) expressions[0];
        block = (Expression<Block>) expressions[1];
        material = (Expression<ItemType>) expressions[2];
        return true;
    }
}
