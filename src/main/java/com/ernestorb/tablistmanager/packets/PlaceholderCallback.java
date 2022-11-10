package com.ernestorb.tablistmanager.packets;

import org.bukkit.entity.Player;

@FunctionalInterface
public interface PlaceholderCallback {

    void callback(TablistTemplate tablistTemplate, Player player);

}
