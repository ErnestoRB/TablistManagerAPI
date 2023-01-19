package com.ernestorb.tablistmanager.packets;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

/**
 * Composes the API to map a TablistTemplate to a player.
 * Internally, a Task is created and is dispatched every second by default,
 * but it is customizable via constructor.
 */
public class TablistHandler {

    private final HashMap<Player, Tablist> tablistHashMap = new HashMap<>();
    private final JavaPlugin plugin;

    private long updatePeriod = 20;
    private BukkitTask task;

    /**
     * Internally starts a Task every 20 ticks to send a Tablist packet
     *
     * @param plugin The plugin to attach the task for
     */
    public TablistHandler(JavaPlugin plugin) {
        this.plugin = plugin;
        start();
    }

    public TablistHandler(JavaPlugin plugin, long updatePeriod) {
        this(plugin);
        this.updatePeriod = updatePeriod;
    }


    /**
     * Stop sending packets. No more updates would happen.
     */
    public void stop() {
        if (this.task != null) {
            this.task.cancel();
            this.task = null;
        }
    }

    /**
     * Start sending packets. Every change on setPlayerTablist would be reflected.
     */
    public void start() {
        if(this.task != null) {
            return;
        }
        this.task = new BukkitRunnable() {
            @Override
            public void run() {
                tablistHashMap.forEach((player, tablist) -> tablist.getPacket().sendPacketOnce(player));
            }
        }.runTaskTimer(this.plugin, 0, updatePeriod); // every second
    }


    /**
     * Displays a Tablist to a player.
     * @param player The player to show the tablist
     * @param template The template for that tablist
     */
    public void setPlayerTablist(Player player, TablistTemplate template) {
        tablistHashMap.put(player, new Tablist(template));
    }


}
