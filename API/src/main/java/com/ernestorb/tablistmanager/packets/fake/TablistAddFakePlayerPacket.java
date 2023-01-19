package com.ernestorb.tablistmanager.packets.fake;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.*;
import com.ernestorb.tablistmanager.loaders.ConfigLoader;
import com.ernestorb.tablistmanager.utils.VersionUtil;

import java.util.Collections;
import java.util.EnumSet;
import java.util.UUID;

public class TablistAddFakePlayerPacket extends FakePlayerPacket {

    protected TablistAddFakePlayerPacket(UUID uuid, String playerName, String displayText) {
        super(uuid, playerName, displayText);
        WrappedGameProfile gameProfile = new WrappedGameProfile(uuid, playerName);
        PacketContainer packet = this.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_INFO);
        setPacket(packet);
        var data = new PlayerInfoData(FakePlayerPacket.changeGameProfileSkin(gameProfile), ConfigLoader.getDefaultLatency().getLatency() + 1, EnumWrappers.NativeGameMode.CREATIVE, WrappedChatComponent.fromText(displayText));
        var infoLists = Collections.singletonList(data);
        if (VersionUtil.isNewTablist()) {
            EnumSet<EnumWrappers.PlayerInfoAction> actions = EnumSet.of(
                    EnumWrappers.PlayerInfoAction.ADD_PLAYER,
                    EnumWrappers.PlayerInfoAction.UPDATE_LATENCY,
                    EnumWrappers.PlayerInfoAction.UPDATE_LISTED, EnumWrappers.PlayerInfoAction.UPDATE_DISPLAY_NAME);
            packet.getPlayerInfoActions().write(0, actions);
            packet.getPlayerInfoDataLists().write(1, infoLists);
            return;
        }
        packet.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.ADD_PLAYER);
        packet.getPlayerInfoDataLists().write(0, infoLists);
    }

}
