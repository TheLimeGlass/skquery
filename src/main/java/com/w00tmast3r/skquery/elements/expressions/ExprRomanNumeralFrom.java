package com.w00tmast3r.skquery.elements.expressions;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.w00tmast3r.skquery.api.PropertyFrom;
import com.w00tmast3r.skquery.api.PropertyTo;
import com.w00tmast3r.skquery.api.UsePropertyPatterns;
import com.w00tmast3r.skquery.util.RomanNumerals;

@UsePropertyPatterns
@PropertyFrom("strings")
@PropertyTo("arabic num(ber|eral)")
public class ExprRomanNumeralFrom extends SimplePropertyExpression<String, Number> {

    @Override
    protected String getPropertyName() {
        return "roman numeral";
    }

    @Override
    public Number convert(String number) {
        return RomanNumerals.fromRoman(number);
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

}
