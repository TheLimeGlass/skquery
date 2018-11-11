package com.w00tmast3r.skquery.elements.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.w00tmast3r.skquery.api.Description;
import com.w00tmast3r.skquery.api.Examples;
import com.w00tmast3r.skquery.api.Name;
import com.w00tmast3r.skquery.api.Patterns;
import com.w00tmast3r.skquery.util.Collect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@Name("Client Sign")
@Description("Cause a sign to have custom lines to certain viewers")
@Examples("command /hidesign:;->trigger:;->->make all players see lines of targeted block as \"\", \"\", \"\", \"\"")
@Patterns("make %players% see lines of %block% as %string%, %string%, %string%( and|,) %string%")
public class EffClientSign extends Effect {

    private Expression<String> line1, line2, line3, line4;
    private Expression<Player> players;
    private Expression<Block> block;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        players = (Expression<Player>) expressions[0];
        block = (Expression<Block>) expressions[1];
        line1 = (Expression<String>) expressions[2];
        line2 = (Expression<String>) expressions[3];
        line3 = (Expression<String>) expressions[4];
        line4 = (Expression<String>) expressions[5];
        return true;
    }

    @Override
    protected void execute(Event event) {
        Block b = block.getSingle(event);
        if (b == null) return;
        String a1 = line1.getSingle(event);
        String a2 = line2.getSingle(event);
        String a3 = line3.getSingle(event);
        String a4 = line4.getSingle(event);
        Material material = b.getType();
        if (material == Material.SIGN_POST || material == Material.WALL_SIGN || material == Material.SIGN) {
            for (Player player : players.getArray(event)) {
                player.sendSignChange(b.getLocation(), Collect.asArray(a1, a2, a3, a4));
            }
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return "client sign";
    }

}