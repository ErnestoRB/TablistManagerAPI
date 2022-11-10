package com.ernestorb.tablistmanager.placeholders;

import com.ernestorb.tablistmanager.packets.PlaceholderCallback;

/**
 * Class use to reproduce builtin player about placeholders
 */
public class PlayerPlaceholders {

    private PlayerPlaceholders(){}

    public static PlaceholderCallback getCallback(){
        return ((tablistTemplate, player) -> {
            tablistTemplate.setHeader(tablistTemplate.getHeader().replaceAll("%player_display_name%", player.getDisplayName()));
            tablistTemplate.setHeader(tablistTemplate.getHeader().replaceAll("%player_name%", player.getName()));
            tablistTemplate.setHeader(tablistTemplate.getHeader().replaceAll("%player_health%", String.valueOf(player.getHealth())));
            tablistTemplate.setHeader(tablistTemplate.getHeader().replaceAll("%player_max_health%", String.valueOf(player.getMaxHealth())));
            tablistTemplate.setHeader(tablistTemplate.getHeader().replaceAll("%player_world%", player.getLocation().getWorld().getName()));
        });
    }
}
