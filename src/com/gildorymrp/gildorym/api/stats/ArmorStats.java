package com.gildorymrp.gildorym.api.stats;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ArmorStats {

	public static double getArmorMelee(Player player) {
		ItemStack helmet = player.getInventory().getHelmet();
		ItemStack chest = player.getInventory().getChestplate();
		ItemStack legs = player.getInventory().getLeggings();
		ItemStack feet = player.getInventory().getBoots();
		double armor = 0;

		armor = getHelmetMelee(helmet) + getChestMelee(chest)
				+ getLegMelee(legs) + getFootMelee(feet);

		return armor;
	}

	public static double getArmorRanged(Player player) {
		ItemStack helmet = player.getInventory().getHelmet();
		ItemStack chest = player.getInventory().getChestplate();
		ItemStack legs = player.getInventory().getLeggings();
		ItemStack feet = player.getInventory().getBoots();
		double armor = getHelmetRanged(helmet) + getChestRanged(chest)
				+ getLegRanged(legs) + getFootRanged(feet);
		return armor;
	}

	public static double getHelmetMelee(ItemStack helmet) {
		if (helmet != null) {
			switch (helmet.getType()) {
			case LEATHER_HELMET:
				return 0.25;
			case CHAINMAIL_HELMET:
				return 0.5;
			case IRON_HELMET:
				return 0.75;
			case DIAMOND_HELMET:
				return 1.0;
			default:
				return 0;
			}
		}
		return 0;

	}

	public static double getChestMelee(ItemStack chest) {
		if (chest != null) {
			switch (chest.getType()) {
			case LEATHER_CHESTPLATE:
				return 1.75;
			case CHAINMAIL_CHESTPLATE:
				return 2.25;
			case IRON_CHESTPLATE:
				return 2.5;
			case DIAMOND_CHESTPLATE:
				return 3.5;
			default:
				return 0;
			}
		}
		return 0;
	}

	public static double getLegMelee(ItemStack legs) {
		if (legs != null) {
			switch (legs.getType()) {
			case LEATHER_LEGGINGS:
				return 0.75;
			case CHAINMAIL_LEGGINGS:
				return 1.75;
			case IRON_LEGGINGS:
				return 2.0;
			case DIAMOND_LEGGINGS:
				return 2.5;
			default:
				return 0;
			}
		}
		return 0;
	}

	public static double getFootMelee(ItemStack feet) {
		if (feet != null) {
			switch (feet.getType()) {
			case LEATHER_BOOTS:
				return 0.25;
			case CHAINMAIL_BOOTS:
				return 0.5;
			case IRON_BOOTS:
				return 0.75;
			case DIAMOND_BOOTS:
				return 1.0;
			default:
				return 0;
			}
		}
		return 0;
	}

	public static double getHelmetRanged(ItemStack helmet) {
		if (helmet != null) {
			switch (helmet.getType()) {
			case LEATHER_HELMET:
				return 0.25;
			case CHAINMAIL_HELMET:
				return 0.5;
			case IRON_HELMET:
				return 0.75;
			case DIAMOND_HELMET:
				return 1.0;
			default:
				return 0;
			}
		}
		return 0;
	}

	public static double getChestRanged(ItemStack chest) {
		if (chest != null) {
			switch (chest.getType()) {
			case LEATHER_CHESTPLATE:
				return 1.75;
			case CHAINMAIL_CHESTPLATE:
				return 2.25;
			case IRON_CHESTPLATE:
				return 2.5;
			case DIAMOND_CHESTPLATE:
				return 3.5;
			default:
				return 0;
			}
		}
		return 0;
	}

	public static double getLegRanged(ItemStack legs) {
		if (legs != null) {
			switch (legs.getType()) {
			case LEATHER_LEGGINGS:
				return 0.75;
			case CHAINMAIL_LEGGINGS:
				return 1.75;
			case IRON_LEGGINGS:
				return 2.0;
			case DIAMOND_LEGGINGS:
				return 2.5;
			default:
				return 0;
			}
		}
		return 0;
	}

	public static double getFootRanged(ItemStack feet) {
		if (feet != null) {
			switch (feet.getType()) {
			case LEATHER_BOOTS:
				return 0.25;
			case CHAINMAIL_BOOTS:
				return 0.5;
			case IRON_BOOTS:
				return 0.75;
			case DIAMOND_BOOTS:
				return 1.0;
			default:
				return 0;
			}
		}
		return 0;
	}

}
