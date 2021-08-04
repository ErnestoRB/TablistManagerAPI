package me.ernestorb.tablistmanager.loaders;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigLoader {

    private FileConfiguration file;

    private boolean tablistPerWorld = false;
    private boolean realLatency = false;
    private final short fakePlayersCount = 10;
    private LatencyEnum defaultLatency = LatencyEnum.ONE;

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
    }

    public boolean isTablistPerWorld() {
        return tablistPerWorld;
    }

    public LatencyEnum getDefaultLatency() {
        return defaultLatency;
    }

    public short getFakePlayersCount() {
        return fakePlayersCount;
    }

    public boolean isRealLatency() {
        return realLatency;
    }
}


