package com.w00tmast3r.skquery.elements.expressions;


import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.util.Kleenean;
import com.w00tmast3r.skquery.api.Patterns;
import com.w00tmast3r.skquery.sql.ScriptCredentials;
import org.bukkit.event.Event;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Patterns("objects in column %string% from %queryresult%")
public class ExprSQLQueryObjects extends SimpleExpression<Object> {

    private Expression<ResultSet> query;
    private Expression<String> column;

    @Override
    protected Object[] get(Event event) {
        try {
            ResultSet q = query.getSingle(event);
            String c = column.getSingle(event);
            if (q == null || c == null) return null;
            ArrayList<Object> output = new ArrayList<>();
            while (q.next()) {
                output.add(q.getObject(c));
            }
            return output.toArray(new Object[output.size()]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends Object> getReturnType() {
        return Object.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "sql query";
    }

    @Override
    public boolean isLoopOf(String s) {
        return s.equals("object");
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        if (ScriptCredentials.get(ScriptLoader.currentScript.getFile()).getConnection() == null) {
            Skript.error("Database features are disabled until the script has SQL credentials associated with it.", ErrorQuality.SEMANTIC_ERROR);
            return false;
        }
        query = (Expression<ResultSet>) expressions[1];
        column = (Expression<String>) expressions[0];
        return true;
    }
}