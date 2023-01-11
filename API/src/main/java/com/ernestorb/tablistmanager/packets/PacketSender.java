package com.ernestorb.tablistmanager.packets;

import org.bukkit.entity.Player;


/**
 * Defines what a packet should implement.
 */
public interface PacketSender {

   void sendPacketOnce(Player player);

}
