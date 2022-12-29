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
import com.ernestorb.tablistmanager.utils.VersionUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerInfoListener extends PacketAdapter {


    private final ConfigLoader configLoader;

    public PlayerInfoListener(TablistManager manager, ListenerPriority listenerPriority, PacketType... types) {
        super(manager.getPlugin(), listenerPriority, types);
        this.configLoader = manager.getConfigLoader();
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        Player destinationPlayer = event.getPlayer();
        PacketContainer packetContainer = event.getPacket();
        if(VersionUtil.isNewTablist()) {
            var action = packetContainer.getPlayerInfoActions().read(0);
            if(action.contains(EnumWrappers.PlayerInfoAction.INITIALIZE_CHAT) || action.contains(EnumWrappers.PlayerInfoAction.UPDATE_LISTED)) {
                return;
            }

        } else {
            if(packetContainer.getPlayerInfoAction().read(0) == EnumWrappers.PlayerInfoAction.REMOVE_PLAYER) {
                return;
            }
        }
        List<PlayerInfoData> playerInfoDataList = packetContainer.getPlayerInfoDataLists().read(VersionUtil.isNewTablist() ? 1 : 0); // sent data;
        List<PlayerInfoData> newPlayerInfoDataList = new ArrayList<>(); // new data
        if (this.configLoader.isTablistPerWorld()) {
            for (PlayerInfoData data : playerInfoDataList) {
                Player dataPlayer = Bukkit.getPlayer(data.getProfile().getName());
                if (dataPlayer != null) { // real player (online one)
                    if (dataPlayer.getWorld().equals(destinationPlayer.getWorld())) { // same world
                        if (this.configLoader.isRealLatency()) {
                            newPlayerInfoDataList.add(data); // not edit the sent packet
                            continue;
                        }
                        PlayerInfoData newData = new PlayerInfoData(data.getProfile(), ConfigLoader.getDefaultLatency().getLatency(), data.getGameMode(), data.getDisplayName()); // just edit latency
                        newPlayerInfoDataList.add(newData);
                    }
                } else {
                    newPlayerInfoDataList.add(data); // for fake players is not needed to check its ping
                }
            }
        } else { // global tablist
            for (PlayerInfoData data : playerInfoDataList) {
                if (this.configLoader.isRealLatency()) {
                    newPlayerInfoDataList.add(data); // not edit the sent packet
                } else {
                    PlayerInfoData newData = new PlayerInfoData(data.getProfile(), ConfigLoader.getDefaultLatency().getLatency(), data.getGameMode(), data.getDisplayName()); // just edit latency
                    newPlayerInfoDataList.add(newData);
                }
            }
        }
            packetContainer.getPlayerInfoDataLists().write(VersionUtil.isNewTablist() ? 1 : 0, newPlayerInfoDataList);

    }

}
