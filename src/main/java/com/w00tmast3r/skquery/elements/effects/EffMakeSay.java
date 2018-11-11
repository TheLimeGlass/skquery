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

@Name("Make Say")
@Description("Force players to say text in chat. If you add a leading slash in the text, it will execute a command.")
@Examples("on chat:;->cancel event;->make a random player out of all players say message")
@Patterns("make %players% say %string%")
public class EffMakeSay extends Effect {

    private Expression<Player> players;
    private Expression<String> text;

    @Override
    protected void execute(Event event) {
        String t = text.getSingle(event);
        if (t == null) return;
        for (Player p : players.getAll(event)) {
            p.chat(t);
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return "say wat";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        players = (Expression<Player>) expressions[0];
        text = (Expression<String>) expressions[1];
        return true;
    }
}
