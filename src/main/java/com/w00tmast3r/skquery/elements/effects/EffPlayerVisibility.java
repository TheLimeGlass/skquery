package com.w00tmast3r.skquery.elements.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.w00tmast3r.skquery.api.Description;
import com.w00tmast3r.skquery.api.Examples;
import com.w00tmast3r.skquery.api.Name;
import com.w00tmast3r.skquery.api.Patterns;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@Name("Visibility")
@Description("Show or hide players from each other")
@Examples("command /ghosts:;->trigger:;->->hide all players from all players")
@Patterns({"hide %players% (to|from) %players%",
        "reveal %players% (to|from) %players%"})
public class EffPlayerVisibility extends Effect {

    private Expression<Player> t, s;
    private int match;

    @SuppressWarnings("deprecation")
    @Override
    protected void execute(Event event) {
        for (Player tr : t.getAll(event)) {
            for (Player sr : s.getAll(event)) {
                if (match == 0) sr.hidePlayer(tr);
                else if (match == 1) sr.showPlayer(tr);
                else assert false;
            }
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return "hide";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        t = (Expression<Player>) expressions[0];
        s = (Expression<Player>) expressions[1];
        match = i;
        return true;
    }
}