package com.w00tmast3r.skquery.elements.events.lang;

import ch.njol.skript.lang.Expression;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CustomEffectEvent extends Event implements MethodEvent, Pullable {

    private static final HandlerList handlers = new HandlerList();
    private final String match;
    private final Expression<?>[] args;
    private final Event superEvent;

    public CustomEffectEvent(String match, Expression<?>[] args, Event superEvent) {
        this.match = match;
        this.args = args;
        this.superEvent = superEvent;
    }

    public static HandlerList getHandlerList() {
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
    public Event getSuperEvent() {
        return superEvent;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
