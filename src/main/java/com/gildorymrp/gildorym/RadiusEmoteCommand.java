package com.gildorymrp.gildorym;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RadiusEmoteCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("radiusemote")) {
			if (sender.hasPermission("gildorym.radiusemote")) {
				if (args.length >= 2) {
					String message = "";

					for (int i = 1; i < args.length; ++i) {
						message += args[i] + " ";
					}

					message = message.replaceAll("&&", "¤");

					for (Player player : ((Player) sender).getLocation().getWorld().getPlayers()) {
						if (((Player) sender).getLocation().distance(player.getLocation()) <= Integer.parseInt(args[0])) {
							player.sendMessage(message);
						}
					}
				} else {
					sender.sendMessage(ChatColor.RED + "Usage: /" + cmd.getName() + " [radius] [message]");
				}
			} else {
				sender.sendMessage(ChatColor.RED + "You do not have permission!");
			}
			return true;
		}
		return false;
	}

}
