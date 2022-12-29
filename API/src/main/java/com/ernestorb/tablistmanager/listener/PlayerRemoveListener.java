package com.ernestorb.tablistmanager.listener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.ernestorb.tablistmanager.TablistManager;
import com.ernestorb.tablistmanager.loaders.ConfigLoader;

public class PlayerRemoveListener extends PacketAdapter {


    private final ConfigLoader configLoader;

    public PlayerRemoveListener(TablistManager manager, ListenerPriority listenerPriority, PacketType... types) {
        super(manager.getPlugin(), listenerPriority, types);
        this.configLoader = manager.getConfigLoader();
    }

    @Override
    public void onPacketSending(PacketEvent event) {}

}
