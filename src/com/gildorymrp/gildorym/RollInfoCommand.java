package com.gildorymrp.gildorym;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gildorymrp.gildorym.api.stats.CharacterStats;
import com.gildorymrp.gildorym.api.stats.Util;

public class RollInfoCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		Player player;
		if (args.length == 0) {
			player = (Player) sender;
		} else {
			player = Bukkit.getServer().getPlayer(args[0]);
		}
		if (player != null) {
			
			CharacterStats cStats = new CharacterStats(player);

			double meleeAttack = Math.floor(cStats.getMeleeAttack());
			double meleeDefence = Math.floor(cStats.getMeleeDefence());
			double rangedAttack = Math.floor(cStats.getRangedAttack());
			double rangedDefence =Math.floor( cStats.getRangedDefence());
			double magicAttack = Math.floor(cStats.getMagicAttack());
			double magicDefence = Math.floor(cStats.getMagicDefence());
			double fortitude = Math.floor(cStats.getFortitude());
			double reflex = Math.floor(cStats.getReflex());
			double will = Math.floor(cStats.getWill());

			sender.sendMessage(ChatColor.GRAY + "======================");
			sender.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + player.getDisplayName() + "'s Roll Info");
			sender.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "Class: " + "" + Util.getClazz(player));
			sender.sendMessage(ChatColor.GRAY + "======================");
			sender.sendMessage(ChatColor.RED + "Melee   - Attack: " + ChatColor.WHITE + "1d20+" + (int) meleeAttack + ChatColor.RED + "  Defence: " + ChatColor.WHITE + "1d20+" + (int) meleeDefence);
			sender.sendMessage(ChatColor.RED + "Ranged - Attack: " + ChatColor.WHITE + "1d20+" + (int) rangedAttack + ChatColor.RED + "  Defence: " + ChatColor.WHITE + "1d20+" + (int) rangedDefence);
			sender.sendMessage(ChatColor.RED + "Magic   - Attack: " + ChatColor.WHITE + "1d20+" + (int) magicAttack + ChatColor.RED + "  Defence: " + ChatColor.WHITE + "1d20+" + (int) magicDefence);
			sender.sendMessage(ChatColor.RED + "Fort: " + ChatColor.WHITE + "1d20+" + (int) Math.floor(fortitude));
			sender.sendMessage(ChatColor.RED + "Ref: " + ChatColor.WHITE + "1d20+" + (int) Math.floor(reflex));
			sender.sendMessage(ChatColor.RED + "Will: " + ChatColor.WHITE + "1d20+" + (int) Math.floor(will));
		} else {
			sender.sendMessage(ChatColor.DARK_RED + "Could not find that player!");
		}
		return true;
	}

}
