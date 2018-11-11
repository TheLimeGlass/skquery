package com.w00tmast3r.skquery.elements.conditions;

import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import com.w00tmast3r.skquery.api.Description;
import com.w00tmast3r.skquery.api.Name;
import com.w00tmast3r.skquery.api.Patterns;
import org.bukkit.event.Event;

@Name("Is Block")
@Description("Checks whether or not a certain itemtype is a placeable block.")
@Patterns({"%itemtype% is [a] block", "%itemtype% (isn't|is not) not [a] block"})
public class CondIsBlock extends Condition {

    private Expression<ItemType> itemtype;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        itemtype = (Expression<ItemType>) expressions[0];
        setNegated(matchedPattern == 1);
        return true;
    }

    @Override
    public boolean check(Event event) {
        return isNegated() ? !itemtype.getSingle(event).hasBlock() : itemtype.getSingle(event).hasBlock();
    }

    @Override
    public String toString(Event event, boolean b) {
        return "is block";
    }

}