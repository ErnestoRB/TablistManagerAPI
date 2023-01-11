package com.ernestorb.tablistmanager.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.ernestorb.tablistmanager.utils.PlaceholdersUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Represents a packet to show the Tablist to the client. It's required to define a TablistTemplate before the packet gets built.
 */
public class TabListPacket implements PacketSender {

    private final TablistTemplate tablistTemplate;
    private final ProtocolManager manager = ProtocolLibrary.getProtocolManager();
    private final PacketContainer packet;

    /**
     * @param tablistTemplate The tablist to build the packet from.
     */
    protected TabListPacket(TablistTemplate tablistTemplate) {
        this.packet = this.manager.createPacket(PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER);
        this.tablistTemplate = tablistTemplate;
    }

    /**
     * Send the tablist packet one time to a player
     *
     * @param player Destiny player
     */
    @Override
    public void sendPacketOnce(Player player) {
        PlaceholdersUtil.compose(PlaceholdersUtil.getPlayerPlaceholders(), PlaceholdersUtil.getServerPlaceholders(), this.tablistTemplate.getPlaceholderCallback()).callback(this.tablistTemplate, player);
        this.packet.getChatComponents().write(0, WrappedChatComponent.fromText(ChatColor.translateAlternateColorCodes('&', this.tablistTemplate.getHeader())));
        this.packet.getChatComponents().write(1, WrappedChatComponent.fromText(ChatColor.translateAlternateColorCodes('&', this.tablistTemplate.getFooter())));
        this.manager.sendServerPacket(player, this.packet);
    }
}
