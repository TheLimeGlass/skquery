package com.w00tmast3r.skquery.elements.events;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAPIException;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import com.w00tmast3r.skquery.SkQuery;
import com.w00tmast3r.skquery.skript.LambdaCondition;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.HashSet;
import java.util.Set;

public class EvtLambdaWhen extends SkriptEvent {

    public static Set<LambdaCondition> limiter = new HashSet<>();

    static {
        Skript.registerEvent("*Lambda when", EvtLambdaWhen.class, LambdaEvent.class, "when %predicate% [[with] limit[(ing|er)] %-boolean%]");

        Bukkit.getScheduler().scheduleSyncRepeatingTask(SkQuery.getInstance(), new Runnable() {
            @Override
            public void run() {
                Bukkit.getServer().getPluginManager().callEvent(new LambdaEvent());
            }
        }, 1, 1);
    }

    private LambdaCondition lambda;
    private Boolean limit;

    @Override
    public boolean check(Event e) {
        if (limit != null && lambda.check(e) && limit) {
            if (!limiter.contains(lambda)) {
                limiter.add(lambda);
            } else return false;
        }
        return lambda.check(e);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Literal<?>[] args, int matchedPattern, SkriptParser.ParseResult parseResult) {
        lambda = ((Literal<LambdaCondition>) args[0]).getSingle();
        if (args.length > 1 && ((Literal<Boolean>) args[1]) != null) {
            limit = ((Literal<Boolean>) args[1]).getSingle();
        }
        if (!LambdaCondition.class.isInstance(lambda)) {
            throw new SkriptAPIException("Invalid use of LambdaWhen: argument must be a predicate!");
        }
        return true;
    }

    @Override
    public String toString(Event arg0, boolean arg1) {
        return "Lambda when event";
    }

    static class LambdaEvent extends Event {
        private final static HandlerList handlers = new HandlerList();

        public static HandlerList getHandlerList() {
            return handlers;
        }

        @Override
        public HandlerList getHandlers() {
            return handlers;
        }
    }
}
