package com.gildorymrp.gildorym;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.gildorymrp.charactercards.GildorymCharacterCards;
import com.gildorymrp.gildorymclasses.GildorymClasses;

public class NewCharacterCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "money set " + sender.getName() + " 100");
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "spawn " + sender.getName());
        GildorymCharacterCards characterCards = (GildorymCharacterCards) Bukkit.getServer().getPluginManager().getPlugin("GildorymCharacterCards");
        characterCards.getCharacterCards().remove(sender.getName());
        GildorymClasses gildorymClasses = (GildorymClasses) Bukkit.getServer().getPluginManager().getPlugin("GildorymClasses");
        //Bukkit.getServer().dispatchCommand(sender, "hero reset");
        //Bukkit.getServer().dispatchCommand(sender, "hero confirm");
        gildorymClasses.classes.remove(sender.getName());
        gildorymClasses.levels.remove(sender.getName());
        gildorymClasses.professions.remove(sender.getName());
        gildorymClasses.experience.remove(sender.getName());
        //Bukkit.getServer().dispatchCommand(sender, "gods leave");
        Bukkit.getServer().dispatchCommand(sender, "nick " + sender.getName());
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "clearinventory " + sender.getName());
        ((Player) sender).getInventory().addItem(new ItemStack[]{new ItemStack(Material.STONE_AXE, 1), new ItemStack(Material.STONE_PICKAXE, 1), new ItemStack(Material.STONE_SPADE, 1), new ItemStack(Material.STONE_SWORD, 1)});
        sender.sendMessage(ChatColor.AQUA + "Your character has been reset and transported to the starting area. Ensure you write a new Character story in the Characters forum and update all your chracters values before venturing forth.");
		return true;
	}

}
