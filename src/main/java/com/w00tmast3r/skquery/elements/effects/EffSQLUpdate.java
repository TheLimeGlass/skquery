package com.w00tmast3r.skquery.elements.effects;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.util.Kleenean;
import com.w00tmast3r.skquery.api.Patterns;
import com.w00tmast3r.skquery.sql.ScriptCredentials;
import org.bukkit.event.Event;

import java.io.File;
import java.sql.SQLException;
import java.sql.Statement;

@Patterns("update %string%")
public class EffSQLUpdate extends Effect {

    private File executor;
    private Expression<String> query;
    private String pool;

    @Override
    protected void execute(Event event) {
        String q = query.getSingle(event);
        if (q == null) return;
        Statement st = null;
        try {
            st = ScriptCredentials.get(executor, pool).getConnection(pool).createStatement();
            st.executeUpdate(q);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (st != null) try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return "sql query";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        if (ScriptCredentials.get(ScriptLoader.currentScript.getFile()).getConnection() == null) {
            Skript.error("Database features are disabled until the script has SQL credentials associated with it.", ErrorQuality.SEMANTIC_ERROR);
            return false;
        }
        executor = ScriptLoader.currentScript.getFile();
        query = (Expression<String>) expressions[0];
        pool = ScriptCredentials.currentPool;
        ScriptCredentials.currentPool = "default";
        return true;
    }
}
