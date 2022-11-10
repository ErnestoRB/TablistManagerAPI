package com.ernestorb.tablistmanager.packets;

import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

/**
 * Defines what a packet should implement.
 */
public interface PacketSender {

   void sendPacketOnce(Player player) throws InvocationTargetException;

}
