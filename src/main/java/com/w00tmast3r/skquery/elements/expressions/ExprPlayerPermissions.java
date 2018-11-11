package com.w00tmast3r.skquery.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.w00tmast3r.skquery.api.PropertyFrom;
import com.w00tmast3r.skquery.api.PropertyTo;
import com.w00tmast3r.skquery.api.UsePropertyPatterns;
import com.w00tmast3r.skquery.skript.PermissionsHandler;
import com.w00tmast3r.skquery.util.Collect;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.permissions.PermissionAttachment;

import java.util.ArrayList;
import java.util.Map;

@UsePropertyPatterns
@PropertyFrom("players")
@PropertyTo("permissions")
public class ExprPlayerPermissions extends SimpleExpression<String> {

    private Expression<Player> player;

    @Override
    protected String[] get(Event event) {
        Player p = player.getSingle(event);
        if (p == null || PermissionsHandler.getPermissions(p) == null || PermissionsHandler.getPermissions(p).getPermissions() == null)
            return null;
        ArrayList<String> permissions = new ArrayList<>();
        for (Map.Entry<String, Boolean> perm : PermissionsHandler.getPermissions(p).getPermissions().entrySet()) {
            if (perm.getValue()) permissions.add(perm.getKey());
        }
        return permissions.toArray(new String[permissions.size()]);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "effective permissions";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        if (!PermissionsHandler.isEnabled()) {
            Skript.error("The skQuery Permissions Manager must be enabled from a script before using permissions features!");
            return false;
        }
        player = (Expression<Player>) expressions[0];
        return true;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.ADD || mode == Changer.ChangeMode.REMOVE || mode == Changer.ChangeMode.SET)
            return Collect.asArray(String[].class);
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        if (delta == null) return;
        for (Player p : player.getAll(e)) {
            PermissionAttachment perm = PermissionsHandler.getPermissions(p);
            for (Object s : delta) {
                perm.setPermission(((String) s), mode == Changer.ChangeMode.ADD);
            }
        }
    }
}
