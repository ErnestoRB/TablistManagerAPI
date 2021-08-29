package me.ernestorb.tablistmanager.listener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import me.ernestorb.tablistmanager.TablistManager;
import me.ernestorb.tablistmanager.loaders.ConfigLoader;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PacketListener extends PacketAdapter {


    private ConfigLoader configLoader;

    public PacketListener(TablistManager manager, ListenerPriority listenerPriority, PacketType... types) {
        super(manager.getPlugin(), listenerPriority, types);
        this.configLoader = manager.getConfigLoader();
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        Player destinationPlayer = event.getPlayer();
        PacketContainer packetContainer = event.getPacket();

        if(packetContainer.getPlayerInfoAction().read(0) == EnumWrappers.PlayerInfoAction.ADD_PLAYER) { // if sent packet is to show up player
            List<PlayerInfoData> playerInfoDataList = packetContainer.getPlayerInfoDataLists().read(0); // sent data
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

            packetContainer.getPlayerInfoDataLists().write(0, newPlayerInfoDataList);

        }
        if(!this.configLoader.isRealLatency()) {
            if(packetContainer.getPlayerInfoAction().read(0) == EnumWrappers.PlayerInfoAction.UPDATE_LATENCY ||
                    packetContainer.getPlayerInfoAction().read(0) == EnumWrappers.PlayerInfoAction.UPDATE_GAME_MODE) {
                event.setCancelled(true);
            }
        }

/*        if(this.configLoader.isTablistPerWorld()){
            if(packetContainer.getPlayerInfoAction().read(0) == EnumWrappers.PlayerInfoAction.ADD_PLAYER) {
                List<PlayerInfoData> playerInfoDataList = packetContainer.getPlayerInfoDataLists().read(0);
                List<PlayerInfoData> newPlayerInfoDataList = new ArrayList<>();
                for(PlayerInfoData data : playerInfoDataList) {
                    Player dataPlayer = Bukkit.getPlayer(data.getProfile().getName());
                    if(dataPlayer!=null) { // real player (online one)
                        if(dataPlayer.getWorld().equals(destinationPlayer.getWorld())){ // same world
                            if(this.configLoader.isRealLatency()) {
                                newPlayerInfoDataList.add(data); // not edit the sent packet
                                continue;
                            }
                            PlayerInfoData newData  = new PlayerInfoData(data.getProfile(), this.configLoader.getDefaultLatency().getLatency() ,data.getGameMode(),data.getDisplayName()); // just edit latency
                            newPlayerInfoDataList.add(newData);
                        }
                        continue;
                    } // fake player
                    if(this.configLoader.isRealLatency()) {
                        newPlayerInfoDataList.add(data); // not edit the sent packet
                        continue;
                    }
                    // edit latency to fake player
                    PlayerInfoData newData  = new PlayerInfoData(data.getProfile(), this.configLoader.getDefaultLatency().getLatency() - 10 ,data.getGameMode(),data.getDisplayName()); // just edit latency
                    newPlayerInfoDataList.add(newData);
                    packetContainer.getPlayerInfoDataLists().write(0,newPlayerInfoDataList);
                }
            }
            return;
        }
        if(!this.configLoader.isRealLatency()) {
            if(packetContainer.getPlayerInfoAction().read(0) == EnumWrappers.PlayerInfoAction.UPDATE_LATENCY ||
                    packetContainer.getPlayerInfoAction().read(0) == EnumWrappers.PlayerInfoAction.UPDATE_GAME_MODE) {
                event.setCancelled(true);
                return;
            }
        }*/
    }

}
