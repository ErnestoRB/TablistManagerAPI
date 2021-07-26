package me.ernestorb.tablistmanager.listener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class PacketListener extends PacketAdapter {


    public PacketListener(Plugin plugin, ListenerPriority listenerPriority, PacketType... types) {
        super(plugin, listenerPriority, types);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        Player destination = event.getPlayer();
        PacketContainer packetContainer = event.getPacket();
        if(packetContainer.getPlayerInfoAction().read(0) == EnumWrappers.PlayerInfoAction.ADD_PLAYER) {
            List<PlayerInfoData> newPlayerInfoDataList = new ArrayList<>();
            List<PlayerInfoData> playerInfoDataList = packetContainer.getPlayerInfoDataLists().read(0);
            for(PlayerInfoData data : playerInfoDataList) {
                Player dataPlayer = Bukkit.getPlayer(data.getProfile().getName());
                if(dataPlayer!=null) { // not fake
                    if(dataPlayer.getWorld().equals(destination.getWorld())){
                        PlayerInfoData newData  = new PlayerInfoData(data.getProfile(),-1,data.getGameMode(),data.getDisplayName());
                        newPlayerInfoDataList.add(newData);
                    }
                }

            }
            packetContainer.getPlayerInfoDataLists().write(0,newPlayerInfoDataList);
            return;
        }
        if(packetContainer.getPlayerInfoAction().read(0) == EnumWrappers.PlayerInfoAction.UPDATE_LATENCY) {
            event.setCancelled(true);
            return;
        }

    }

}
