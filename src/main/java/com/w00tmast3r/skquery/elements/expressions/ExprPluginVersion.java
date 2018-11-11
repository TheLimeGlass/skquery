package com.w00tmast3r.skquery.elements.expressions;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.w00tmast3r.skquery.api.PropertyFrom;
import com.w00tmast3r.skquery.api.PropertyTo;
import com.w00tmast3r.skquery.api.UsePropertyPatterns;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

@UsePropertyPatterns
@PropertyFrom("string")
@PropertyTo("version")
public class ExprPluginVersion extends SimplePropertyExpression<String, String> {
    @Override
    protected String getPropertyName() {
        return "plugin version";
    }

    @Override
    public String convert(String s) {
        Plugin p = Bukkit.getPluginManager().getPlugin(s);
        return p == null ? null : p.getDescription().getVersion();
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}
