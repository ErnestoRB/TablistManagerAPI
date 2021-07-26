package me.ernestorb.tablistmanager.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import me.ernestorb.tablistmanager.placeholders.PlayerPlaceholders;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class TabListPacket implements PacketSender {

    private final TablistTemplate tablistTemplate;
    private ProtocolManager manager = ProtocolLibrary.getProtocolManager();
    private PacketContainer packet;


    protected TabListPacket(TablistTemplate tablistTemplate) {
        this.packet = this.manager.createPacket(PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER);
        this.tablistTemplate = tablistTemplate;
    }

    @Override
    public void sendPacketOnce(Player player) throws InvocationTargetException {
        this.tablistTemplate.getPlaceholderCallback().callback(this.tablistTemplate, player);
        PlayerPlaceholders.getCallback().callback(this.tablistTemplate,player);
        this.packet.getChatComponents().write(0, WrappedChatComponent.fromText(this.tablistTemplate.getHeader()));
        this.packet.getChatComponents().write(1, WrappedChatComponent.fromText(this.tablistTemplate.getFooter()));
        this.manager.sendServerPacket(player,this.packet);
    }
}
