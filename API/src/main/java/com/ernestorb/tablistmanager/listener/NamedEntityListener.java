package com.ernestorb.tablistmanager.listener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.ernestorb.tablistmanager.TablistManager;
import com.ernestorb.tablistmanager.loaders.ConfigLoader;
import com.ernestorb.tablistmanager.packets.PacketSender;
import com.ernestorb.tablistmanager.packets.TablistAddPlayerPacket;
import com.ernestorb.tablistmanager.utils.VersionUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class NamedEntityListener extends PacketAdapter {


    private final ConfigLoader configLoader;

    public NamedEntityListener(TablistManager manager, ListenerPriority listenerPriority, PacketType... types) {
        super(manager.getPlugin(), listenerPriority, types);
        this.configLoader = manager.getConfigLoader();
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        if (!configLoader.isTablistPerWorld()) return;
        var packet = event.getPacket();
        var targetPlayer = event.getPlayer();
        var entityUUID = packet.getUUIDs().read(0);
        var packetPlayer = Bukkit.getPlayer(entityUUID);
        if (packetPlayer != null) {
            if (BukkitListener.getWorldChanges().contains(targetPlayer) || BukkitListener.getWorldChanges().contains(packetPlayer)) {
                PacketSender tablistAddPacket = new TablistAddPlayerPacket(packetPlayer);
                PacketSender tablistAddPacket2 = new TablistAddPlayerPacket(targetPlayer);
                tablistAddPacket.sendPacketOnce(targetPlayer);
                tablistAddPacket2.sendPacketOnce(packetPlayer);
                BukkitListener.getWorldChanges().remove(targetPlayer);
                BukkitListener.getWorldChanges().remove(packetPlayer);
            }
        }
    }

}
