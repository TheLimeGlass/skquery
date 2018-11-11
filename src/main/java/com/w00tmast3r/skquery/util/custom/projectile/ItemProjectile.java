package com.w00tmast3r.skquery.util.custom.projectile;

import com.w00tmast3r.skquery.SkQuery;
import com.w00tmast3r.skquery.util.CancellableBukkitTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class ItemProjectile {


    private ItemStack projectile;

    public ItemProjectile(ItemStack projectile) {
        this.projectile = projectile;
    }

    public ItemStack getProjectile() {
        return projectile;
    }

    public void setProjectile(ItemStack projectile) {
        this.projectile = projectile;
    }

    public ItemProjectile shoot(final LivingEntity shooter, Vector velocity) {
        final org.bukkit.entity.Item proj = shooter.getWorld().dropItem(shooter.getEyeLocation(), projectile);
        proj.setVelocity(velocity);
        proj.setPickupDelay(Integer.MAX_VALUE);
        CancellableBukkitTask taskBukkit = new CancellableBukkitTask() {
            @Override
            public void run() {
                if (!proj.isValid() || (proj.getNearbyEntities(0, 0, 0).size() != 0 && !proj.getNearbyEntities(0, 0, 0).contains(shooter)) || proj.isOnGround()) {
                    Bukkit.getPluginManager().callEvent(new ItemProjectileHitEvent(proj, shooter));
                    proj.remove();
                    cancel();
                }
            }
        };
        taskBukkit.setTaskId(Bukkit.getScheduler().scheduleSyncRepeatingTask(SkQuery.getInstance(), taskBukkit, 0, 1));
        return this;
    }

}
