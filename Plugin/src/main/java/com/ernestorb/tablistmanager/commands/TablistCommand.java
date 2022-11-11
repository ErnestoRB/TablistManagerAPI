package com.ernestorb.tablistmanager.commands;

import com.ernestorb.tablistmanager.Main;
import com.ernestorb.tablistmanager.TablistManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TablistCommand implements CommandExecutor {

    private final TablistManager manager;

    public TablistCommand(Main plugin) {
        this.manager = plugin.getManager();
    }


    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!commandSender.isOp() || !commandSender.hasPermission("tablistmanager.*")) {
            return true;
        }
        if(strings.length == 1) {
            if(strings[0].equalsIgnoreCase("reload")) {
                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aPlugin reloaded!"));
                this.manager.reload();
                return true;
            }
        }
        return false;
    }
}
