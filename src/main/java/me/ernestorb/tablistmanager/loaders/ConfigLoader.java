package me.ernestorb.tablistmanager.loaders;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigLoader {

    private FileConfiguration file;

    private boolean tablistPerWorld;
    private boolean realLatency = false;
    private int fillUntil;
    private boolean fillWithFakePlayers;
    private static LatencyEnum defaultLatency = LatencyEnum.ONE;

    public ConfigLoader(FileConfiguration config) {
        if(config==null){
            throw new NullPointerException("No debe ser nulo el archivo!");
        }
        this.file = config;
        this.loadFields();
    }

    public void loadFields() {
        this.tablistPerWorld = this.file.getBoolean("tablistPerWorld");
        this.realLatency = this.file.getBoolean("useRealLatency");
        if(this.file.get("defaultLatency")==null){
            this.file.set("defaultLatency",getDefaultLatency());
        } else {
            this.defaultLatency = (LatencyEnum) this.file.get("defaultLatency");
        }
        this.fillWithFakePlayers = this.file.getBoolean("fillWithFakePlayers");
        this.fillUntil = this.file.getInt("fillUntil");
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


