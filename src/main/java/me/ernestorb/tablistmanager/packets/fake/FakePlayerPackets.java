package me.ernestorb.tablistmanager.packets.fake;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import me.ernestorb.tablistmanager.packets.PacketSender;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

public class FakePlayerPackets implements PacketSender {

    private PacketContainer packet;
    private final ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

    protected FakePlayerPackets(UUID uuid, String playerName, String displayText) {}

    public void setPacket(PacketContainer packet) {
        this.packet = packet;
    }

    public ProtocolManager getProtocolManager() {
        return protocolManager;
    }

    @Override
    public void sendPacketOnce(Player player) throws InvocationTargetException {
        this.protocolManager.sendServerPacket(player,this.packet);
    }
}
