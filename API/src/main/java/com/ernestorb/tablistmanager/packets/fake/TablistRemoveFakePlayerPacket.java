package com.ernestorb.tablistmanager.packets.fake;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.*;
import com.ernestorb.tablistmanager.loaders.ConfigLoader;
import com.ernestorb.tablistmanager.utils.VersionUtil;
import org.bukkit.GameMode;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class TablistRemoveFakePlayerPacket extends FakePlayerPacket {


    protected TablistRemoveFakePlayerPacket(UUID uuid, String playerName, String displayText) {
        super(uuid, playerName,displayText);
        if(VersionUtil.isNewTablist()) {
            PacketContainer packet = this.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_INFO_REMOVE);
            packet.getLists(Converters.passthrough(UUID.class)).write(0, Collections.singletonList(uuid));
            this.setPacket(packet);
            return;
        }
        PacketContainer packet = this.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_INFO);
        packet.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.REMOVE_PLAYER);
        List<PlayerInfoData> playerInfoDataList = packet.getPlayerInfoDataLists().writeDefaults().read(0);
        WrappedGameProfile gameProfile = new WrappedGameProfile(uuid,playerName);
        playerInfoDataList.add( // latency here isnt important
                new PlayerInfoData(FakePlayerPacket.changeGameProfileSkin(gameProfile), ConfigLoader.getDefaultLatency().getLatency() + 100, EnumWrappers.NativeGameMode.fromBukkit(GameMode.CREATIVE), WrappedChatComponent.fromText(displayText))
        );
        packet.getPlayerInfoDataLists().write(0,playerInfoDataList);
        this.setPacket(packet);
    }
}
