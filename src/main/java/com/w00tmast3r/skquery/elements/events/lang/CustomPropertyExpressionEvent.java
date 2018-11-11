package com.w00tmast3r.skquery.elements.events.lang;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import com.w00tmast3r.skquery.elements.expressions.ExprCustomPropertyExpression;
import com.w00tmast3r.skquery.util.Collect;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CustomPropertyExpressionEvent extends Event implements MethodEvent, Returnable, Pullable {

    private static HandlerList handlers = new HandlerList();
    private final String match;
    private final Expression<?>[] args;
    private final Class<?> expectedOutput;
    private final ExprCustomPropertyExpression.Pattern pattern;
    private final Event superEvent;
    private Object[] returnValue = null;

    public CustomPropertyExpressionEvent(String match, Expression<?>[] args, Class<?> expectedOutput, ExprCustomPropertyExpression.Pattern pattern, Event superEvent) {
        this.match = match;
        this.args = args;
        this.expectedOutput = expectedOutput;
        this.pattern = pattern;
        this.superEvent = superEvent;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public String getMatch() {
        return match;
    }

    @Override
    public Expression<?>[] getArgs() {
        return args;
    }

    @Override
    public Class<?> getExpectedOutput() {
        return expectedOutput;
    }

    public Object[] getReturn() {
        return returnValue;
    }

    @Override
    public void setReturn(Object[] returnValue) {
        if (returnValue.getClass().getComponentType().isAssignableFrom(expectedOutput)) this.returnValue = returnValue;
        else
            Skript.error(Collect.toString(returnValue) + " is not of the expected type, " + expectedOutput.getSimpleName());
    }

    @Override
    public Event getSuperEvent() {
        return superEvent;
    }

    public ExprCustomPropertyExpression.Pattern getPattern() {
        return pattern;
    }
}
