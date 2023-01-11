package com.ernestorb.tablistmanager.plugin.commands;

import com.ernestorb.tablistmanager.TablistManager;
import com.ernestorb.tablistmanager.plugin.loaders.ConfigLoader;
import com.ernestorb.tablistmanager.plugin.utils.TablistUtil;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TablistCommand implements CommandExecutor {

    private final TablistManager manager;
    private final ConfigLoader configLoader;
    private final Server server;

    public TablistCommand(Server server, TablistManager manager, ConfigLoader configLoader) {
        this.server = server;
        this.manager = manager;
        this.configLoader = configLoader;
    }


    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!commandSender.isOp() || !commandSender.hasPermission("tablistmanager.*")) {
            return true;
        }
        if (strings.length == 1) {
            if (strings[0].equalsIgnoreCase("reload")) {
                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aPlugin reloaded!"));
                this.manager.reload();
                this.configLoader.reloadFields();
                this.server.getOnlinePlayers().forEach((player -> TablistUtil.updatePlayerTablist(this.manager, this.configLoader, player)));
                return true;
            }
        }
        return false;
    }
}
