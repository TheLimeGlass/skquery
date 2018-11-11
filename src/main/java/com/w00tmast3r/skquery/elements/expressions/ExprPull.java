package com.w00tmast3r.skquery.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.w00tmast3r.skquery.api.Patterns;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Event;

import java.io.File;
import java.io.IOException;


@Patterns("value [of] %string% pulled from %string%")
public class ExprPull extends SimpleExpression<String> {

    private Expression<String> cfg, path;

    @Override
    protected String[] get(Event event) {
        String c = cfg.getSingle(event);
        String p = path.getSingle(event);
        if (c == null || p == null) return null;
        File file = new File(Skript.getInstance().getDataFolder() + "/scripts/" + c + ".yml");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileConfiguration fcfg = YamlConfiguration.loadConfiguration(file);
        try {
            return new String[]{fcfg.get(p).toString()};
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "pull";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        path = (Expression<String>) expressions[0];
        cfg = (Expression<String>) expressions[1];
        return true;
    }
}
