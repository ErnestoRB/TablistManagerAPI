package me.ernestorb.tablistmanager.listener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import me.ernestorb.tablistmanager.packets.TablistAddPlayerPacket;
import me.ernestorb.tablistmanager.packets.TablistRemovePlayerPacket;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

public class BukkitListener implements Listener {


    @EventHandler
    public void onPlayerChangeWorldEvent(PlayerChangedWorldEvent evt) {
        Player evtPlayer = evt.getPlayer();
        World fromWorld = evt.getFrom();
        World toWorld = evt.getPlayer().getWorld();

        // Remove player to old world players
        List<Player> playersOnFromWorld = Bukkit.getOnlinePlayers().stream().filter(player -> player.getWorld().equals(fromWorld)).collect(Collectors.toList());
        playersOnFromWorld.forEach(player -> {
            try {
                new TablistRemovePlayerPacket(evtPlayer).sendPacketOnce(player);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        });

        // Add player to new world players
        List<Player> playersOnToWorld = Bukkit.getOnlinePlayers().stream().filter(player -> player.getWorld().equals(toWorld)).collect(Collectors.toList());
            playersOnToWorld.forEach(player -> {
                try {
                    new TablistAddPlayerPacket(evtPlayer).sendPacketOnce(player);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            });
            //Remove all players from old world to event player
        try {
            new TablistRemovePlayerPacket(playersOnToWorld).sendPacketOnce(evtPlayer);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        // Add new world players to event player
        try {
            new TablistAddPlayerPacket(playersOnToWorld).sendPacketOnce(evtPlayer);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


    }

}
