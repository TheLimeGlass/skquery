package com.w00tmast3r.skquery.elements.conditions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import java.io.File;

@Name("File existance")
@Description("Checks whether or not a file at the defined path(s) exist.")
public class CondFileExistance extends Condition {

    static {
        Skript.registerCondition(CondFileExistance.class, "file %string% (1¦does|2¦does(n't| not)) exist",
                "existance of [file] %string% is %boolean%"); //Umbaska's shitty syntax.
    }

    private Expression<String> files;
    private Expression<Boolean> check;

    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        files = (Expression<String>) expressions[0];
        setNegated(parseResult.mark == 1);
        if (expressions.length > 1) check = (Expression<Boolean>) expressions[0];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "file existance" + files != null ? files.toString(event, debug) : "";
    }

    @Override
    public boolean check(Event event) {
        if (files == null) return !isNegated();
        Boolean negated = (check != null) ? check.getSingle(event) : isNegated();
        File file = new File(files.getSingle(event));
        return file.exists() ? negated : !negated;
    }

}