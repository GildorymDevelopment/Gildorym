package com.gildorymrp.gildorym;

import java.math.BigDecimal;

import net.ess3.api.MaxMoneyException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.earth2me.essentials.IEssentials;
import com.earth2me.essentials.User;


public class CharacterCommands implements CommandExecutor {
	private static final String NOT_ENOUGH_PERMS = 
			ChatColor.RED + "You do not have enough permission to do that." + ChatColor.RESET;
	private static final String NOT_ENOUGH_PERMS_OTHER = 
			ChatColor.RED + "You do not have enough permission to do that on other people." + ChatColor.RESET;
	private static final String TOO_MANY_PARAMS = 
			ChatColor.RED + "Too many parameters for this command!" + ChatColor.RESET;
	private static final String TARGET_NOT_FOUND = 
			ChatColor.RED + "There is no user named %s on the server right now" + ChatColor.RESET;
	private static final String TARGET_FOUND = 
			ChatColor.GRAY + "Targetting %s..." + ChatColor.RESET;
	private static final String CONSOLE_NEEDS_TARGET = 
			ChatColor.RED + "Non-player command senders must specify a target" + ChatColor.RESET;
	private static final String CREATED_NEW_CHAR_OTHER = 
			ChatColor.GREEN + "%s has been forced to create a new character." + ChatColor.RESET;
	
	private Gildorym gildorym;
	private IEssentials ess;
	
	public CharacterCommands(Gildorym plugin, IEssentials essentials) {
		this.gildorym = plugin;
		this.ess = essentials;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(label.equalsIgnoreCase("newcharacter")) {
			return onNewCommand(sender, cmd, label, args);
		}
		
		return false;
	}
	public boolean onNewCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("gildorym.command.newcharacter")) {
			sender.sendMessage(NOT_ENOUGH_PERMS);
			return true;
		}
		if(args.length > 1) {
			sender.sendMessage(TOO_MANY_PARAMS);
			return false;
		}
		
		String generationMethod = null;
		Player target;
		if(args.length == 1) {
			if(!sender.hasPermission("gildorym.command.newcharacter.other")) {
				sender.sendMessage(NOT_ENOUGH_PERMS_OTHER);
				return false;
			}
			target = gildorym.getServer().getPlayer(args[0]);
			if(target == null) {
				sender.sendMessage(String.format(TARGET_NOT_FOUND, args[0]));
				return false;
			}else {
				sender.sendMessage(String.format(TARGET_FOUND, target.getName()));
				generationMethod = "Forced by " + sender.getName();
			}
		}else {
			if(!(sender instanceof Player)) {
				sender.sendMessage(CONSOLE_NEEDS_TARGET);
				return false;
			}
			target = (Player) sender;
			generationMethod = "Created by player";
		}
		
		long time = System.currentTimeMillis();
		MySQLDatabase sqlDB = gildorym.getMySQLDatabase();
		
		User user = ess.getUser(target);
		try {
			user.setMoney(new BigDecimal(100));
		} catch (MaxMoneyException e) {
			// what..?
			e.printStackTrace();
		}
		
      Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "spawn " + target.getName());
      user.setNickname(target.getName());
      target.getInventory().clear();
      target.getInventory().addItem(new ItemStack[]{new ItemStack(Material.STONE_AXE, 1), new ItemStack(Material.STONE_PICKAXE, 1), new ItemStack(Material.STONE_SPADE, 1), new ItemStack(Material.STONE_SWORD, 1)});
      target.sendMessage(ChatColor.AQUA + "Your character has been reset and transported to the starting area. Ensure you write a new Character story in the Characters forum and update all your characters values before venturing forth.");
      
      GildorymCharacter oldChar = gildorym.getActiveCharacters().get(target.getName());
      if(oldChar != null) {
    	  CreatedCharacterInfo cciOld = sqlDB.getCreatedCharacterInfoByChar(oldChar.getUid());
    	  cciOld.setStoredUTC(time);
    	  sqlDB.addOrUpdateCreatedCharacterInfo(cciOld);
      }
      
      GildorymCharacter gChar = Gildorym.createDefaultCharacter(target);
      
      if(!sqlDB.saveCharacter(gChar))
    	  throw new AssertionError("Assertion failed: saving the character was not successful");
      sqlDB.setCurrentCharacter(target.getName(), gChar.getUid());
      gildorym.getActiveCharacters().put(target.getName(), gChar);

      // Get the created character id, which will have just been generated if this is the first
      // time the player has created a character

      int createdCharacterId = sqlDB.getActive(target.getName())[1];

      CreatedCharacterInfo cci = new CreatedCharacterInfo(createdCharacterId, gChar.getUid(), System.currentTimeMillis(), -1, generationMethod);
      sqlDB.addOrUpdateCreatedCharacterInfo(cci);
      
      if(target != sender) {
    	  sender.sendMessage(String.format(CREATED_NEW_CHAR_OTHER, target.getName()));
      }
      return true;
	}

}
