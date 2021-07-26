package me.ernestorb.tablistmanager.packets;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class TablistHandler {

    private final HashMap<Player, me.ernestorb.tablistmanager.packets.Tablist> tablistHashMap = new HashMap<>();

    public TablistHandler(JavaPlugin plugin) {
        new BukkitRunnable() {
            @Override
            public void run() {
                tablistHashMap.forEach((player, tablist) -> {
                    try {
                        tablist.getPacket().sendPacketOnce(player);
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });
            }
        }.runTaskTimer(plugin,0,1); // every tick
    }


    public void setPlayerTablist(Player player, TablistTemplate template) {
        tablistHashMap.put(player, new Tablist(template));
    }


}
