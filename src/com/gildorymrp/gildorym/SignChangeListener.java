package com.gildorymrp.gildorym;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignChangeListener implements Listener {

	@EventHandler
	public void onSignChange(SignChangeEvent event) {
		if (event.getLine(0).equalsIgnoreCase("[boat]")) {
			if (event.getPlayer().hasPermission("gildorym.boatsigns.create")) {
				event.setLine(0, ChatColor.BLUE + "[boat]");
			} else {
				event.getBlock().breakNaturally();
				event.getPlayer().sendMessage(ChatColor.RED + "You do not have permission to create boat signs!");
			}
		}
		//Untested and temporary automated door lock making
		if (event.getLine(0).equalsIgnoreCase("[lock]")) {
			if (event.getPlayer().hasPermission("gildorym.action.makelock")) {
				String quality = ChatColor.RED + "Error";
				if (event.getLine(1) != null) {
					
					
					if (event.getLine(1).equalsIgnoreCase("cheap")) {
						quality = "[1b]";
					}else if (event.getLine(1).equalsIgnoreCase("decent")) {
						quality = "[2c]";
					}else if (event.getLine(1).equalsIgnoreCase("moderate")) {
						quality = "[3d]";
					}else if (event.getLine(1).equalsIgnoreCase("epic")) {
						quality = "[4e]";
					}else if (event.getLine(1).equalsIgnoreCase("legendary")) {
						quality = "[5f]";
					}else if (event.getLine(1).equalsIgnoreCase("magic")) {
						quality = "[6a]";
					}
					event.setLine(1, quality);
					if (!quality.contains("Error"))
					event.setLine(1, ChatColor.GOLD + quality);
				}else if (event.getLine(2) != null || event.getLine(3) != null) {
					event.setLine(1, quality);
					
					event.getPlayer().sendMessage("Do not fill out lines 3 or 4. Lock the sign before you leave.");
				}
			}			
		}
	}

}
