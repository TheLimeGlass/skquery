package com.w00tmast3r.skquery.elements.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.w00tmast3r.skquery.api.PropertyFrom;
import com.w00tmast3r.skquery.api.PropertyTo;
import com.w00tmast3r.skquery.api.UsePropertyPatterns;
import com.w00tmast3r.skquery.util.Collect;
import com.w00tmast3r.skquery.util.minecraft.MoonPhase;
import org.bukkit.World;
import org.bukkit.event.Event;

@UsePropertyPatterns
@PropertyFrom("world")
@PropertyTo("[current] moon phase")
public class ExprMoonPhase extends SimplePropertyExpression<World, MoonPhase> {

    @Override
    protected String getPropertyName() {
        return "moon phase";
    }

    @Override
    public MoonPhase convert(World world) {
        return getPhaseOf(world);
    }

    @Override
    public Class<? extends MoonPhase> getReturnType() {
        return MoonPhase.class;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        return mode == Changer.ChangeMode.SET ? Collect.asArray(MoonPhase.class) : null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        MoonPhase moonPhase = delta[0] == null ? MoonPhase.FULL_MOON : (MoonPhase) delta[0];
        World w = getExpr().getSingle(e);
        if (w == null || getPhaseOf(w) == moonPhase) return;
        while (getPhaseOf(w) != moonPhase) {
            w.setFullTime(w.getFullTime() + 24000);
        }
    }

    private MoonPhase getPhaseOf(World world) {
        long days = world.getFullTime() / 24000;
        int phase = (int) (days % 8);
        switch (phase) {
            case 0:
                return MoonPhase.FULL_MOON;
            case 1:
                return MoonPhase.WANING_GIBBOUS;
            case 2:
                return MoonPhase.LAST_QUARTER;
            case 3:
                return MoonPhase.WANING_CRESCENT;
            case 4:
                return MoonPhase.NEW_MOON;
            case 5:
                return MoonPhase.WAXING_CRESCENT;
            case 6:
                return MoonPhase.FIRST_QUARTER;
            case 7:
                return MoonPhase.WAXING_GIBBOUS;
        }
        return MoonPhase.FULL_MOON;
    }
}
