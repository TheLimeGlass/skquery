package com.w00tmast3r.skquery.elements.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.w00tmast3r.skquery.api.Description;
import com.w00tmast3r.skquery.api.Examples;
import com.w00tmast3r.skquery.api.Name;
import com.w00tmast3r.skquery.api.Patterns;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

@Name("Clear Server Recipes")
@Description("Clears all recipes registered in the server. Use ((EffClearRecipe)this effect) to clear individual recipes.")
@Examples("on script load:;->wipe server crafting recipes")
@Patterns("wipe server crafting recipes")
public class EffClearRecipes extends Effect {

    @Override
    protected void execute(Event event) {
        Bukkit.getServer().clearRecipes();
    }

    @Override
    public String toString(Event event, boolean b) {
        return "MFG recipes";
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        return true;
    }
}
