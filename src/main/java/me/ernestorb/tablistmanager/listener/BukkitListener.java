package me.ernestorb.tablistmanager.listener;

import me.ernestorb.tablistmanager.loaders.ConfigLoader;
import me.ernestorb.tablistmanager.packets.TablistAddPlayerPacket;
import me.ernestorb.tablistmanager.packets.TablistRemovePlayerPacket;
import me.ernestorb.tablistmanager.packets.fake.FakePlayer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldUnloadEvent;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class BukkitListener implements Listener {


    private final ConfigLoader configLoader;
    private final HashMap<World,List<FakePlayer>> worldPacketMap = new HashMap<>();
    private List<FakePlayer> globalPacketList = new ArrayList<>();

    public BukkitListener(ConfigLoader configLoader) {
        this.configLoader = configLoader;
        if(this.configLoader.isFillWithFakePlayers()){
            if(this.configLoader.isTablistPerWorld()) {
                Bukkit.getWorlds().forEach(world -> worldPacketMap.put(world, generateFakePlayerList(this.configLoader.getFillUntil())));
                // generate a FakePlayerList for each world
            } else {
                globalPacketList = generateFakePlayerList(this.configLoader.getFillUntil());
            }
        }
    }

    @EventHandler
    public void onWorldLoad(WorldLoadEvent event){
        if(this.configLoader.isFillWithFakePlayers() && this.configLoader.isTablistPerWorld()) {
            this.worldPacketMap.put(event.getWorld(), generateFakePlayerList(this.configLoader.getFillUntil()));
        }
    }

    @EventHandler
    public void onWorldUnload(WorldUnloadEvent event){
        if(this.configLoader.isTablistPerWorld() && this.configLoader.isFillWithFakePlayers()) {
            this.worldPacketMap.remove(event.getWorld());
        }
    }

    @EventHandler
    public void onPlayerJoinEventForFakePlayerPurposes(PlayerJoinEvent event){
        Player p = event.getPlayer();
        World world = p.getWorld();
        if(this.configLoader.isFillWithFakePlayers() ){
            if(this.configLoader.isTablistPerWorld()){
                List<FakePlayer> fakePlayers =  this.worldPacketMap.get(world);
                if(fakePlayers.size()==0) return; // No left fake players to show
                FakePlayer removedFakePlayer = fakePlayers.remove(0); // remove one from new World
                world.getPlayers().forEach(player -> {
                    try {
                        removedFakePlayer.getTablistRemovePacket().sendPacketOnce(player); // remove fakeplayer from players
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });
                fakePlayers.forEach(fakePlayer -> {
                    try {
                        fakePlayer.getTablistAddPacket().sendPacketOnce(p); // send fake players to event player
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });
            } else {
                if(this.globalPacketList.size()==0)  return;
                FakePlayer removedFakePlayer = this.globalPacketList.remove(0);
                Bukkit.getOnlinePlayers().forEach(player -> {
                    try {
                        removedFakePlayer.getTablistRemovePacket().sendPacketOnce(player);
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });
                this.globalPacketList.forEach(fakePlayer -> {
                    try {
                        fakePlayer.getTablistAddPacket().sendPacketOnce(p);
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }

    @EventHandler
    public void onPlayerLeaveEventForFakePlayerPurposes(PlayerQuitEvent event){
        Player p = event.getPlayer();
        World world = p.getWorld();
        if(this.configLoader.isFillWithFakePlayers()){
            if(this.configLoader.isTablistPerWorld()) {
                List<FakePlayer> fakePlayers =  this.worldPacketMap.get(world);
                if(world.getPlayers().size()-1 >= this.configLoader.getFillUntil()) return; // if its above limit then don't add any fake player
                FakePlayer newFakePlayer = FakePlayer.randomFakePlayer();
                fakePlayers.add(newFakePlayer);
                world.getPlayers().forEach(player -> {
                    try {
                        newFakePlayer.getTablistAddPacket().sendPacketOnce(player);
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });
            } else {
                if(Bukkit.getOnlinePlayers().size()-1 >= this.configLoader.getFillUntil()) return;
                FakePlayer newFakePlayer = FakePlayer.randomFakePlayer();
                this.globalPacketList.add(newFakePlayer);
                Bukkit.getOnlinePlayers().forEach(player -> {
                    try {
                        newFakePlayer.getTablistAddPacket().sendPacketOnce(player);
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }


    @EventHandler
    public void onPlayerChangeWorldEventForFakePlayerPurposes(PlayerChangedWorldEvent evt) {
        Player evtPlayer = evt.getPlayer();
        World fromWorld = evt.getFrom();
        World toWorld = evtPlayer.getWorld();
        if(this.configLoader.isTablistPerWorld() && this.configLoader.isFillWithFakePlayers()) {
            List<FakePlayer> oldWorldFakePlayers = this.worldPacketMap.get(fromWorld); // old world
            List<FakePlayer> newWorldfakePlayers = this.worldPacketMap.get(toWorld); // new world
            if(newWorldfakePlayers.size()!=0){
                FakePlayer removedFakePlayer = newWorldfakePlayers.remove(0); // remove one from new World
                toWorld.getPlayers().forEach(player -> { // update tablist from new world players
                    try {
                        removedFakePlayer.getTablistRemovePacket().sendPacketOnce(player);
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });
                newWorldfakePlayers.forEach(fakePlayer -> { // add FakePlayers of new World to event Player
                    try {
                        fakePlayer.getTablistAddPacket().sendPacketOnce(evtPlayer);
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });
            }

            if(fromWorld.getPlayers().size() < this.configLoader.getFillUntil()){ // if under limit then add one!
                FakePlayer addedFakePlayer = FakePlayer.randomFakePlayer(); // create new FakePlayer for old world
                oldWorldFakePlayers.add(addedFakePlayer); // dont forget to put it on the list!!!!!!!!!!!
                fromWorld.getPlayers().forEach(player -> { // send packet to players from that world
                    try {
                        addedFakePlayer.getTablistAddPacket().sendPacketOnce(player);
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });
            }

            oldWorldFakePlayers.forEach(fakePlayer -> {// remove FakePlayers of old World to event Player
                try {
                    fakePlayer.getTablistRemovePacket().sendPacketOnce(evtPlayer);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            });

        } else {

        }
    }



    @EventHandler
    public void onPlayerChangeWorldEvent(PlayerChangedWorldEvent evt) {
        if(!this.configLoader.isTablistPerWorld()){
            return;
        }
        Player evtPlayer = evt.getPlayer();
        World fromWorld = evt.getFrom();
        World toWorld = evt.getPlayer().getWorld();

        // Remove player to old world players
        List<Player> playersOnFromWorld = Bukkit.getOnlinePlayers().stream().filter(player -> player.getWorld().equals(fromWorld)).collect(Collectors.toList());
        playersOnFromWorld.forEach(player -> {
            try {
                new TablistRemovePlayerPacket(evtPlayer).sendPacketOnce(player);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        });

        // Add player to new world players
        List<Player> playersOnToWorld = Bukkit.getOnlinePlayers().stream().filter(player -> player.getWorld().equals(toWorld)).collect(Collectors.toList());
            playersOnToWorld.forEach(player -> {
                try {
                    new TablistAddPlayerPacket(evtPlayer).sendPacketOnce(player);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            });
            //Remove all players from old world to event player
        try {
            new TablistRemovePlayerPacket(playersOnToWorld).sendPacketOnce(evtPlayer);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        // Add new world players to event player
        try {
            new TablistAddPlayerPacket(playersOnToWorld).sendPacketOnce(evtPlayer);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


    }

    private static List<FakePlayer> generateFakePlayerList(int size){
        List<FakePlayer> list = new ArrayList<>();
        for(int i=0;i<size;i++){
            list.add(FakePlayer.randomFakePlayer());
        }
        return list;
    }

}
