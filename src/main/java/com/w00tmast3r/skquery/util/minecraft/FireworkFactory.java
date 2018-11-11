package com.w00tmast3r.skquery.util.minecraft;

import com.w00tmast3r.skquery.util.Reflection;
import org.bukkit.Bukkit;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

import java.lang.reflect.Field;

public class FireworkFactory {

    private FireworkEffect[] effects = null;
    private Location loc = new Location(Bukkit.getWorlds().get(0), 0, 0, 0);

    public FireworkFactory effects(FireworkEffect... effects) {
        this.effects = effects;
        return this;
    }

    public FireworkFactory location(Location loc) {
        this.loc = loc;
        return this;
    }

    public void play() {
        Firework f = loc.getWorld().spawn(loc, Firework.class);
        FireworkMeta data = f.getFireworkMeta();
        for (FireworkEffect effect : effects) {
            data.addEffect(effect);
        }
        f.setFireworkMeta(data);
        try {
            Class<?> entityFireworkClass = Reflection.getNMSClass("EntityFireworks");
            Class<?> craftFireworkClass = Reflection.getOBCClass("entity.CraftFirework");
            Object firework = craftFireworkClass.cast(f);
            Object entityFirework = firework.getClass().getMethod("getHandle").invoke(firework);
            Field expectedLifespan = entityFireworkClass.getDeclaredField("expectedLifespan");
            Field ticksFlown = entityFireworkClass.getDeclaredField("ticksFlown");
            ticksFlown.setAccessible(true);
            ticksFlown.setInt(entityFirework, expectedLifespan.getInt(entityFirework) - 1);
            ticksFlown.setAccessible(false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}