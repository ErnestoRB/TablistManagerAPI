package com.ernestorb.tablistmanager.plugin;

import com.ernestorb.tablistmanager.plugin.commands.TablistCommand;
import com.ernestorb.tablistmanager.TablistManager;
import com.ernestorb.tablistmanager.plugin.loaders.ConfigLoader;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class TablistManagerPlugin extends JavaPlugin {

    private TablistManager manager;

    @Override
    public void onEnable() {
        ConfigLoader configLoader = new ConfigLoader(new File(this.getDataFolder(), "config.yml"));
        this.manager = new TablistManager(this);
        this.getCommand("tablist").setExecutor(new TablistCommand(this.getServer(), this.manager, configLoader));
        this.getServer().getPluginManager().registerEvents(new com.ernestorb.tablistmanager.plugin.listeners.TablistManager(configLoader, this.manager), this);
    }

    @Override
    public void onDisable() {
    }

    public TablistManager getManager() {
        return manager;
    }
}