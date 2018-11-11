package com.w00tmast3r.skquery.util.custom.projectile;

import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ItemProjectileHitEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final Item projectile;
    private final LivingEntity shooter;

    public ItemProjectileHitEvent(Item projectile, LivingEntity shooter) {
        this.projectile = projectile;
        this.shooter = shooter;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public Item getProjectile() {
        return projectile;
    }

    public LivingEntity getShooter() {
        return shooter;
    }
}
