package com.ernestorb.tablistmanager;

import com.ernestorb.tablistmanager.commands.TablistCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class TablistManagerPlugin extends JavaPlugin {


    private TablistManager manager;

    @Override
    public void onEnable() {
        this.manager = new TablistManager(this);
        this.getCommand("tablist").setExecutor(new TablistCommand(this));
    }

    @Override
    public void onDisable() {
    }

    public TablistManager getManager() {
        return manager;
    }
}