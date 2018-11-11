package com.w00tmast3r.skquery.elements.conditions;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Checker;
import ch.njol.util.Kleenean;
import com.w00tmast3r.skquery.api.Description;
import com.w00tmast3r.skquery.api.Name;
import com.w00tmast3r.skquery.api.Patterns;
import org.bukkit.event.Event;

@Name("Is Divisible")
@Description("Checks whether or not a number can be divided into another number.")
@Patterns({"%number% is divisible by %number%", "%number% is not divisible by %number%"})
public class CondIsDivisible extends Condition {

    private Expression<Number> number, divisible;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        number = (Expression<Number>) expressions[0];
        divisible = (Expression<Number>) expressions[1];
        setNegated(matchedPattern == 1);
        return true;
    }

    @Override
    public boolean check(final Event event) {
        return number.check(event, new Checker<Number>() {
            @Override
            public boolean check(final Number in) {
                return ((in.floatValue() % divisible.getSingle(event).floatValue()) == 0);
            }
        }, isNegated());
    }

    @Override
    public String toString(Event event, boolean b) {
        return "is divisible";
    }

}