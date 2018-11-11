package com.w00tmast3r.skquery.elements;

import com.w00tmast3r.skquery.api.AbstractTask;
import com.w00tmast3r.skquery.skript.EnumClassInfo;
import com.w00tmast3r.skquery.skript.TypeClassInfo;
import com.w00tmast3r.skquery.util.minecraft.MoonPhase;
import org.bukkit.Art;
import org.bukkit.FireworkEffect;
import org.bukkit.Sound;
import org.bukkit.entity.Villager;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.scoreboard.DisplaySlot;

import java.sql.ResultSet;


public class SimpleTypes extends AbstractTask {

    @Override
    public void run() {
        EnumClassInfo.create(EntityRegainHealthEvent.RegainReason.class, "regainreason").register();
        EnumClassInfo.create(FireworkEffect.Type.class, "fireworktype").register();
        EnumClassInfo.create(Villager.Profession.class, "profession").register();
        EnumClassInfo.create(InventoryType.class, "inventorytype").register();
        EnumClassInfo.create(DisplaySlot.class, "displayslot").register();
        EnumClassInfo.create(MoonPhase.class, "moonphase").register();
        EnumClassInfo.create(Sound.class, "sound").register();
        EnumClassInfo.create(Art.class, "art").register();

        TypeClassInfo.create(FireworkEffect.class, "fireworkeffect").register();
        TypeClassInfo.create(ResultSet.class, "queryresult").register();
    }

}