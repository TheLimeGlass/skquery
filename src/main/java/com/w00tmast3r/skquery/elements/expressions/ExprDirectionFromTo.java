package com.w00tmast3r.skquery.elements.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.Direction;
import ch.njol.util.Kleenean;
import com.w00tmast3r.skquery.api.Patterns;
import com.w00tmast3r.skquery.util.Collect;
import org.bukkit.Location;
import org.bukkit.event.Event;


@Patterns("[the] direction from %location% to %location%")
public class ExprDirectionFromTo extends SimpleExpression<Direction> {

    private Expression<Location> from, to;

    @Override
    protected Direction[] get(Event event) {
        Location f = from.getSingle(event);
        Location t = to.getSingle(event);
        if (f == null || t == null) return null;
        return Collect.asArray(new Direction(t.toVector().subtract(f.toVector())));
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Direction> getReturnType() {
        return Direction.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "dir fr to";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        from = (Expression<Location>) expressions[0];
        to = (Expression<Location>) expressions[1];
        return true;
    }
}
