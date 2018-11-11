package com.w00tmast3r.skquery.elements.expressions;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.w00tmast3r.skquery.api.Patterns;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;


@Patterns("text from [url] %string%")
public class ExprURLText extends SimplePropertyExpression<String, String> {

    @Override
    protected String getPropertyName() {
        return "URL";
    }

    @SuppressWarnings("resource")
    @Override
    public String convert(String s) {
        try {
            URL url = new URL(s);
            Scanner a = new Scanner(url.openStream());
            String str = "";
            boolean first = true;
            while (a.hasNext()) {
                if (first) str = a.next();
                else str += " " + a.next();
                first = false;
            }
            return str;
        } catch (IOException ex) {
            return null;
        }
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}
