package me.ernestorb.tablistmanager.packets.fake;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.*;
import me.ernestorb.tablistmanager.loaders.ConfigLoader;
import me.ernestorb.tablistmanager.loaders.LatencyEnum;
import me.ernestorb.tablistmanager.packets.PacketSender;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

public class TablistAddFakePlayerPacket extends FakePlayerPackets {


    protected TablistAddFakePlayerPacket(UUID uuid,String playerName, String displayText) {
        super(uuid,playerName,displayText);
        PacketContainer packet = this.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_INFO);
        packet.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.ADD_PLAYER);
        List<PlayerInfoData> playerInfoDataList = packet.getPlayerInfoDataLists().writeDefaults().read(0);
        WrappedGameProfile gameProfile = new WrappedGameProfile(uuid,playerName);

        playerInfoDataList.add(
                new PlayerInfoData(FakePlayerPackets.changeGameProfileSkin(gameProfile), ConfigLoader.getDefaultLatency().getLatency() + 100, EnumWrappers.NativeGameMode.fromBukkit(GameMode.CREATIVE), WrappedChatComponent.fromText(displayText))
        );
        packet.getPlayerInfoDataLists().write(0,playerInfoDataList);
        this.setPacket(packet);
    }



}
