package com.w00tmast3r.skquery.util.custom.menus.v1_;

import com.w00tmast3r.skquery.SkQuery;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;

import java.util.List;

public class VirtualChestManager implements Listener {

    private final String inventoryName;
    private final String player;
    private final List<String>[] commands;

    public VirtualChestManager(String inventoryName, String player, List<String>[] commands) {
        this.inventoryName = inventoryName;
        this.player = player;
        this.commands = commands;
    }

    public static void startListener() {
        //    Bukkit.getPluginManager().registerEvents(new Listener() {
        //        @EventHandler
        //        public void onInventoryClick(InventoryClickEvent e) {
        //            if (e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR && e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', "&0&0&0&0&0&0&0&0"))) {
        //                e.setCurrentItem(null);
        //            }
        //        }
        //    }, SkriptPlus.me);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getCurrentItem() != null
                && e.getCurrentItem().getType() != Material.AIR
                && e.getInventory().getType() == InventoryType.CHEST
                && e.getCurrentItem().getItemMeta() != null
                && e.getCurrentItem().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', "&0&0&0&0&0&0&0&0"))
                && e.getInventory().getName().equalsIgnoreCase(inventoryName)
                && e.getWhoClicked().getName().equalsIgnoreCase(player)) {
            e.setCancelled(true);
            safeClose((Player) e.getWhoClicked());
            if (commands[e.getSlot()] != null) for (String s : commands[e.getSlot()])
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), s);
            HandlerList.unregisterAll(this);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (e.getPlayer().getName().equalsIgnoreCase(player) && e.getInventory().getName().equalsIgnoreCase(inventoryName))
            HandlerList.unregisterAll(this);
    }

    private void safeClose(final Player p) {
        Bukkit.getScheduler().runTaskLater(SkQuery.getInstance(), new Runnable() {
            @Override
            public void run() {
                p.getOpenInventory().close();
            }
        }, 1L);
    }
}
