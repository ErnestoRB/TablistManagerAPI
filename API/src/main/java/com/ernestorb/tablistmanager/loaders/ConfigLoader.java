package com.ernestorb.tablistmanager.loaders;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

/**
 * Represents the in-memory configuration for the API features.
 */
public class ConfigLoader {

    private FileConfiguration fileConfiguration;
    private File path;

    private boolean tablistPerWorld;
    private boolean realLatency = false;
    private int fillUntil;
    private boolean fillWithFakePlayers;
    private static LatencyEnum defaultLatency = LatencyEnum.ONE;

    /**
     * @param config The config file to read values from.
     * @deprecated
     */
    @Deprecated
    public ConfigLoader(FileConfiguration config) {
        if (config == null) {
            throw new NullPointerException("Config file couldn't be loaded! :(");
        }
        this.fileConfiguration = config;
        this.loadFields();
    }

    public ConfigLoader(File path) {
        this.path = path;
        this.newConfigFile();
        this.loadFields();
    }

    /**
     * Establishes the configuration file to be used for the next loadFields()
     *
     * @param fileConfiguration The previously loaded file
     * @deprecated
     */
    @Deprecated
    public void setFileConfiguration(FileConfiguration fileConfiguration) {
        this.fileConfiguration = fileConfiguration;
    }

    private void newConfigFile() {
        if (this.path == null) return;
        this.fileConfiguration = YamlConfiguration.loadConfiguration(this.path);
    }

    public void reloadFields() {
        if (this.path == null) return;
        this.newConfigFile();
        this.loadFields();
    }

    /**
     * Loads the values from the config file
     */
    public void loadFields() {
        this.tablistPerWorld = this.fileConfiguration.getBoolean("tablistPerWorld");
        this.realLatency = this.fileConfiguration.getBoolean("useRealLatency");
        defaultLatency = LatencyEnum.valueOf(this.fileConfiguration.getString("defaultLatency", "ONE"));
        this.fillWithFakePlayers = this.fileConfiguration.getBoolean("fillWithFakePlayers");
        this.fillUntil = this.fileConfiguration.getInt("fillUntil");
    }

    public boolean isTablistPerWorld() {
        return tablistPerWorld;
    }

    public static LatencyEnum getDefaultLatency() {
        return defaultLatency;
    }

    public int getFillUntil() {
        return fillUntil;
    }

    public boolean isFillWithFakePlayers() {
        return fillWithFakePlayers;
    }

    public boolean isRealLatency() {
        return realLatency;
    }
}


