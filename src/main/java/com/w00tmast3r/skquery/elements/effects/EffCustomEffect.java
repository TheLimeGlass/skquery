package com.w00tmast3r.skquery.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.w00tmast3r.skquery.api.Disabled;
import com.w00tmast3r.skquery.elements.events.lang.CustomEffectEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.Collection;

@Disabled
public class EffCustomEffect extends Effect {

    private static final ArrayList<String> custom = new ArrayList<>();
    private Expression<?>[] expressions;
    private String execute;

    public static void addAll(Collection<String> effects) {
        for (String effect : effects) if (!custom.contains(effect)) custom.add(effect);
    }

    public static ArrayList<String> getCustom() {
        return custom;
    }

    public static void register() {
        Skript.registerEffect(EffCustomEffect.class, custom.toArray(new String[custom.size()]));
    }

    @Override
    protected void execute(Event e) {
        Bukkit.getPluginManager().callEvent(new CustomEffectEvent(execute, expressions, e));
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "custom effect";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        expressions = exprs;
        execute = custom.get(matchedPattern);
        return true;
    }
}
