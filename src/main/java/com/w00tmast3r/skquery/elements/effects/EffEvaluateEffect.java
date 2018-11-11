package com.w00tmast3r.skquery.elements.effects;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.w00tmast3r.skquery.api.Description;
import com.w00tmast3r.skquery.api.Examples;
import com.w00tmast3r.skquery.api.Name;
import com.w00tmast3r.skquery.api.Patterns;
import com.w00tmast3r.skquery.skript.Markup;
import org.bukkit.event.Event;

@Name("Evaluate Input Effect")
@Description("Runs the input string relative to the calling trigger which invoked it.")
@Examples("command /effectcommand <text>:;->trigger:;->->evaluate argument")
@Patterns({"evaluate %string/markup%", "^%markup%"})
public class EffEvaluateEffect extends Effect {

    private Expression<?> effect;


    @SuppressWarnings("unchecked")
    @Override
    protected void execute(Event event) {
        String pre = null;
        Object o = effect.getSingle(event);
        if (o instanceof String) pre = (String) o;
        else if (o instanceof Markup) pre = o.toString();
        if (pre == null) return;
        try {
            ScriptLoader.setCurrentEvent("this", event.getClass());
            Effect e = Effect.parse(pre, null);
            ScriptLoader.deleteCurrentEvent();
            if (e == null) return;
            e.run(event);
        } catch (Exception ignored) {
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return "evaluate";
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        effect = expressions[0];
        return true;
    }
}
