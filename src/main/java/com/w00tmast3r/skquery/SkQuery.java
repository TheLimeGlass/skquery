package com.w00tmast3r.skquery;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import com.w00tmast3r.skquery.elements.events.EvtLambdaWhen;
import com.w00tmast3r.skquery.skript.DynamicEnumTypes;
import com.w00tmast3r.skquery.skript.SkqFileRegister;
import com.w00tmast3r.skquery.sql.ScriptCredentials;
import com.w00tmast3r.skquery.util.custom.menus.v2_.FormattedSlotManager;
import com.w00tmast3r.skquery.util.custom.note.MidiUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class SkQuery extends JavaPlugin {

    public static Boolean LIME_EDIT = true;
    private static SkQuery instance;
    private static SkriptAddon addonInstance;
    private static Metrics metrics;

    public static String cc(String colour) {
        return ChatColor.translateAlternateColorCodes('&', colour);
    }

    public static SkriptAddon getAddonInstance() {
        return addonInstance;
    }

    public static SkQuery getInstance() {
        return instance;
    }

    public static Metrics getMetrics() {
        return metrics;
    }

    @Override
    public void onEnable() {
        instance = this;
        DynamicEnumTypes.register();
        getDataFolder().mkdirs();
        addonInstance = Skript.registerAddon(this);
        Registration.enableSnooper();
        Bukkit.getPluginManager().registerEvents(new FormattedSlotManager(), this);
        SkqFileRegister.load();
        metrics = new Metrics(this);
    }

    @Override
    public void onDisable() {
        ScriptCredentials.clear();
        MidiUtil.dump();
        EvtLambdaWhen.limiter.clear();
    }

}