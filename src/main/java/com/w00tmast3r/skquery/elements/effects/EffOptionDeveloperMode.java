package com.w00tmast3r.skquery.elements.effects;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.lang.SkriptParser;
import com.w00tmast3r.skquery.SkQuery;
import com.w00tmast3r.skquery.api.Description;
import com.w00tmast3r.skquery.api.Examples;
import com.w00tmast3r.skquery.api.Name;
import com.w00tmast3r.skquery.api.Patterns;
import com.w00tmast3r.skquery.elements.effects.base.OptionsPragma;
import com.w00tmast3r.skquery.util.CancellableBukkitTask;
import com.w00tmast3r.skquery.util.Collect;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Name("Developer Mode Option")
@Description("Enable the developer mode pragma to auto reload a script as it changes.  This must be placed under the script-local options.")
@Examples("script options:;->$ developer mode")
@Patterns("$ developer mode")
public class EffOptionDeveloperMode extends OptionsPragma {

    private long lastUpdated;

    @Override
    protected void register(final File executingScript, final SkriptParser.ParseResult parseResult) {
        lastUpdated = executingScript.lastModified();
        CancellableBukkitTask task = new CancellableBukkitTask() {
            @SuppressWarnings("deprecation")
            @Override
            public void run() {
                if (lastUpdated != executingScript.lastModified()) {
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&7[&6skQuery&7] &r(Dev Mode) Starting auto-reload of script '" + executingScript.getName() + "'"));
                    try {
                        Method unloadScript = ScriptLoader.class.getDeclaredMethod("unloadScript", File.class);
                        unloadScript.setAccessible(true);
                        unloadScript.invoke(null, executingScript);
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    ScriptLoader.loadScripts(Collect.asArray(executingScript));
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&7[&6skQuery&7] &r(Dev Mode) '" + executingScript.getName() + "' has been reloaded."));
                    cancel();
                }
            }
        };
        task.setTaskId(Bukkit.getScheduler().scheduleSyncRepeatingTask(SkQuery.getInstance(), task, 0, 100));
    }
}
