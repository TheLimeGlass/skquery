package com.w00tmast3r.skquery.elements.expressions;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.w00tmast3r.skquery.api.PropertyFrom;
import com.w00tmast3r.skquery.api.PropertyTo;
import com.w00tmast3r.skquery.api.UsePropertyPatterns;
import com.w00tmast3r.skquery.util.RomanNumerals;

@UsePropertyPatterns
@PropertyFrom("numbers")
@PropertyTo("roman num(ber|eral)")
public class ExprRomanNumeralTo extends SimplePropertyExpression<Number, String> {

    @Override
    protected String getPropertyName() {
        return "roman numeral";
    }

    @Override
    public String convert(Number number) {
        return RomanNumerals.toRoman(number.intValue());
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

}
