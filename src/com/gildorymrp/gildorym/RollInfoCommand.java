package com.gildorymrp.gildorym;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gildorymrp.gildorym.stats.CharacterStats;
import com.gildorymrp.gildorym.stats.Util;

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

			double meleeAttack = cStats.getMeleeAttack();
			double meleeDefence = cStats.getMeleeDefence();
			double rangedAttack = cStats.getRangedAttack();
			double rangedDefence = cStats.getRangedDefence();
			double magicAttack = cStats.getMagicAttack();
			double magicDefence = cStats.getMagicDefence();
			double fortitude = cStats.getFortitude();
			double reflex = cStats.getReflex();
			double will = cStats.getWill();

			sender.sendMessage(ChatColor.GRAY + "======================");
			sender.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + player.getDisplayName() + "'s Roll Info");
			sender.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "Class: " + "" + Util.getClazz(player));
			sender.sendMessage(ChatColor.GRAY + "======================");
			sender.sendMessage(ChatColor.RED + "Melee   - Attack: " + ChatColor.WHITE + (int) Math.floor(meleeAttack) + ChatColor.RED + "  Defence: " + ChatColor.WHITE
					+ (int) Math.floor(meleeDefence));
			sender.sendMessage(ChatColor.RED + "Ranged - Attack: "
					+ ChatColor.WHITE + (int) Math.floor(rangedAttack)
					+ ChatColor.RED + "  Defence: " + ChatColor.WHITE
					+ (int) Math.floor(rangedDefence));
			sender.sendMessage(ChatColor.RED + "Magic   - Attack: "
					+ ChatColor.WHITE + (int) Math.floor(magicAttack)
					+ ChatColor.RED + "  Defence: " + ChatColor.WHITE
					+ (int) Math.floor(magicDefence));
			sender.sendMessage(ChatColor.RED + "Fort: " + ChatColor.WHITE
					+ "1d20+" + (int) Math.floor(fortitude));
			sender.sendMessage(ChatColor.RED + "Ref: " + ChatColor.WHITE
					+ "1d20+" + (int) Math.floor(reflex));
			sender.sendMessage(ChatColor.RED + "Will: " + ChatColor.WHITE
					+ "1d20+" + (int) Math.floor(will));
		} else {
			sender.sendMessage(ChatColor.DARK_RED
					+ "Could not find that player!");
		}
		return true;
	}

}
