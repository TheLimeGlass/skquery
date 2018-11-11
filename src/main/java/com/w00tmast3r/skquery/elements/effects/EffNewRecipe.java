package com.w00tmast3r.skquery.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.w00tmast3r.skquery.api.Description;
import com.w00tmast3r.skquery.api.Name;
import com.w00tmast3r.skquery.api.Patterns;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

@Name("Register Recipe")
@Description("Register a crafting recipe. Use air as an itemtype to indicate the slot is not used. All 9 itemtypes must be present.")
@Patterns({"register new shaped recipe for %itemtype% using %itemtype%, %itemtype%, %itemtype%, %itemtype%, %itemtype%, %itemtype%, %itemtype%, %itemtype%, %itemtype%",
        "register new shapeless recipe for %itemtype% using %itemtype%, %itemtype%, %itemtype%, %itemtype%, %itemtype%, %itemtype%, %itemtype%, %itemtype%, %itemtype%"})
public class EffNewRecipe extends Effect {

    private boolean isShapeless;
    private Expression<ItemType> out, m1, m2, m3, m4, m5, m6, m7, m8, m9;

    @SuppressWarnings("deprecation")
    @Override
    protected void execute(Event event) {
        ItemType o = out.getSingle(event);
        ItemType i1 = m1.getSingle(event);
        ItemType i2 = m2.getSingle(event);
        ItemType i3 = m3.getSingle(event);
        ItemType i4 = m4.getSingle(event);
        ItemType i5 = m5.getSingle(event);
        ItemType i6 = m6.getSingle(event);
        ItemType i7 = m7.getSingle(event);
        ItemType i8 = m8.getSingle(event);
        ItemType i9 = m9.getSingle(event);
        if (o == null || i1 == null || i2 == null || i3 == null || i4 == null || i5 == null || i6 == null || i7 == null || i8 == null || i9 == null)
            return;
        if (isShapeless) {
            ShapelessRecipe r = new ShapelessRecipe(o.getRandom());
            if (i1.getRandom().getType() != Material.AIR) r.addIngredient(i1.getRandom().getData());
            if (i2.getRandom().getType() != Material.AIR) r.addIngredient(i2.getRandom().getData());
            if (i3.getRandom().getType() != Material.AIR) r.addIngredient(i3.getRandom().getData());
            if (i4.getRandom().getType() != Material.AIR) r.addIngredient(i4.getRandom().getData());
            if (i5.getRandom().getType() != Material.AIR) r.addIngredient(i5.getRandom().getData());
            if (i6.getRandom().getType() != Material.AIR) r.addIngredient(i6.getRandom().getData());
            if (i7.getRandom().getType() != Material.AIR) r.addIngredient(i7.getRandom().getData());
            if (i8.getRandom().getType() != Material.AIR) r.addIngredient(i8.getRandom().getData());
            if (i9.getRandom().getType() != Material.AIR) r.addIngredient(i9.getRandom().getData());
            Skript.getInstance().getServer().addRecipe(r);
        } else {
            ShapedRecipe r = new ShapedRecipe(o.getRandom());
            r.shape("abc",
                    "def",
                    "ghi");
            if (i1.getRandom().getType() != Material.AIR) r.setIngredient('a', i1.getRandom().getData());
            if (i2.getRandom().getType() != Material.AIR) r.setIngredient('b', i2.getRandom().getData());
            if (i3.getRandom().getType() != Material.AIR) r.setIngredient('c', i3.getRandom().getData());
            if (i4.getRandom().getType() != Material.AIR) r.setIngredient('d', i4.getRandom().getData());
            if (i5.getRandom().getType() != Material.AIR) r.setIngredient('e', i5.getRandom().getData());
            if (i6.getRandom().getType() != Material.AIR) r.setIngredient('f', i6.getRandom().getData());
            if (i7.getRandom().getType() != Material.AIR) r.setIngredient('g', i7.getRandom().getData());
            if (i8.getRandom().getType() != Material.AIR) r.setIngredient('h', i8.getRandom().getData());
            if (i9.getRandom().getType() != Material.AIR) r.setIngredient('i', i9.getRandom().getData());
            Skript.getInstance().getServer().addRecipe(r);
        }

    }

    @Override
    public String toString(Event event, boolean b) {
        return "recipe";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        out = (Expression<ItemType>) expressions[0];
        m1 = (Expression<ItemType>) expressions[1];
        m2 = (Expression<ItemType>) expressions[2];
        m3 = (Expression<ItemType>) expressions[3];
        m4 = (Expression<ItemType>) expressions[4];
        m5 = (Expression<ItemType>) expressions[5];
        m6 = (Expression<ItemType>) expressions[6];
        m7 = (Expression<ItemType>) expressions[7];
        m8 = (Expression<ItemType>) expressions[8];
        m9 = (Expression<ItemType>) expressions[9];
        isShapeless = i == 1;
        return true;
    }
}
