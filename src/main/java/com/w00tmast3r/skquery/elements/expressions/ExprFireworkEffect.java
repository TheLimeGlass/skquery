package com.w00tmast3r.skquery.elements.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.w00tmast3r.skquery.api.Patterns;
import com.w00tmast3r.skquery.util.Collect;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.event.Event;


@Patterns({"(1¦|2¦flickering |3¦trailing |4¦flickering trailing |5¦trailing flickering )%fireworktype% [firework] [effect] colo[u]red %rgbcolors%",
        "(1¦|2¦flickering |3¦trailing |4¦flickering trailing |5¦trailing flickering )%fireworktype% [firework] [effect] colo[u]red %rgbcolors% fad(e|ing) [to] %rgbcolors%",
        "(1¦|2¦flickering |3¦trailing |4¦flickering trailing |5¦trailing flickering )%fireworktype% [firework] [effect] colo[u]red %rgbcolors%",
        "(1¦|2¦flickering |3¦trailing |4¦flickering trailing |5¦trailing flickering )%fireworktype% [firework] [effect] colo[u]red %rgbcolors% fad(e|ing) [to] %rgbcolors%"})
public class ExprFireworkEffect extends SimpleExpression<FireworkEffect> {

    private Expression<FireworkEffect.Type> type;
    private Expression<Color> color, fade;
    private boolean flicker;
    private boolean trail;
    private boolean hasFade;

    @Override
    protected FireworkEffect[] get(Event event) {
        FireworkEffect.Type t = type.getSingle(event);
        if (t == null) return null;
        FireworkEffect.Builder builder = FireworkEffect.builder();
        builder.with(t);
        for (Color c : color.getAll(event)) {
            builder.withColor(c);
        }
        if (hasFade) {
            for (Color c : fade.getAll(event)) {
                builder.withFade(c);
            }
        }
        builder.flicker(flicker);
        builder.trail(trail);
        return Collect.asArray(builder.build());
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends FireworkEffect> getReturnType() {
        return FireworkEffect.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "ssss pop";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        flicker = parseResult.mark == 2 || parseResult.mark > 3;
        trail = parseResult.mark >= 3;
        hasFade = i == 1;
        type = (Expression<FireworkEffect.Type>) expressions[0];
        color = (Expression<Color>) expressions[1];
        if (hasFade) {
            fade = (Expression<Color>) expressions[2];
        }
        return true;
    }
}
