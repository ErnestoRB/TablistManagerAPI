package com.ernestorb.tablistmanager.loaders;

import org.bukkit.configuration.file.FileConfiguration;

/**
 * Represents the in-memory config from the config file.
 */
public class ConfigLoader {

    private FileConfiguration fileConfiguration;

    private boolean tablistPerWorld;
    private boolean realLatency = false;
    private int fillUntil;
    private boolean fillWithFakePlayers;
    private static LatencyEnum defaultLatency = LatencyEnum.ONE;

    /**
     * @param config The config file to read values from.
     */
    public ConfigLoader(FileConfiguration config) {
        if(config==null){
            throw new NullPointerException("Config file couldn't be loaded! :(");
        }
        this.fileConfiguration = config;
        this.loadFields();
    }

    /**
     * Establishes the configuration file to be used for the next loadFields()
     * @param fileConfiguration
     */
    public void setFileConfiguration(FileConfiguration fileConfiguration) {
        this.fileConfiguration = fileConfiguration;
    }

    /**
     * (Re)loads the values from the config file
     */
    public void loadFields() {
        this.tablistPerWorld = this.fileConfiguration.getBoolean("tablistPerWorld");
        this.realLatency = this.fileConfiguration.getBoolean("useRealLatency");
        if(this.fileConfiguration.get("defaultLatency")==null){
            this.fileConfiguration.set("defaultLatency",getDefaultLatency().toString());
        } else {
            defaultLatency = LatencyEnum.valueOf((String) this.fileConfiguration.get("defaultLatency")) ;
        }
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


