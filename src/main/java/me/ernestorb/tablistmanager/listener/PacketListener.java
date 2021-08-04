package me.ernestorb.tablistmanager.listener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import me.ernestorb.tablistmanager.Tablist;
import me.ernestorb.tablistmanager.loaders.ConfigLoader;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PacketListener extends PacketAdapter {


    private ConfigLoader configLoader;

    public PacketListener(Tablist plugin, ListenerPriority listenerPriority, PacketType... types) {
        super(plugin, listenerPriority, types);
        this.configLoader = plugin.getConfigLoader();
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        Player destinationPlayer = event.getPlayer();
        PacketContainer packetContainer = event.getPacket();

        if(this.configLoader.isTablistPerWorld()){
            if(packetContainer.getPlayerInfoAction().read(0) == EnumWrappers.PlayerInfoAction.ADD_PLAYER) {
                List<PlayerInfoData> playerInfoDataList = packetContainer.getPlayerInfoDataLists().read(0);
                List<PlayerInfoData> newPlayerInfoDataList = new ArrayList<>();
                for(PlayerInfoData data : playerInfoDataList) {
                    Player dataPlayer = Bukkit.getPlayer(data.getProfile().getName());
                    if(dataPlayer!=null) { // real player (online one)
                        if(dataPlayer.getWorld().equals(destinationPlayer.getWorld())){ // same world
                            if(!this.configLoader.isRealLatency()) { //
                                newPlayerInfoDataList.add(data); // not edit the sent packet
                                continue;
                            }
                            PlayerInfoData newData  = new PlayerInfoData(data.getProfile(), this.configLoader.getDefaultLatency().getLatency() ,data.getGameMode(),data.getDisplayName()); // just edit latency
                            newPlayerInfoDataList.add(newData);
                        }
                        continue;
                    } // fake player
                    if(!this.configLoader.isRealLatency()) { //
                        newPlayerInfoDataList.add(data); // not edit the sent packet
                        continue;
                    }
                    // edit latency to fake player
                    PlayerInfoData newData  = new PlayerInfoData(data.getProfile(), this.configLoader.getDefaultLatency().getLatency() - 10 ,data.getGameMode(),data.getDisplayName()); // just edit latency
                    newPlayerInfoDataList.add(newData);
                }
                packetContainer.getPlayerInfoDataLists().write(0,newPlayerInfoDataList);
            }
            return;
        }
        if(!this.configLoader.isRealLatency()) {
            if(packetContainer.getPlayerInfoAction().read(0) == EnumWrappers.PlayerInfoAction.UPDATE_LATENCY) {
                event.setCancelled(true);
                return;
            }
        }


    }

}
