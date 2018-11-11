package com.w00tmast3r.skquery.elements.expressions;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.w00tmast3r.skquery.api.Patterns;
import com.w00tmast3r.skquery.elements.conditions.CondIsPrime;


@Patterns("%number%(st|nd|rd|th) prime")
public class ExprNthPrime extends SimplePropertyExpression<Number, Integer> {
    @Override
    protected String getPropertyName() {
        return "prime";
    }

    @Override
    public Integer convert(Number n) {
        int candidate, count;
        for (candidate = 2, count = 0; count < n.intValue(); ++candidate) {
            if (CondIsPrime.isPrime(candidate)) {
                ++count;
            }
        }
        return candidate - 1;
    }

    @Override
    public Class<? extends Integer> getReturnType() {
        return Integer.class;
    }
}
