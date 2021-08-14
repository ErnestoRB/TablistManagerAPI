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

    private File dataFolder;
    private YamlConfiguration configFile;
    private TablistHandler tablistHandler;
    private ConfigLoader configLoader;
    private JavaPlugin plugin;

    public TablistManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.tablistHandler = new TablistHandler(plugin);
        ProtocolManager manager = ProtocolLibrary.getProtocolManager();
        this.dataFolder = plugin.getDataFolder();
        File file = new File(this.dataFolder.getPath(), configFileName);
        if(!file.exists()) {
            plugin.saveResource(configFileName, true);
            System.out.println("Guardando informacion");
            file = new File(this.dataFolder.getPath(), configFileName);
        }
        this.configFile = YamlConfiguration.loadConfiguration(file);
        System.out.println("Información cargada");

        this.configLoader = new ConfigLoader(this.configFile);
        manager.addPacketListener(new PacketListener(this, ListenerPriority.NORMAL, PacketType.Play.Server.PLAYER_INFO));
        plugin.getServer().getPluginManager().registerEvents(new BukkitListener(this.getConfigLoader()), plugin);
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
