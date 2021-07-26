package me.ernestorb.tablistmanager.loaders;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigLoader {

    private FileConfiguration file;

    private boolean tablistPerWorld = false;


    public ConfigLoader(FileConfiguration config) {
        this.file = config;
        this.loadFields();
    }

    public void loadFields() {
        if(this.file!=null) {
            this.tablistPerWorld = this.file.getBoolean("tablistPerWorld");
        }
    }

    public boolean isTablistPerWorld() {
        return tablistPerWorld;
    }
}
