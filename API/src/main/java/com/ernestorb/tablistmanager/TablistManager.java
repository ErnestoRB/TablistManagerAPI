package com.ernestorb.tablistmanager;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.ernestorb.tablistmanager.listener.BukkitListener;
import com.ernestorb.tablistmanager.listener.PlayerInfoListener;
import com.ernestorb.tablistmanager.listener.PlayerRemoveListener;
import com.ernestorb.tablistmanager.loaders.ConfigLoader;
import com.ernestorb.tablistmanager.packets.TablistHandler;
import com.ernestorb.tablistmanager.utils.VersionUtil;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

/**
 * Represents the entrypoint for the API.
 */
public final class TablistManager {

    private static final String configFileName = "tablistConfig.yml";

    private final BukkitListener listener;
    private final TablistHandler tablistHandler;
    private final ConfigLoader configLoader;
    private final JavaPlugin plugin;

    private File configFile;
    private YamlConfiguration yamlConfig;

    /**
     * Entrypoint of the API.
     * By default, the API register Listeners for both ProtocolLib and Spigot
     * @param plugin The plugin in which the API would be used
     */
    public TablistManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.tablistHandler = new TablistHandler(plugin);
        generateConfigFile();
        createYamlConfig();
        this.configLoader = new ConfigLoader(this.yamlConfig);
        this.listener = new BukkitListener(this.configLoader);
        ProtocolManager manager = ProtocolLibrary.getProtocolManager();
        if(VersionUtil.isNewTablist()) {
            manager.addPacketListener(new PlayerRemoveListener(this, ListenerPriority.NORMAL, PacketType.Play.Server.PLAYER_INFO_REMOVE));
        }
        manager.addPacketListener(new PlayerInfoListener(this, ListenerPriority.NORMAL, PacketType.Play.Server.PLAYER_INFO));
        plugin.getServer().getPluginManager().registerEvents(this.listener, plugin);
    }

    private void generateConfigFile() {
        File dataFolder = new File(plugin.getDataFolder().getParentFile(), "TablistManagerAPI").getAbsoluteFile();
        if(!dataFolder.exists()){
            this.plugin.getLogger().info("Config folder 'TablistManagerAPI' doesn't exists. Creating one 4 ya.");
            if(dataFolder.mkdirs()) {
                this.plugin.getLogger().info("Config folder created");
            } else {
                this.plugin.getLogger().severe("Config folder couldn't be created (Do you have permissions?)") ;
            }
        }
        File file = new File(dataFolder, configFileName);
        this.configFile = file;
        if(!file.exists()) {
            plugin.getLogger().info("Attempting to save default config for TablistManagerAPI at " + file.getPath());
            try {
                InputStream fileContent = plugin.getResource(configFileName);
                file.createNewFile();
                FileOutputStream fs = new FileOutputStream(file.getAbsolutePath());
                fs.write(fileContent.readAllBytes());
                fs.close();
            } catch (IOException e) {
                plugin.getLogger().severe("Default config file for TablistManagerAPI couldn't be created") ;
            }
        }
    }

    public void reload() {
        this.generateConfigFile();
        this.createYamlConfig();
        this.configLoader.setFileConfiguration(this.yamlConfig);
        this.configLoader.loadFields();
        this.listener.reloadChanges();
    }

    private void createYamlConfig() {
        this.yamlConfig = YamlConfiguration.loadConfiguration(this.configFile);
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public ConfigLoader getConfigLoader() {
        return configLoader;
    }

    /**
     * @return An instance of the TablistHandler attached to this TablistManager instance
     */
    public TablistHandler getTablistHandler() {
        return tablistHandler;
    }

}
