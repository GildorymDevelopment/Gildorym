package com.gildorymrp.gildorym;

import java.util.List;

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
	private static final String NULL_CHARACTER2 =
			ChatColor.YELLOW + "Your character is null, which shouldn't happen. Contact a developer. " + ChatColor.RESET;
	
	private static final String NO_WOUNDS = 
			ChatColor.GREEN + "You have no wounds! Stay healthy and live long." + ChatColor.RESET;
	
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
		}else if(label.equalsIgnoreCase("wounds")) {
			return onWoundsCommand(sender, c, label, args);
		}
		return false;
	}

	private boolean onWoundCommand(CommandSender sender, Command c, String label,
			String[] args) {
		if(!sender.hasPermission("gildorym.command.wound")) {
			sender.sendMessage(NOT_ENOUGH_PERMS);
			return true;
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
		
		Wound wound = new Wound(
				System.currentTimeMillis(), dType, damageAmount, System.currentTimeMillis() + 1000 * timeRegenSeconds, notes.toString()
				);
		gildorym.getMySQLDatabase().inflictWound(gChar, wound);
		gChar.addWound(wound);
		
		sender.sendMessage("Wound successfully inflicted!");
		return true;
	}
	private boolean onWoundsCommand(CommandSender sender, Command c, String label,
			String[] args) {
		if(!sender.hasPermission("gildorym.command.wounds")) {
			sender.sendMessage(NOT_ENOUGH_PERMS);
			return true;
		}
		GildorymCharacter gChar = gildorym.getActiveCharacters().get(sender.getName());
		gildorym.getMySQLDatabase().pruneWounds(gChar);
		if(gChar == null) {
			sender.sendMessage(NULL_CHARACTER2);
			return false;
		}
		
		List<Wound> wounds = gChar.getWounds();
		if(wounds.size() == 0) {
			sender.sendMessage(NO_WOUNDS);
			return true;
		}
		
		StringBuilder msg = new StringBuilder("You have ");
		if(wounds.size() <= 1) {
			msg.append(ChatColor.GREEN);
		}else if(wounds.size() <= 2) {
			msg.append(ChatColor.YELLOW);
		}else if(wounds.size() <= 3) {
			msg.append(ChatColor.RED);
		}else {
			msg.append(ChatColor.DARK_RED);
		}
		msg.append(wounds.size()).append(ChatColor.RESET).append(" wound");
		if(wounds.size() > 1) {
			msg.append("s");
		}
		msg.append(":");
		
		sender.sendMessage(msg.toString());
		for(Wound w : wounds) {
			sender.sendMessage("  " + w.getDamageType().commandName() + " hurts for " + w.getDamageAmount() + " damage for the next " + remainingTime(w));
			sender.sendMessage("      Notes: " + w.getNotes());
		}
		return true;
	}
	
	private String remainingTime(Wound w) {
		long timeMs = (w.getTimeRegen() - System.currentTimeMillis());
		long secondsTotal = timeMs / 1000;
		
		long minutesTotal = secondsTotal / 60;
		long hoursTotal = minutesTotal / 60;
		long daysTotal = hoursTotal / 24;
		
		StringBuilder res = new StringBuilder();
		
		if(daysTotal >= 1) {
			res.append(daysTotal).append(" day");
			if(daysTotal > 1)
				res.append("s");
		}
		if(hoursTotal >= 1) {
			if(daysTotal >= 1) 
				res.append(", ");
			res.append(hoursTotal % 24).append(" hour");
			if(hoursTotal % 24 > 1)
				res.append("s");
		}
		if(minutesTotal >= 1) {
			if(hoursTotal >= 1)
				res.append(", ");
			res.append(minutesTotal % 60).append(" minute");
			if(minutesTotal % 60 > 1)
				res.append("s");
		}
		if(secondsTotal >= 1) {
			if(minutesTotal >= 1)
				res.append(", ");
			res.append(secondsTotal & 60).append(" second");
			if(secondsTotal % 60 > 1)
				res.append("s");
		}
		return res.toString();
	}

	private int safeParse(String str) {
		try {
			return Integer.valueOf(str);
		}catch(NumberFormatException e) {
			return -1;
		}
	}

}
