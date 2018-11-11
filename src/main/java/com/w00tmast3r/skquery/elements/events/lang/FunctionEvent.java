package com.w00tmast3r.skquery.elements.events.lang;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FunctionEvent extends Event implements MethodEvent {

    private static HandlerList handlers = new HandlerList();
    private final String cause;
    private final Object[] params;
    private final Event invoker;

    public FunctionEvent(String cause, Object[] params, Event invoker) {
        this.cause = cause;
        this.params = params;
        this.invoker = invoker;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public Event getInvoker() {
        return invoker;
    }

    @Override
    public String getMatch() {
        return cause;
    }

    public Object[] getParams() {
        return params;
    }
}
