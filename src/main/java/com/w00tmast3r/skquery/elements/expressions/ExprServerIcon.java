package com.w00tmast3r.skquery.elements.expressions;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import com.w00tmast3r.skquery.api.Patterns;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.server.ServerListPingEvent;

import java.awt.image.BufferedImage;


@Patterns("[the] server icon")
public class ExprServerIcon extends SimpleExpression<BufferedImage> {
    @Override
    protected BufferedImage[] get(Event event) {
        return null;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends BufferedImage> getReturnType() {
        return BufferedImage.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "icon";
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        if (!ScriptLoader.isCurrentEvent(ServerListPingEvent.class)) {
            Skript.error("Cannot use server icon expression outside of a server ping event", ErrorQuality.SEMANTIC_ERROR);
            return false;
        }
        return true;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        try {
            ((ServerListPingEvent) e).setServerIcon(Bukkit.loadServerIcon((BufferedImage) delta[0]));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) return CollectionUtils.array(BufferedImage.class);
        return null;
    }
}
