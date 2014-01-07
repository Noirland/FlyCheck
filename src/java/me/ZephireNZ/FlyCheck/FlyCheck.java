package me.ZephireNZ.FlyCheck;

import me.ZephireNZ.FlyPotions.FlyPotions;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class FlyCheck extends JavaPlugin {

    public FlyPotions flyPotions;

    @Override
    public void onEnable() {
        flyPotions = getFlyPotions();
    }

    public FlyPotions getFlyPotions() {
        Plugin fpPlugin = getServer().getPluginManager().getPlugin("FlyPotions");
        if(fpPlugin != null && fpPlugin instanceof FlyPotions) {
            return (FlyPotions) fpPlugin;
        }else{
            return null;
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("flycheck")) {
            if(sender.hasPermission("flycheck.check")) {
                if(args.length == 1) {
                    Player player = getServer().getPlayer(args[0]);
                    if(player != null) {
                        checkPlayer(sender, player);
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
            boolean creative = player.getGameMode().equals(GameMode.CREATIVE);
            boolean flypotion = false;
            boolean flyPotionsPlugin = flyPotions != null;
            boolean fly = false;
            if(flyPotionsPlugin) {
                if(flyPotions.flying.contains(player.getName())) {
                    flypotion = true;
                }
            }
            if(!player.getGameMode().equals(GameMode.CREATIVE) && player.getAllowFlight()) {
                if(!(flyPotionsPlugin && flypotion)) {
                    fly = true;
                }
            }
            String             sendString  = player.getName() + ChatColor.RESET + "";
            if(creative)       sendString += ChatColor.GREEN + " can fly "   + ChatColor.RESET + "because of creative.";
            else if(flypotion) sendString += ChatColor.GREEN + " can fly "   + ChatColor.RESET + "because of fly potions.";
            else if(fly)       sendString += ChatColor.GREEN + " can fly "   + ChatColor.RESET + "because of another plugin or /fly.";
            else               sendString += ChatColor.RED   + " CANNOT fly" + ChatColor.RESET + ".";

            sender.sendMessage(sendString);

    }
}
