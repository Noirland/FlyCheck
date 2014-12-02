package me.ZephireNZ.FlyCheck;

import nz.co.noirland.noirfly.NoirFly;
import nz.co.noirland.zephcore.Util;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class FlyCheck extends JavaPlugin {

    public NoirFly noirFly;

    @Override
    public void onEnable() {
        noirFly = getNoirFly();
    }

    public NoirFly getNoirFly() {
        Plugin flyPlugin = getServer().getPluginManager().getPlugin("NoirFly");
        if(flyPlugin != null && flyPlugin instanceof NoirFly) {
            return (NoirFly) flyPlugin;
        }else{
            return null;
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("flycheck")) {
            if(sender.hasPermission("flycheck.check")) {
                if(args.length == 1) {
                    OfflinePlayer player = Util.player(args[0]);
                    if(player.isOnline()) {
                        checkPlayer(sender, player.getPlayer());
                        return true;
                    }
                }
            }else{
                sender.sendMessage(ChatColor.DARK_RED + "You do not have permission to use that command.");
                return true;
            }
        }
        return false;
    }

    public void checkPlayer(CommandSender sender, Player player) {
        String sendString  = player.getName() + ChatColor.RESET + "";

        if(player.getGameMode().equals(GameMode.CREATIVE)) {
            sendString += sendString += ChatColor.GREEN + " can fly "   + ChatColor.RESET + "because of creative.";
        } else if(noirFly != null && noirFly.isFlying(player.getUniqueId())) {
            sendString += ChatColor.GREEN + " can fly " + ChatColor.RESET + "because of NoirFly.";
        } else if(player.getAllowFlight()) {
            sendString += ChatColor.GREEN + " can fly " + ChatColor.RESET + "because of another plugin";
        } else {
            sendString += ChatColor.RED   + " CANNOT fly" + ChatColor.RESET + ".";
        }

        sender.sendMessage(sendString);

    }
}
