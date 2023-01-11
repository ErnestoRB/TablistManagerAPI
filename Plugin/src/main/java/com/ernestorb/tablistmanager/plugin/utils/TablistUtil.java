package com.ernestorb.tablistmanager.plugin.utils;

import com.ernestorb.tablistmanager.TablistManager;
import com.ernestorb.tablistmanager.packets.TablistTemplate;
import com.ernestorb.tablistmanager.plugin.loaders.ConfigLoader;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class TablistUtil {

    public static void updatePlayerTablist(TablistManager manager, ConfigLoader configLoader, Player p) {
        var worldName = p.getWorld().getName();
        var header = configLoader.getWorldHeader(worldName);
        var footer = configLoader.getWorldFooter(worldName);
        manager.getTablistHandler().setPlayerTablist(p, TablistTemplate.fromLists(header.orElse(new ArrayList<>()), footer.orElse(new ArrayList<>())));
    }
}
