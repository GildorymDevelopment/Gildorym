package com.gildorymrp.gildorym;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WoundCommand implements CommandExecutor {
	private static final String NOT_ENOUGH_PERMS = 
			ChatColor.RED + "You do not have enough permission to do that." + ChatColor.RESET;
	private static final String TARGET_NOT_FOUND = 
			ChatColor.RED + "There is no user named %s on the server right now" + ChatColor.RESET;
	private static final String DAMAGE_TYPE_INVALID =
			ChatColor.RED + "Valid damage types include FallDamage, PlayerAttack, MonsterAttack, Drowning, and Other" + ChatColor.RESET;
	private static final String INVALID_DAMAGE_AMOUNT =
			ChatColor.RED + "Must deal at least 1 damage" + ChatColor.RESET;
	private static final String INVALID_TIME_REGEN_AMOUNT =
			ChatColor.RED + "Must last at least 1 second" + ChatColor.RESET;
	private static final String NULL_CHARACTER =
			ChatColor.YELLOW + "The targets character is null, which shouldn't happen. Contact a developer. " + ChatColor.RESET;
	
	private Gildorym gildorym;
	
	public WoundCommand(Gildorym plugin) {
		gildorym = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command c, String label, String[] args) {
		/*
		 * usage: /<command> [player] [damage type] [damage amount] [time regen (seconds)] (notes)
		 * example: /wound Tjstretchalot FallDamage 1 600 Failed a skill check
		 */
		
		if(label.equalsIgnoreCase("wound")) {
			return onWoundCommand(sender, c, label, args);
		}
		return false;
	}

	private boolean onWoundCommand(CommandSender sender, Command c, String label,
			String[] args) {
		// Does the sender have the appropriate permissions?
		if(!sender.hasPermission("gildorym.command.wound")) {
			sender.sendMessage(NOT_ENOUGH_PERMS);
		}
		
		if(args.length < 4)
			return false;
		
		Player target = sender.getServer().getPlayer(args[0]);
		if(target == null) {
			sender.sendMessage(String.format(TARGET_NOT_FOUND, args[0]));
			return false;
		}
		DamageType dType = DamageType.commandName(args[1]);
		if(dType == null) {
			sender.sendMessage(DAMAGE_TYPE_INVALID);
			return false;
		}
		int damageAmount = safeParse(args[2]);
		if(damageAmount <= 0) {
			sender.sendMessage(INVALID_DAMAGE_AMOUNT);
			return false;
		}
		
		int timeRegenSeconds = safeParse(args[3]);
		if(timeRegenSeconds <= 0) {
			sender.sendMessage(INVALID_TIME_REGEN_AMOUNT);
			return false;
		}
		
		StringBuilder notes = new StringBuilder();
		if(args.length > 4)
			notes.append(args[4]);
		for(int i = 5; i < args.length; i++) {
			notes.append(" ").append(args[i]);
		}
		
		GildorymCharacter gChar = gildorym.getActiveCharacters().get(target.getName());
		if(gChar == null) {
			sender.sendMessage(NULL_CHARACTER);
			return false;
		}
		
		return true;
	}
	
	private int safeParse(String str) {
		try {
			return Integer.valueOf(str);
		}catch(NumberFormatException e) {
			return -1;
		}
	}

}
