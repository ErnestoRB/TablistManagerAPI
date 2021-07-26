package me.ernestorb.tablistmanager.placeholders;

import me.ernestorb.tablistmanager.packets.PlaceholderCallback;

public class PlayerPlaceholders {

    private PlayerPlaceholders(){}

    public static PlaceholderCallback getCallback(){
        return ((tablistTemplate, player) -> {
            tablistTemplate.setHeader(tablistTemplate.getHeader().replaceAll("%player_name%", player.getName()));
            tablistTemplate.setHeader(tablistTemplate.getHeader().replaceAll("%player_world%", player.getLocation().getWorld().getName()));
        });
    }
}
