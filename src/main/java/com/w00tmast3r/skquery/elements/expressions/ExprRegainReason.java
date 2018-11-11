package com.w00tmast3r.skquery.elements.expressions;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.util.Kleenean;
import com.w00tmast3r.skquery.api.Patterns;
import com.w00tmast3r.skquery.util.Collect;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityRegainHealthEvent;

@Patterns("(regen|heal) (cause|reason)")
public class ExprRegainReason extends SimpleExpression<EntityRegainHealthEvent.RegainReason> {
    @Override
    protected EntityRegainHealthEvent.RegainReason[] get(Event event) {
        return Collect.asArray(((EntityRegainHealthEvent) event).getRegainReason());
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends EntityRegainHealthEvent.RegainReason> getReturnType() {
        return EntityRegainHealthEvent.RegainReason.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "regen reason";
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        if (!ScriptLoader.isCurrentEvent(EntityRegainHealthEvent.class)) {
            Skript.error("Heal cause can only be used in an on heal event", ErrorQuality.SEMANTIC_ERROR);
            return false;
        }
        return true;
    }
}
