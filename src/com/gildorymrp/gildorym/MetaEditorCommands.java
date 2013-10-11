package com.gildorymrp.gildorym;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
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
		// signitem: Signs the user's name onto the item

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
				ItemStack item = ((Player) sender).getItemInHand();
				if (item == null || item.getType() == Material.AIR) {
					sender.sendMessage(ChatColor.RED
							+ "You must have an item in hand!");
					return true;
				}
				String displayName = "";
				for (String arg : args) {
					displayName += mcFormat(arg) + " ";
				}
				item.getItemMeta().setDisplayName(displayName.trim());
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
				ItemStack item = ((Player) sender).getItemInHand();
				if (item == null || item.getType() == Material.AIR) {
					sender.sendMessage(ChatColor.RED
							+ "You must have an item in hand!");
					return true;
				}
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
				item.getItemMeta().setLore(lore);
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
				ItemStack item = ((Player) sender).getItemInHand();
				if (item == null || item.getType() == Material.AIR) {
					sender.sendMessage(ChatColor.RED
							+ "You must have an item in hand!");
					return true;
				}
				String loreText = "";
				for (String arg : args) {
					loreText += mcFormat(arg) + " ";
				}
				String[] loreTextLines = loreText.split("\\|");
				ItemMeta metadata = item.getItemMeta();
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
				item.getItemMeta().setLore(lore);
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
				ItemStack item = ((Player) sender).getItemInHand();
				if (item == null || item.getType() == Material.AIR) {
					sender.sendMessage(ChatColor.RED
							+ "You have an item in hand!");
					return true;
				}
				List<String> lore = new ArrayList<String>();
				item.getItemMeta().setLore(lore);
				sender.sendMessage(ChatColor.GRAY
						+ "The item's lore text has been removed.");
				return true;
			}
		} else if (cmd.getName().equalsIgnoreCase("signitem")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED
						+ "Only a player can use this command!");
				return true;
			} else {
				ItemStack item = ((Player) sender).getItemInHand();
				if (item == null || item.getType() == Material.AIR) {
					sender.sendMessage(ChatColor.RED
							+ "You must have an item in hand!");
					return true;
				}
				String playername = ((Player) sender).getName();
				ItemMeta metadata = item.getItemMeta();
				List<String> lore;
				if (metadata.hasLore()) {
					lore = metadata.getLore();
				} else {
					lore = new ArrayList<String>();
				}
				lore.add(ChatColor.GRAY + "[ Event Item -" + playername + " ]");
				item.getItemMeta().setLore(lore);
				sender.sendMessage(ChatColor.GRAY + "The item has been signed.");
			}
		}
		return false;
	}

	private String mcFormat(String str) {
		return str.replace('&', '�');
	}

}
