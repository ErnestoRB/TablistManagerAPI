package me.ernestorb.tablistmanager;

import me.ernestorb.tablistmanager.packets.fake.FakePlayer;
import me.ernestorb.tablistmanager.packets.fake.TablistAddFakePlayerPacket;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class TestCommand implements CommandExecutor {

    private static HashMap<Player, FakePlayer> testMap = new HashMap<>();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(args.length==1) {
                switch (args[0]){
                    case "add":
                        if(!testMap.containsKey(p)){
                            testMap.put(p,FakePlayer.randomFakePlayer());
                        }
                        try {
                            testMap.get(p).getTablistAddPacket().sendPacketOnce(p);
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "remove":
                        try {
                            testMap.get(p).getTablistRemovePacket().sendPacketOnce(p);
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }

        }
        return true;
    }
}
