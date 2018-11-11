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

@Name("Is Prime")
@Description("Checks whether or not a number is prime.")
@Patterns({"%number% is prime", "%number% is not prime"})
public class CondIsPrime extends Condition {

    private Expression<Number> number;

    public static boolean isPrime(int n) {
        if (n % 2 == 0) return false;
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        number = (Expression<Number>) expressions[0];
        setNegated(matchedPattern == 1);
        return true;
    }

    @Override
    public boolean check(final Event event) {
        return number.check(event, new Checker<Number>() {
            @Override
            public boolean check(final Number in) {
                return isPrime(in.intValue());
            }
        }, isNegated());
    }

    @Override
    public String toString(Event event, boolean b) {
        return "is prime";
    }

}