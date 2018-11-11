package com.w00tmast3r.skquery.api;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.util.SimpleEvent;
import com.w00tmast3r.skquery.Documentation;
import org.bukkit.event.Event;

/**
 * A runnable with a set of tools that help for registering events.
 */
public abstract class AbstractTask implements Runnable {

    protected void registerEvent(String name, Class<? extends Event> event, String... patterns) {
        registerEvent(name, SimpleEvent.class, event, patterns);
    }

    protected void registerEvent(String name, Class<? extends SkriptEvent> handler, Class<? extends Event> event, final String... patterns) {
        Documentation.addEvent(event, patterns);
        Skript.registerEvent(name, handler, event, patterns);
    }

}