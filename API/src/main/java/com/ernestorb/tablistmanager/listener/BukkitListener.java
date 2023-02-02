package com.ernestorb.tablistmanager.listener;

import com.ernestorb.tablistmanager.packets.PacketSender;
import com.ernestorb.tablistmanager.packets.TablistAddPlayerPacket;
import com.ernestorb.tablistmanager.packets.TablistRemovePlayerPacket;
import com.ernestorb.tablistmanager.packets.fake.FakePlayer;
import com.ernestorb.tablistmanager.loaders.ConfigLoader;
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
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

public class BukkitListener implements Listener {

    private static final Deque<Player> worldChanges = new ConcurrentLinkedDeque<>();
    private final ConfigLoader configLoader;
    private final HashMap<World, List<FakePlayer>> worldPacketMap = new HashMap<>();
    private List<FakePlayer> globalPacketList = new ArrayList<>();

    public BukkitListener(ConfigLoader configLoader) {
        this.configLoader = configLoader;
        this.reloadChanges();
    }

    public void reloadChanges() {
        // remove old fakePlayers
        this.globalPacketList.forEach(fakePlayer -> Bukkit.getOnlinePlayers().forEach(onlinePlayer -> fakePlayer.getTablistRemovePacket().sendPacketOnce(onlinePlayer)));
        this.worldPacketMap.forEach((world, fakePlayers) -> fakePlayers.forEach(fakePlayer -> world.getPlayers().forEach(player -> fakePlayer.getTablistRemovePacket().sendPacketOnce(player))));
        this.globalPacketList.clear();
        this.worldPacketMap.clear();
        if (this.configLoader.isFillWithFakePlayers()) {
            if (this.configLoader.isTablistPerWorld()) {
                Bukkit.getWorlds().forEach(world ->
                        {
                            worldPacketMap.put(world, generateFakePlayerList(this.configLoader.getFillUntil() - world.getPlayers().size()));
                            worldPacketMap.get(world).forEach(fakePlayer -> world.getPlayers().forEach(player -> fakePlayer.getTablistAddPacket().sendPacketOnce(player)));
                        }
                );
                // generate a FakePlayerList for each world
            } else {
                this.globalPacketList = generateFakePlayerList(this.configLoader.getFillUntil() - Bukkit.getOnlinePlayers().size());
                globalPacketList.forEach(fakePlayer -> Bukkit.getOnlinePlayers().forEach(player -> fakePlayer.getTablistAddPacket().sendPacketOnce(player)));

            }
        }

    }

    @EventHandler
    public void onWorldLoad(WorldLoadEvent event) {
        if (this.configLoader.isFillWithFakePlayers() && this.configLoader.isTablistPerWorld()) {
            this.worldPacketMap.put(event.getWorld(), generateFakePlayerList(this.configLoader.getFillUntil()));
        }
    }

    @EventHandler
    public void onWorldUnload(WorldUnloadEvent event) {
        if (this.configLoader.isTablistPerWorld() && this.configLoader.isFillWithFakePlayers()) {
            this.worldPacketMap.remove(event.getWorld());
        }
    }

    @EventHandler
    public void onPlayerJoinEventForFakePlayerPurposes(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        World world = p.getWorld();
        if (this.configLoader.isFillWithFakePlayers()) {
            if (this.configLoader.isTablistPerWorld()) {
                List<FakePlayer> fakePlayers = this.worldPacketMap.get(world);
                if (fakePlayers.size() == 0) return; // No left fake players to show
                FakePlayer removedFakePlayer = fakePlayers.remove(0); // remove one from new World
                world.getPlayers().forEach(player -> {
                    removedFakePlayer.getTablistRemovePacket().sendPacketOnce(player); // remove fakeplayer from players
                });
                fakePlayers.forEach(fakePlayer -> {
                    fakePlayer.getTablistAddPacket().sendPacketOnce(p); // send fake players to event player
                });
            } else {
                if (this.globalPacketList.size() == 0) return;
                FakePlayer removedFakePlayer = this.globalPacketList.remove(0);
                Bukkit.getOnlinePlayers().forEach(player -> removedFakePlayer.getTablistRemovePacket().sendPacketOnce(player));
                this.globalPacketList.forEach(fakePlayer -> fakePlayer.getTablistAddPacket().sendPacketOnce(p));
            }
        }
    }

    @EventHandler
    public void onPlayerLeaveEventForFakePlayerPurposes(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        World world = p.getWorld();
        if (this.configLoader.isFillWithFakePlayers()) {
            if (this.configLoader.isTablistPerWorld()) {
                List<FakePlayer> fakePlayers = this.worldPacketMap.get(world);
                if (world.getPlayers().size() - 1 >= this.configLoader.getFillUntil())
                    return; // if its above limit then don't add any fake player
                FakePlayer newFakePlayer = FakePlayer.randomFakePlayer();
                fakePlayers.add(newFakePlayer);
                world.getPlayers().forEach(player -> newFakePlayer.getTablistAddPacket().sendPacketOnce(player));
            } else {
                if (Bukkit.getOnlinePlayers().size() - 1 >= this.configLoader.getFillUntil()) return;
                FakePlayer newFakePlayer = FakePlayer.randomFakePlayer();
                this.globalPacketList.add(newFakePlayer);
                Bukkit.getOnlinePlayers().forEach(player -> newFakePlayer.getTablistAddPacket().sendPacketOnce(player));
            }
        }
    }


    // For filling the tablist with fake players
    @EventHandler
    public void onPlayerChangeWorldEventForFakePlayerPurposes(PlayerChangedWorldEvent evt) {
        Player evtPlayer = evt.getPlayer();
        World fromWorld = evt.getFrom();
        World toWorld = evtPlayer.getWorld();
        if (this.configLoader.isTablistPerWorld() && this.configLoader.isFillWithFakePlayers()) {
            List<FakePlayer> oldWorldFakePlayers = this.worldPacketMap.get(fromWorld); // old world
            List<FakePlayer> newWorldfakePlayers = this.worldPacketMap.get(toWorld); // new world
            if (newWorldfakePlayers.size() != 0) {
                FakePlayer removedFakePlayer = newWorldfakePlayers.remove(0); // remove one from new World
                toWorld.getPlayers().forEach(player -> { // update tablist from new world players
                    removedFakePlayer.getTablistRemovePacket().sendPacketOnce(player);
                });
                newWorldfakePlayers.forEach(fakePlayer -> { // add FakePlayers of new World to event Player
                    fakePlayer.getTablistAddPacket().sendPacketOnce(evtPlayer);
                });
            }

            if (fromWorld.getPlayers().size() < this.configLoader.getFillUntil()) { // if under limit then add one!
                FakePlayer addedFakePlayer = FakePlayer.randomFakePlayer(); // create new FakePlayer for old world
                oldWorldFakePlayers.add(addedFakePlayer); // dont forget to put it on the list!!
                fromWorld.getPlayers().forEach(player -> { // send packet to players from that world
                    addedFakePlayer.getTablistAddPacket().sendPacketOnce(player);
                });
            }

            oldWorldFakePlayers.forEach(fakePlayer -> {// remove FakePlayers of old World to event Player
                fakePlayer.getTablistRemovePacket().sendPacketOnce(evtPlayer);
            });

        }
    }


    @EventHandler
    public void onPlayerChangeWorldEvent(PlayerChangedWorldEvent evt) {
        if (!this.configLoader.isTablistPerWorld()) {
            return;
        }
        Player evtPlayer = evt.getPlayer();
        World oldWorld = evt.getFrom();
        World newWorld = evtPlayer.getWorld();
        PacketSender tablistRemovePacket = new TablistRemovePlayerPacket(evtPlayer);
        // Remove player to old world players
        List<Player> oldWorldPlayers = oldWorld.getPlayers();
        oldWorldPlayers.forEach(tablistRemovePacket::sendPacketOnce);
        //Remove all players from old world to event player
        new TablistRemovePlayerPacket(oldWorldPlayers).sendPacketOnce(evtPlayer);
        PacketSender tablistAddPacket = new TablistAddPlayerPacket(evtPlayer);
        PacketSender tablistAddPacket2 = new TablistAddPlayerPacket(newWorld.getPlayers());
        newWorld.getPlayers().forEach(tablistAddPacket::sendPacketOnce);
        tablistAddPacket2.sendPacketOnce(evtPlayer);
    }

    private static List<FakePlayer> generateFakePlayerList(int size) {
        List<FakePlayer> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(FakePlayer.randomFakePlayer());
        }
        return list;
    }

    public static Deque<Player> getWorldChanges() {
        return worldChanges;
    }
}
