package com.gildorymrp.gildorym;

import java.util.Random;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Gildorym extends JavaPlugin {

	public Economy economy;
	
	public void onEnable() {
		this.economy = this.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class).getProvider();
		if (this.economy == null) {
			this.getLogger().severe("Could not find a compatible economy, disabling!");
			this.setEnabled(false);
		}
		
		this.getCommand("newcharacter").setExecutor(new NewCharacterCommand());
		this.getCommand("setname").setExecutor(new SetNameCommand());
		this.getCommand("setnameother").setExecutor(new SetNameOtherCommand());
		this.getCommand("rollinfo").setExecutor(new RollInfoCommand());
		this.getCommand("radiusemote").setExecutor(new RadiusEmoteCommand());
		RollCommand rc = new RollCommand(this);
		this.getCommand("roll").setExecutor(rc);
		this.getCommand("dmroll").setExecutor(rc);
		MetaEditorCommands mec = new MetaEditorCommands();
		this.getCommand("renameitem").setExecutor(mec);
		this.getCommand("setlore").setExecutor(mec);
		this.getCommand("addlore").setExecutor(mec);
		this.getCommand("removelore").setExecutor(mec);
		this.getCommand("signitem").setExecutor(mec);
		
		this.getServer().getPluginManager().registerEvents(new EntityDamageListener(this), this);
		this.getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
		this.getServer().getPluginManager().registerEvents(new SignChangeListener(), this);
	}

	public void onInjury(Player injured, String type, int dieSize, int fallDistance, int roll) {
		ChatColor b = ChatColor.BLUE;
		ChatColor r = ChatColor.RED;
		ChatColor w = ChatColor.WHITE;
		ChatColor dr = ChatColor.DARK_RED;
		ChatColor g = ChatColor.GOLD;
		
		int severity, fallInFeet, rollPercent, severityPercent, x, y, z;
		
		fallInFeet = fallDistance * 3;
		severity = (new Random()).nextInt(dieSize) + 1;
		rollPercent = roll * 5;
		severityPercent = severity * 2;
		x = injured.getLocation().getBlockX();
		y = injured.getLocation().getBlockY();
		z = injured.getLocation().getBlockZ();
		
		String message, reflexAlert, injuryAlert, alert, injury, deathLocation;

		message = "You have fallen " + w + "" + fallInFeet + "";
		reflexAlert = r + "Reflex: " + w + rollPercent + r + "%";
		injuryAlert = r + "  Injury: " + w + severityPercent + r + "%";
		deathLocation = injured.getWorld().getName() + ": x: " + x + " y: " + y + " z: " + z;
		alert = injured.getName() + b + " has just fallen " + w + fallDistance + b + " blocks, recieving ";
		
		if(type.equalsIgnoreCase("none")){
			message += b + "feet";
			injury = " escaping without injury.";
			injured.sendMessage(reflexAlert);
			injured.sendMessage(b+ message + b + injury);
			
		} else if (type.equalsIgnoreCase("major")) {
			message += r + " feet, recieving ";
			injury = dr + "error";
			
			if (severity < 16) {
				injury = "a Punctured Organ (Anything but the Heart), Completely Crushed Limb, Cracked Skull or Cracked Vertebra; 4 Damage.";
				injured.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 35000, 3), true); 
				injured.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 35000, 3), true);
				
			} else if (severity < 31) {
				injury = "a Crushed Small limb (Such as hand/foot), Shattered Bones, or Severe Concussion; 3 Damage.";
				injured.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 35000, 3), true);
				
			} else if (severity < 51) {
				injury = "a Cleanly Broken Bone, Torn Major Muscle, or Loss of a Minor Limb; 2 Damage.";
				injured.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 35000, 2), true);
				
			}
			injured.sendMessage(reflexAlert + injuryAlert);
			injured.sendMessage(r + message + r + injury);
			
			for (Player player : Bukkit.getServer().getOnlinePlayers()) {
				if (player.hasPermission("gildorym.falldamage.alert")) {
					player.sendMessage(alert + r + injury);
					player.sendMessage(reflexAlert + r + "  Injury: " + w + severity);
				}
			}
		} else if (type.equalsIgnoreCase("minor")) {
			message += g + " feet, recieving ";
			injury = dr + "error";
			
			if (severity < 11) {
				injury = "a Minor Fracture, Brused Ribs, or Dislocation; 1 damage.";
				injured.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 35000, 1), true);
				
			} else if (severity < 21) {
				injury = "a Minor Torn Ligement, Chipped Bone, or Light Concussion; 1 damage.";
				injured.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 35000, 1), true);
				
			} else if (severity < 35) {
				injury = "a Mild Sprain, Mild Laceration, or Badly Pulled Muscle; 1 damage.";
				injured.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 15000, 1), true);
				
			} else if (severity < 50) {
				injury = "Bruises, Mild Lacerations, or Minor Sprain; 1 damage.";
				injured.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 5000, 0), true);
			} else if (severity == 50) {
				injury = "only a few scratches.";
				
			}
			injured.sendMessage(reflexAlert + injuryAlert);
			injured.sendMessage(g + message + g + injury);
			
			for (Player player : Bukkit.getServer().getOnlinePlayers()) {
				if (player.hasPermission("gildorym.falldamage.alert")) {
					player.sendMessage(alert + g + injury);
					player.sendMessage(reflexAlert + r + "  Injury: " + w + severity);
				}
			}
		} else if(type.equalsIgnoreCase("death")){
			message += dr + " feet, ";
			injury = "and died";
			injured.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 999999999, 10), true);
			
			injured.sendMessage(dr + message + dr + injury);
			for (Player player : Bukkit.getServer().getOnlinePlayers()) {
				if (player.hasPermission("gildorym.falldamage.alert")) {
					player.sendMessage(alert + dr + injury);
					player.sendMessage(reflexAlert);
					player.sendMessage(deathLocation);
				}
			}
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "warp deathbox " + injured.getName());
		} else {
			injured.sendMessage(ChatColor.RED + "Error : Injury type "
					+ ChatColor.RESET + type + ChatColor.RED
					+ " does not exist.");
		}

	}
}
