package com.ernestorb.tablistmanager.loaders;

/**
 * Enum that represents the number of lines showed on the tablist. Used to build player's packets.
 */
public enum LatencyEnum {
    ZERO(-1),
    ONE(1001),
    TWO(700),
    THREE(500),
    FOUR(200),
    FIVE(100);


    private final int latency;

    LatencyEnum(int latency) {
        this.latency = latency;
    }

    public int getLatency() {
        return latency;
    }
}
