package com.ernestorb.tablistmanager.plugin.listeners;

import com.ernestorb.tablistmanager.plugin.loaders.ConfigLoader;
import com.ernestorb.tablistmanager.plugin.utils.TablistUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class TablistManager implements Listener {

    private final ConfigLoader configLoader;
    private final com.ernestorb.tablistmanager.TablistManager manager;

    public TablistManager(ConfigLoader configLoader, com.ernestorb.tablistmanager.TablistManager manager) {
        this.configLoader = configLoader;
        this.manager = manager;
    }

    @EventHandler
    public void onPlayerWorldChange(PlayerChangedWorldEvent event) {
        updateTablist(event.getPlayer());
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        updateTablist(event.getPlayer());
    }

    private void updateTablist(Player p) {
        TablistUtil.updatePlayerTablist(this.manager, this.configLoader, p);
    }
}
