package me.ernestorb.tablistmanager;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import me.ernestorb.tablistmanager.listener.BukkitListener;
import me.ernestorb.tablistmanager.listener.PacketListener;
import me.ernestorb.tablistmanager.loaders.ConfigLoader;
import me.ernestorb.tablistmanager.packets.TablistHandler;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Tablist extends JavaPlugin {

    private TablistHandler tablistHandler;
    private ConfigLoader configLoader;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.getCommand("test").setExecutor(new TestCommand());
        this.tablistHandler = new TablistHandler(this);
        ProtocolManager manager = ProtocolLibrary.getProtocolManager();
        this.configLoader = new ConfigLoader(this.getConfig());
        manager.addPacketListener(new PacketListener(this, ListenerPriority.NORMAL, PacketType.Play.Server.PLAYER_INFO));
        this.getServer().getPluginManager().registerEvents(new BukkitListener(this.getConfigLoader()),this);
    }

    @Override
    public void onDisable() {
    }

    public TablistHandler getTablistHandler() {
        return tablistHandler;
    }

    public ConfigLoader getConfigLoader() {
        return configLoader;
    }
}
