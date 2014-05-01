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
	private static final String CONSOLE_NEEDS_TARGET = 
			ChatColor.RED + "Non-player command senders must specify a target" + ChatColor.RESET;
	private static final String DAMAGE_TYPE_INVALID =
			ChatColor.RED + "Valid damage types include FallDamage, PlayerAttack, MonsterAttack, Drowning, and Other" + ChatColor.RESET;
	private static final String INVALID_DAMAGE_AMOUNT =
			ChatColor.RED + "Cannot deal negative damage" + ChatColor.RESET;
	private static final String INVALID_TIME_REGEN_AMOUNT =
			ChatColor.RED + "Must last at least 1 second (-1 for forever)" + ChatColor.RESET;
	private static final String INVALID_POTION_EFFECT_NUMBER =
			ChatColor.RED + "Potion effects are between 0 (no effect) and 23" + ChatColor.RESET;
	private static final String INVALID_POTION_EFFECT_LEVEL =
			ChatColor.RED + "Potion levels must be 0 (no effect) or positive and finite" + ChatColor.RESET;
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
		 * example: /wound Tjstretchalot FallDamage 1 600 2 1 Failed a skill check
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
		
		if(args.length < 4) {
			if(args.length == 0 || args[0].equalsIgnoreCase("help")) {
				showWoundHelp(sender);
				return true;
			}else {
				return false;
			}
		}
		
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
		if(damageAmount < 0) {
			sender.sendMessage(INVALID_DAMAGE_AMOUNT);
			return false;
		}
		
		long timeRegenSeconds = safeParse(args[3]);
		if(timeRegenSeconds < -1 || timeRegenSeconds == 0) {
			sender.sendMessage(INVALID_TIME_REGEN_AMOUNT);
			return false;
		}else if(timeRegenSeconds == -1)
			timeRegenSeconds = Long.MAX_VALUE;
		
		int potionEffect = safeParse(args[4]);
		if(potionEffect < 0 || potionEffect > 23) {
			sender.sendMessage(INVALID_POTION_EFFECT_NUMBER);
			return false;
		}
		
		int potionLevel = safeParse(args[5]) - 1;
		if(potionLevel < 0) {
			sender.sendMessage(INVALID_POTION_EFFECT_LEVEL);
			return false;
		}
		
		StringBuilder notes = new StringBuilder();
		if(args.length > 6)
			notes.append(args[6]);
		for(int i = 7; i < args.length; i++) {
			notes.append(" ").append(args[i]);
		}
		
		GildorymCharacter gChar = gildorym.getActiveCharacters().get(target.getName());
		if(gChar == null) {
			sender.sendMessage(NULL_CHARACTER);
			return false;
		}
		
		Wound wound = new Wound(
				System.currentTimeMillis(), dType, damageAmount, System.currentTimeMillis() + 1000 * timeRegenSeconds, notes.toString(), potionEffect, potionLevel
				);
		gildorym.getMySQLDatabase().inflictWound(gChar, wound);
		gChar.addWound(wound);
		
		sender.sendMessage("Wound successfully inflicted!");
		return true;
	}
	
	private void showWoundHelp(CommandSender sender) {
		sender.sendMessage("Usage:   /wound ((player) [damage type] [damage amount] [time regen (seconds)] [potion effect number] [potion effect level] (notes))");
		sender.sendMessage("Example: /wound Tjstretchalot FallDamage 1 600 2 1 Failed a skill check");
		sender.sendMessage("Note that potion effect number 0 (regardless of level) will cause no effect. Similiarly, level 0 will cause no effect");
		StringBuilder line = new StringBuilder("Dmg. Types: ");
		
		DamageType[] types = DamageType.values();
		line.append(types[0].commandName());
		for(int i = 1; i < types.length; i++) {
			line.append(", ").append(types[i].commandName());
		}
		sender.sendMessage(line.toString());
	}

	private boolean onWoundsCommand(CommandSender sender, Command c, String label,
			String[] args) {
		if(!sender.hasPermission("gildorym.command.wounds")) {
			sender.sendMessage(NOT_ENOUGH_PERMS);
			return true;
		}
		
		Player target = null;
		if(args.length == 1) {
			target = sender.getServer().getPlayer(args[0]);
			if(target == null) {
				sender.sendMessage(String.format(TARGET_NOT_FOUND, args[0]));
				return false;
			}
		}else {
			if(!(sender instanceof Player)) {
				sender.sendMessage(CONSOLE_NEEDS_TARGET);
				return false;
			}
			target = (Player) sender;
		}
		GildorymCharacter gChar = gildorym.getActiveCharacters().get(target.getName());
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
		
		StringBuilder msg = new StringBuilder(target.getName());
		msg.append(" has ");
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
			sender.sendMessage("  " + w.getDamageType().commandName() + " causes " + w.getDamageAmount() + " damage for " + remainingTime(w));
			sender.sendMessage("      Effect: " + w.getPotionEffectName() + " level " + (w.getEffectLevel() + 1));
			sender.sendMessage("      Notes: " + w.getNotes());
		}
		return true;
	}
	
	private String remainingTime(Wound w) {
		if(w.getTimeRegen() == Long.MAX_VALUE)
			return "an exceptionally long time";
		
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
