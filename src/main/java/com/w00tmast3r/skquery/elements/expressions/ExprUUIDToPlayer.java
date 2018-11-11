package com.w00tmast3r.skquery.elements.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.w00tmast3r.skquery.api.Patterns;
import com.w00tmast3r.skquery.util.Collect;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.UUID;

@Patterns("player from [uuid] %string%")
public class ExprUUIDToPlayer extends SimpleExpression<Player> {

    private Expression<String> uuid;

    @Override
    protected Player[] get(Event e) {
        String player = uuid.getSingle(e);
        if (player == null) return null;
        UUID uniqueId;
        try {
            uniqueId = UUID.fromString(player);
        } catch (IllegalArgumentException ex) {
            return null;
        }
        return Collect.asArray(Bukkit.getPlayer(uniqueId));
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Player> getReturnType() {
        return Player.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "player from uuid";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        uuid = (Expression<String>) exprs[0];
        return true;
    }
}
