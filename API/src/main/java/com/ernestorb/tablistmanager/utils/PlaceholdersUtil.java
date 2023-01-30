package com.ernestorb.tablistmanager.utils;

import com.ernestorb.tablistmanager.packets.PlaceholderCallback;

public class PlaceholdersUtil {

    private PlaceholdersUtil() {
    }

    /**
     * @return Callback related to the player that the tablist is gonna show to
     */
    public static PlaceholderCallback getPlayerPlaceholders() {
        return ((tablistTemplate, player) -> {
            tablistTemplate.replace("%player_display_name%", player.getDisplayName());
            tablistTemplate.replace("%player_name%", player.getName());
            tablistTemplate.replace("%player_health%", String.valueOf(player.getHealth()));
            tablistTemplate.replace("%player_max_health%", String.valueOf(player.getMaxHealth()));
            tablistTemplate.replace("%player_world%", player.getLocation().getWorld().getName());
            tablistTemplate.replace("%player_gamemode%", player.getGameMode().toString());
        });
    }

    public static PlaceholderCallback getServerPlaceholders() {
        return ((tablistTemplate, player) -> {
            tablistTemplate.replace("%server_players%", String.valueOf(player.getServer().getOnlinePlayers().size()));
            tablistTemplate.replace("%server_max_players%", String.valueOf(player.getServer().getMaxPlayers()));
            tablistTemplate.replace("%server_motd%", player.getServer().getMotd());
        });
    }

    /**
     * @param args Placeholders to compose, being the right-most the one with more priority
     * @return A new callback that internally calls the arguments in the order provided
     */
    public static PlaceholderCallback compose(PlaceholderCallback... args) {
        return ((tablistTemplate, player) -> {
            for (var placeholder : args) {
                placeholder.callback(tablistTemplate, player);
            }
        });
    }
}
