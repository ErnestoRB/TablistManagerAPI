package me.ernestorb.tablistmanager.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import me.ernestorb.tablistmanager.loaders.ConfigLoader;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class TablistRemovePlayerPacket implements PacketSender{

    private PacketContainer packet;
    private final ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();


    public TablistRemovePlayerPacket(List<Player> playersToAdd) {
        this.packet = protocolManager.createPacket(PacketType.Play.Server.PLAYER_INFO);
        packet.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.REMOVE_PLAYER);
        List<PlayerInfoData> playerInfoDataList = packet.getPlayerInfoDataLists().writeDefaults().read(0);
        for(Player player : playersToAdd) {
            playerInfoDataList.add( // latency isnt important!
                    new PlayerInfoData(WrappedGameProfile.fromPlayer(player), ConfigLoader.getDefaultLatency().getLatency(), EnumWrappers.NativeGameMode.fromBukkit(player.getGameMode()), WrappedChatComponent.fromText(player.getDisplayName()))
            );
        }
        packet.getPlayerInfoDataLists().write(0,playerInfoDataList);
    }

    public TablistRemovePlayerPacket(Player player) {
        this.packet = protocolManager.createPacket(PacketType.Play.Server.PLAYER_INFO);
        packet.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.REMOVE_PLAYER);
        List<PlayerInfoData> playerInfoDataList = packet.getPlayerInfoDataLists().writeDefaults().read(0);
            playerInfoDataList.add(
                    new PlayerInfoData(WrappedGameProfile.fromPlayer(player),ConfigLoader.getDefaultLatency().getLatency(), EnumWrappers.NativeGameMode.fromBukkit(player.getGameMode()), WrappedChatComponent.fromText(player.getDisplayName()))
            );
        packet.getPlayerInfoDataLists().write(0,playerInfoDataList);
    }

    @Override
    public void sendPacketOnce(Player player) throws InvocationTargetException {
        protocolManager.sendServerPacket(player,this.packet);
    }
}
