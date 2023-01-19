package com.ernestorb.tablistmanager.listener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.ernestorb.tablistmanager.TablistManager;

public class PlayerRemoveListener extends PacketAdapter {

    public PlayerRemoveListener(TablistManager manager, ListenerPriority listenerPriority, PacketType... types) {
        super(manager.getPlugin(), listenerPriority, types);
    }

    @Override
    public void onPacketSending(PacketEvent event) {}

}
