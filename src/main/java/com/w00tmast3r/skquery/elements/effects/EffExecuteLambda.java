package com.w00tmast3r.skquery.elements.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.w00tmast3r.skquery.api.Patterns;
import com.w00tmast3r.skquery.elements.expressions.ExprInput;
import com.w00tmast3r.skquery.skript.LambdaEffect;
import org.bukkit.event.Event;

@Patterns("do [%-number% time[s]] %lambda%")
public class EffExecuteLambda extends Effect {

    private Expression<LambdaEffect> effect;
    private Expression<Number> times;

    @Override
    protected void execute(Event event) {
        LambdaEffect l = effect.getSingle(event);
        if (l == null) return;
        if (times != null) {
            for (int i = 0; i < times.getSingle(event).intValue(); i++) {
                ExprInput.setInput(event, i);
                l.walk(event);
                ExprInput.removeInput(event);
            }
        } else {
            l.walk(event);
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return "evaluate do";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        times = (Expression<Number>) expressions[0];
        effect = (Expression<LambdaEffect>) expressions[1];
        return true;
    }
}
