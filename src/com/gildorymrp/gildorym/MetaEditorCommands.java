package com.gildorymrp.gildorym;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

public class MetaEditorCommands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		// This plugin handles several commands
		// renameitem: Changes the display name of an item
		// setlore: Sets the lore text on an item, overwriting current lore
		// addlore: Adds additional lore text to an item after current lore
		// removelore: Removes all lore text from an item
		if (cmd.getName().equalsIgnoreCase("renameitem")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED
						+ "Only a player can use this command!");
				return true;
			} else if (args.length == 0) {
				sender.sendMessage(ChatColor.RED
						+ "You must specify a name for the item!");
				return true;
			} else {
				String displayName = "";
				for (String arg : args) {
					displayName += mcFormat(arg) + " ";
				}
				((Player) sender).getItemInHand().getItemMeta()
						.setDisplayName(displayName.trim());
				sender.sendMessage(ChatColor.GRAY
						+ "The item's name has been changed.");
				return true;
			}
		} else if (cmd.getName().equalsIgnoreCase("setlore")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED
						+ "Only a player can use this command!");
				return true;
			} else if (args.length == 0) {
				sender.sendMessage(ChatColor.RED
						+ "You must specify lore text for the item!");
				return true;
			} else {
				String loreText = "";
				for (String arg : args) {
					loreText += mcFormat(arg) + " ";
				}
				String[] loreTextLines = loreText.split("\\|");
				List<String> lore = new ArrayList<String>();
				for (String loreTextLine : loreTextLines) {
					if (!loreTextLine.equals(null)) {
						lore.add(loreTextLine);
					}
				}
				((Player) sender).getItemInHand().getItemMeta().setLore(lore);
				sender.sendMessage(ChatColor.GRAY
						+ "The item's lore text has been set.");
				return true;
			}
		} else if (cmd.getName().equalsIgnoreCase("addlore")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED
						+ "Only a player can use this command!");
				return true;
			} else if (args.length == 0) {
				sender.sendMessage(ChatColor.RED
						+ "You must specify lore text for the item!");
				return true;
			} else {
				String loreText = "";
				for (String arg : args) {
					loreText += mcFormat(arg) + " ";
				}
				String[] loreTextLines = loreText.split("\\|");
				ItemMeta metadata = ((Player) sender).getItemInHand()
						.getItemMeta();
				List<String> lore;
				if (metadata.hasLore()) {
					lore = metadata.getLore();
				} else {
					lore = new ArrayList<String>();
				}
				for (String loreTextLine : loreTextLines) {
					if (!loreTextLine.equals(null)) {
						lore.add(loreTextLine);
					}
				}
				((Player) sender).getItemInHand().getItemMeta().setLore(lore);
				sender.sendMessage(ChatColor.GRAY
						+ "The item's lore text has been added to.");
				return true;
			}
		} else if (cmd.getName().equalsIgnoreCase("removelore")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED
						+ "Only a player can use this command!");
				return true;
			} else {
				List<String> lore = new ArrayList<String>();
				((Player) sender).getItemInHand().getItemMeta().setLore(lore);
				sender.sendMessage(ChatColor.GRAY
						+ "The item's lore text has been removed.");
				return true;
			}
		}
		return false;
	}

	private String mcFormat(String str) {
		return str.replace('&', '§');
	}

}
