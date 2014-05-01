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


public class NewCharacterCommand implements CommandExecutor {
	private Gildorym gildorym;
	private IEssentials ess;
	
	public NewCharacterCommand(Gildorym plugin, IEssentials essentials) {
		this.gildorym = plugin;
		this.ess = essentials;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		long time = System.currentTimeMillis();
		MySQLDatabase sqlDB = gildorym.getMySQLDatabase();
		if(!(sender instanceof Player)) {
			sender.sendMessage("You can't do that from console, silly");
			return true;
		}
		Player player = (Player) sender;
		
		
		User user = ess.getUser(player);
		try {
			user.setMoney(new BigDecimal(100));
		} catch (MaxMoneyException e) {
			// what..?
			e.printStackTrace();
		}
		
      Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "spawn " + player.getName());
      user.setNickname(player.getName());
      player.getInventory().clear();
      player.getInventory().addItem(new ItemStack[]{new ItemStack(Material.STONE_AXE, 1), new ItemStack(Material.STONE_PICKAXE, 1), new ItemStack(Material.STONE_SPADE, 1), new ItemStack(Material.STONE_SWORD, 1)});
      player.sendMessage(ChatColor.AQUA + "Your character has been reset and transported to the starting area. Ensure you write a new Character story in the Characters forum and update all your characters values before venturing forth.");
      
      GildorymCharacter oldChar = gildorym.getActiveCharacters().get(player.getName());
      if(oldChar != null) {
    	  CreatedCharacterInfo cciOld = sqlDB.getCreatedCharacterInfoByChar(oldChar.getUid());
    	  cciOld.setStoredUTC(time);
    	  sqlDB.addOrUpdateCreatedCharacterInfo(cciOld);
      }
      
      GildorymCharacter gChar = Gildorym.createDefaultCharacter(player);
      
      if(!sqlDB.saveCharacter(gChar))
    	  throw new AssertionError("Assertion failed: saving the character was not successful");
      sqlDB.setCurrentCharacter(player.getName(), gChar.getUid());

      // Get the created character id, which will have just been generated if this is the first
      // time the player has created a character

      int createdCharacterId = sqlDB.getActive(player.getName())[1];

      CreatedCharacterInfo cci = new CreatedCharacterInfo(createdCharacterId, gChar.getUid(), System.currentTimeMillis(), -1, "CREATED_BY_PLAYER");
      sqlDB.addOrUpdateCreatedCharacterInfo(cci);
      
      
      return true;
	}

}
