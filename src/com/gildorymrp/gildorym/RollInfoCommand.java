package com.gildorymrp.gildorym;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gildorymrp.charactercards.GildorymCharacterCards;
import com.gildorymrp.charactercards.Race;
import com.gildorymrp.gildorymclasses.CharacterClass;
import com.gildorymrp.gildorymclasses.GildorymClasses;

public class RollInfoCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player;
		if (args.length == 0) {
			player = (Player) sender;
		} else {
			player = Bukkit.getServer().getPlayer(args[0]);
		}

		GildorymClasses gildorymClasses = (GildorymClasses) Bukkit.getServer()
				.getPluginManager().getPlugin("GildorymClasses");
		GildorymCharacterCards gildorymCharacterCards = (GildorymCharacterCards) Bukkit
				.getServer().getPluginManager()
				.getPlugin("GildorymCharacterCards");

		if (player != null) {
			CharacterClass characterClass = null;
			Race race = null;
			int level = 0;
			int age = 0;
			boolean isAdult = true;
			
			try {
				characterClass = gildorymClasses.classes.get(player.getName());
				level = gildorymClasses.levels.get(player.getName());
			} catch (NullPointerException ex) {
				sender.sendMessage(ChatColor.RED
						+ "You must set your class first!");
			}

			double meleeAttack = 0.0D;
			double meleeDefence = 0.0D;
			double rangedAttack = 0.0D;
			double rangedDefence = 0.0D;
			double magicAttack = 0.0D;
			double magicDefence = 0.0D;
			double fortitude = 0.0D;
			double reflex = 0.0D;
			double will = 0.0D;
			int BAB_good = 0;
			int BAB_avg = 0;
			int BAB_poor = 0;

			if (level <= 20) {
				BAB_good = level;
			} else {
				BAB_good = 20;
			}

			if (level == 1) {
				BAB_avg = 1;
				BAB_poor = 1;
			} else if (level > 1 && level <= 20) {
				BAB_avg = (int) Math.floor(((double) level) * 0.75D);
				BAB_poor = (int) Math.floor(((double) level) * 0.5D);
			} else if (level > 20) {
				BAB_avg = 15;
				BAB_avg = 10;
			}

			
			switch (characterClass) {
			case BARBARIAN:
				meleeAttack += (5 + BAB_good);
				meleeDefence += (3 + BAB_good);
				rangedAttack += (5 + BAB_avg);
				rangedDefence += (3 + BAB_avg);
				magicAttack = 0.0D;
				magicDefence += (1 + BAB_poor);
				fortitude += 2 + (level / 2);
				reflex += 0 + (level / 3);
				will += 0 + (level / 3);
				break;
			case BARD:
				meleeAttack += (3 + BAB_avg);
				meleeDefence += (1 + BAB_avg);
				rangedAttack += (3 + BAB_avg);
				rangedDefence += (1 + BAB_avg);
				magicAttack += (2 + BAB_avg);
				magicDefence += (1 + BAB_avg);
				fortitude += 0 + (level / 3);
				reflex += 2 + (level / 2);
				will += 2 + (level / 2);
				break;
			case CLERIC:
				meleeAttack += (3 + BAB_avg);
				meleeDefence += (2 + BAB_good);
				rangedAttack += (1 + BAB_poor);
				rangedDefence += (1 + BAB_avg);
				magicAttack += (4 + BAB_good);
				magicDefence += (3 + BAB_good);
				fortitude += 2 + (level / 2);
				reflex += 0 + (level / 3);
				will += 2 + (level / 2);
				break;
			case DRUID:
				meleeAttack += (3 + BAB_avg);
				meleeDefence += (2 + BAB_avg);
				rangedAttack += (1 + BAB_poor);
				rangedDefence += (1 + BAB_poor);
				magicAttack += (4 + BAB_good);
				magicDefence += (3 + BAB_good);
				fortitude += 2 + (level / 2);
				reflex += 0 + (level / 3);
				will += 2 + (level / 2);
				break;
			case FIGHTER:
				meleeAttack += (5 + BAB_good);
				meleeDefence += (5 + BAB_good);
				rangedAttack += (3 + BAB_avg);
				rangedDefence += (3 + BAB_good);
				magicAttack = 0.0D;
				magicDefence += (1 + BAB_poor);
				fortitude += 2 + (level / 2);
				reflex += 0 + (level / 3);
				will += 0 + (level / 3);
				break;
			case MONK:
				meleeAttack += (5 + BAB_good);
				meleeDefence += (6 + BAB_good);
				rangedAttack += (3 + BAB_avg);
				rangedDefence += (3 + BAB_good);
				magicAttack = 0.0D;
				magicDefence += (1 + BAB_poor);
				fortitude += 2 + (level / 2);
				reflex += 2 + (level / 2);
				will += 2 + (level / 2);
				boolean notArmored = notArmored(player);
					if (notArmored) {
						meleeDefence += (0.25 * level);
						rangedDefence += (0.25 * level);
					}
				break;
			case PALADIN:
				meleeAttack += (4 + BAB_avg);
				meleeDefence += (4 + BAB_good);
				rangedAttack += (2 + BAB_avg);
				rangedDefence += (2 + BAB_good);
				magicAttack += (3 + BAB_avg);
				magicDefence += (3 + BAB_avg);
				fortitude += 2 + (level / 2);
				reflex += 0 + (level / 3);
				will += 0 + (level / 3);
				break;
			case RANGER:
				meleeAttack += (3 + BAB_avg);
				meleeDefence += (2 + BAB_avg);
				rangedAttack += (4 + BAB_good);
				rangedDefence += (2 + BAB_avg);
				magicAttack += (1 + BAB_avg);
				magicDefence += (2 + BAB_avg);
				fortitude += 2 + (level / 2);
				reflex += 2 + (level / 2);
				will += 0 + (level / 3);
				break;
			case ROGUE:
				meleeAttack += (5 + BAB_good);
				meleeDefence += (4 + BAB_avg);
				rangedAttack += (3 + BAB_good);
				rangedDefence += (3 + BAB_avg);
				magicAttack = 0.0D;
				magicDefence += (2 + BAB_avg);
				fortitude += 0 + (level / 3);
				reflex += 2 + (level / 2);
				will += 2 + (level / 3);
				break;
			case SORCERER:
				meleeAttack += (2 + BAB_poor);
				meleeDefence += (2 + BAB_poor);
				rangedAttack += (1 + BAB_avg);
				rangedDefence += (1 + BAB_avg);
				magicAttack += (5 + BAB_good);
				magicDefence += (5 + BAB_good);
				fortitude += 0 + (level / 3);
				reflex += 0 + (level / 3);
				will += 2 + (level / 2);
				break;
			case WIZARD:
				meleeAttack += (1 + BAB_poor);
				meleeDefence += (1 + BAB_poor);
				rangedAttack += (2 + BAB_avg);
				rangedDefence += (2 + BAB_avg);
				magicAttack += (5 + BAB_good);
				magicDefence += (5 + BAB_good);
				fortitude += 0 + (level / 3);
				reflex += 0 + (level / 3);
				will += 2 + (level / 2);
				break;
			default:
				break;
			
			}

			try {
				race = gildorymCharacterCards.getCharacterCards()
						.get(player.getName()).getRace();
				age = gildorymCharacterCards.getCharacterCards().get(player.getName()).getAge();
			} catch (Exception ex) {
				sender.sendMessage(ChatColor.RED
						+ "You must set your race first!");
			}

			switch (race){
			case DWARF:
				magicAttack -= 2;
				fortitude += 1;
				if (level > 1)
					magicAttack -= (0.125 * level);
				if (age < 40)
					isAdult = false;
				
				break;
			case ELF:
				rangedAttack += 2;
				reflex += 1;
				if (level > 1)
					rangedAttack += (0.125 * level);
				if (age < 20)
					isAdult = false;
				break;
			case GNOME:
				meleeAttack -= 2;
				rangedAttack -= 2;
				fortitude += 1;
				if (level > 1) {
					meleeAttack -= (0.125 * level);
					rangedAttack -= (0.125 * level);
				}
				if (age < 40)
					isAdult = false;
				break;
			case HALFELF:
				if (age < 20)
					isAdult = false;
				break;
			case HALFLING:
				reflex += 1;
				rangedAttack += 2;
				meleeAttack -= 2;
				if (level > 1) {
					meleeAttack -= (0.125 * level);
					rangedAttack += (0.125 * level);
				}
				if (age < 20)
					isAdult = false;
				break;
			case HALFORC:
				meleeAttack += 2;
				rangedAttack += 2;
				magicAttack -= 4;
				magicDefence -= 2;
				if (level > 1) {
					meleeAttack += (0.125 * level);
					rangedAttack += (0.125 * level);
					magicAttack -= (0.25 * level);
					magicDefence -= (0.125 * level);
				}
				if (age < 14)
					isAdult = false;
				break;
			case HUMAN:
				if (age < 15)
					isAdult = false;
				break;
			case OTHER:
				break;
			case UNKNOWN:
				break;
			default:
				break;
			}

			Material itemType = player.getItemInHand().getType();

			switch (itemType) {
			// Melee Attack +1
			case STONE_SPADE:
			case WOOD_PICKAXE:
			case STONE_PICKAXE:
			case IRON_PICKAXE:
			case IRON_HOE:
				meleeAttack += 1.0D;
				break;
			case WOOD_SWORD:
				meleeAttack += 1.0D;
				rangedAttack += 1.0D;
				break;
			// Melee Attack +2
			case STONE_SWORD:
			case WOOD_AXE:
			case DIAMOND_PICKAXE:
				meleeAttack += 2.0D;
				break;
			case STONE_AXE:
				meleeAttack += 2.0D;
				rangedAttack += 2.0D;
				break;
			// Melee Attack +3
			case IRON_SWORD:
			case IRON_SPADE:
			case IRON_AXE:
				meleeAttack += 3.0D;
				break;
			case DIAMOND_HOE:
				meleeAttack += 3.0D;
				rangedAttack += 3.0D;
				break;
			// Melee Attack +4
			case DIAMOND_SPADE:
				meleeAttack += 4.0D;
				break;
			// Melee Attack +5
			case DIAMOND_SWORD:
			case DIAMOND_AXE:
				meleeAttack += 5.0D;
				break;
			// Ranged Attack +3
			case BOW:
			case CARROT_STICK:
				rangedAttack += 3.0D;
				break;
			// Ranged Attack +4
			case GOLD_PICKAXE:
				rangedAttack += 4.0D;
				break;
			// Melee Attack +1; Magic Attack +3
			case WOOD_HOE:
				meleeAttack += 1.0D;
				magicAttack += 3.0D;
				break;
			// Melee Attack +2; Magic Attack +2
			case STONE_HOE:
				meleeAttack += 2.0D;
				magicAttack += 2.0D;
				break;
			// Melee Attack +2; Magic Attack +3
			case GOLD_SWORD:
				meleeAttack += 2.0D;
				magicAttack += 3.0D;
				break;
			// Melee Attack +3; Magic Attack +2
			case GOLD_SPADE:
				meleeAttack += 3.0D;
				magicAttack += 2.0D;
				break;
			// Melee Attack +3; Magic Attack +4
			case GOLD_AXE:
			case GOLD_HOE:
				meleeAttack += 3.0D;
				magicAttack += 4.0D;
				break;
			// Magic Attack +2
			case ENDER_PEARL:
				magicAttack += 2.0D;
				break;
			// Magic Attack +3
			case EYE_OF_ENDER:
				magicAttack += 3.0D;
				break;
			// Magic Attack +4
			case BLAZE_ROD:
			case GOLDEN_APPLE:
				magicAttack += 4.0D;
				break;
			// Magic Attack +5
			case WOOD_SPADE:
				magicAttack += 5.0D;
				break;
			default:
				break;
			}

			// Helmets
			if (player.getInventory().getHelmet() != null) {
				Material helmetType = player.getInventory().getHelmet().getType();
				if (helmetType == Material.LEATHER_HELMET) {
					meleeDefence += 0.25D;
					rangedDefence += 0.25D;
				} else if (helmetType == Material.CHAINMAIL_HELMET) {
					meleeDefence += 0.5D;
					rangedDefence += 0.5D;
				} else if (helmetType == Material.IRON_HELMET) {
					meleeDefence += 0.75D;
					rangedDefence += 0.75D;
				} else if (helmetType == Material.DIAMOND_HELMET) {
					meleeDefence += 1.0D;
					rangedDefence += 1.0D;
				}
			}

			// Chestplates
			if (player.getInventory().getChestplate() != null) {
				Material chestplateType = player.getInventory().getChestplate().getType();
				if (chestplateType == Material.LEATHER_CHESTPLATE) {
					meleeDefence += 1.75D;
					rangedDefence += 1.75D;
				} else if (chestplateType == Material.CHAINMAIL_CHESTPLATE) {
					meleeDefence += 2.25D;
					rangedDefence += 2.25D;
				} else if (chestplateType == Material.IRON_CHESTPLATE) {
					meleeDefence += 2.5D;
					rangedDefence += 2.5D;
				} else if (chestplateType == Material.DIAMOND_CHESTPLATE) {
					meleeDefence += 3.5D;
					rangedDefence += 3.5D;
				}
			}

			// Leggings
			if (player.getInventory().getLeggings() != null) {
				Material leggingsType = player.getInventory().getLeggings().getType();
				if (leggingsType == Material.LEATHER_LEGGINGS) {
					meleeDefence += 0.75D;
					rangedDefence += 0.75D;
				} else if (leggingsType == Material.CHAINMAIL_LEGGINGS) {
					meleeDefence += 1.75D;
					rangedDefence += 1.75D;
				} else if (leggingsType == Material.IRON_LEGGINGS) {
					meleeDefence += 2.0D;
					rangedDefence += 2.0D;
				} else if (leggingsType == Material.DIAMOND_LEGGINGS) {
					meleeDefence += 2.5D;
					rangedDefence += 2.5D;
				}
			}

			// Boots
			if (player.getInventory().getBoots() != null) {
				Material bootsType = player.getInventory().getBoots().getType();
				if (bootsType == Material.LEATHER_BOOTS) {
					meleeDefence += 0.25D;
					rangedDefence += 0.25D;
				} else if (bootsType == Material.CHAINMAIL_BOOTS) {
					meleeDefence += 0.5D;
					rangedDefence += 0.5D;
				} else if (bootsType == Material.IRON_BOOTS) {
					meleeDefence += 0.75D;
					rangedDefence += 0.75D;
				} else if (bootsType == Material.DIAMOND_BOOTS) {
					meleeDefence += 1.0D;
					rangedDefence += 1.0D;
				}
			}

			/*if (!isAdult){
				
				meleeAttack /= 2;
				meleeDefence /= 2;
				rangedAttack /= 2;
				rangedDefence /= 2;
				magicAttack /= 2;
				magicDefence /= 2;
				fortitude /= 2;
				reflex /= 2;
				will /= 2;
			}*/
			
			sender.sendMessage(ChatColor.GRAY + "======================");
			sender.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + player.getDisplayName() + "'s Roll Info");
			sender.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "Class: " + characterClass);
			sender.sendMessage(ChatColor.GRAY + "======================");
			sender.sendMessage(ChatColor.RED + "Melee   - Attack: " + ChatColor.WHITE + (int) Math.floor(meleeAttack) + ChatColor.RED + "  Defence: " + ChatColor.WHITE + (int) Math.floor(meleeDefence));
			sender.sendMessage(ChatColor.RED + "Ranged - Attack: " + ChatColor.WHITE + (int) Math.floor(rangedAttack) + ChatColor.RED + "  Defence: " + ChatColor.WHITE + (int) Math.floor(rangedDefence));
			sender.sendMessage(ChatColor.RED + "Magic   - Attack: " + ChatColor.WHITE + (int) Math.floor(magicAttack) + ChatColor.RED + "  Defence: " + ChatColor.WHITE + (int) Math.floor(magicDefence));
			sender.sendMessage(ChatColor.RED + "Fort: " + ChatColor.WHITE + "1d20+" + (int) Math.floor(fortitude));
			sender.sendMessage(ChatColor.RED + "Ref: " + ChatColor.WHITE + "1d20+" + (int) Math.floor(reflex));
			sender.sendMessage(ChatColor.RED + "Will: " + ChatColor.WHITE + "1d20+" + (int) Math.floor(will));
		} else {
			sender.sendMessage(ChatColor.DARK_RED + "Could not find that player!");
		}
		return true;
	}

	private boolean notArmored(Player player) {
		Material helmet, chest, legs, boots;
		helmet = player.getInventory().getHelmet().getType();
		chest = player.getInventory().getChestplate().getType();
		legs = player.getInventory().getLeggings().getType();
		boots = player.getInventory().getBoots().getType();
		boolean noArmor = false;
		
		switch(helmet){
		case LEATHER_HELMET:
		case CHAINMAIL_HELMET:
		case IRON_HELMET:
		case DIAMOND_HELMET:
			return false;
		default:
			noArmor = true;
		}
		
		switch(chest){
		case LEATHER_CHESTPLATE:
		case CHAINMAIL_CHESTPLATE:
		case IRON_CHESTPLATE:
		case DIAMOND_CHESTPLATE:
			return false;
		default:
			noArmor = true;
		}
		
		switch(legs){
		case LEATHER_LEGGINGS:
		case CHAINMAIL_LEGGINGS:
		case IRON_LEGGINGS:
		case DIAMOND_LEGGINGS:
			return false;
		default:
			noArmor = true;
		}
		
		switch(boots){
		case LEATHER_BOOTS:
		case CHAINMAIL_BOOTS:
		case IRON_BOOTS:
		case DIAMOND_BOOTS:
			return false;
		default:
			noArmor = true;
		}
		
		if (noArmor)
			return true;
		
		return false;
	}

}
