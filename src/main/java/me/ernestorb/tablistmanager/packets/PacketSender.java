package me.ernestorb.tablistmanager.packets;

import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public interface PacketSender {

   void sendPacketOnce(Player player) throws InvocationTargetException;

}
