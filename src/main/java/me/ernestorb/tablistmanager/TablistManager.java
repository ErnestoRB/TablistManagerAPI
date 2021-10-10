package me.ernestorb.tablistmanager;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import me.ernestorb.tablistmanager.listener.BukkitListener;
import me.ernestorb.tablistmanager.listener.PacketListener;
import me.ernestorb.tablistmanager.loaders.ConfigLoader;
import me.ernestorb.tablistmanager.packets.TablistHandler;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class TablistManager {


    private static final String configFileName = "tablistConfig.yml";

    private final BukkitListener listener;
    private final TablistHandler tablistHandler;
    private final ConfigLoader configLoader;
    private final JavaPlugin plugin;

    public TablistManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.tablistHandler = new TablistHandler(plugin);
        ProtocolManager manager = ProtocolLibrary.getProtocolManager();
        File dataFolder = plugin.getDataFolder();
        File file = new File(dataFolder.getPath(), configFileName);
        if(!file.exists()) {
            plugin.saveResource(configFileName, true);
            System.out.println("Guardando informacion de Tablist v"+ getPlugin().getDescription().getVersion());
            file = new File(dataFolder.getPath(), configFileName);
        }
        YamlConfiguration configFile = YamlConfiguration.loadConfiguration(file);
        System.out.println("Información cargada");

        this.configLoader = new ConfigLoader(configFile);
        manager.addPacketListener(new PacketListener(this, ListenerPriority.NORMAL, PacketType.Play.Server.PLAYER_INFO));
        this.listener = new BukkitListener(this.configLoader);
        plugin.getServer().getPluginManager().registerEvents(this.listener, plugin);
    }

    public void reload() {
        this.configLoader.loadFields();
        this.listener.reloadChanges();
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public ConfigLoader getConfigLoader() {
        return configLoader;
    }

    public TablistHandler getTablistHandler() {
        return tablistHandler;
    }

}
