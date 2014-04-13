package com.gildorymrp.gildorym.api.stats;

import org.bukkit.inventory.ItemStack;

public class WeaponStats {

	public static int getMeleeAttack(ItemStack weapon) {
		if (weapon != null)
			switch (weapon.getType()) {
			case IRON_PICKAXE:
			case IRON_HOE:
			case STONE_SPADE:
			case STONE_PICKAXE:
			case WOOD_HOE:
			case WOOD_PICKAXE:
			case WOOD_SWORD:
				return 1;
			case DIAMOND_PICKAXE:
			case GOLD_SWORD:
			case STONE_AXE:
			case STONE_HOE:
			case STONE_SWORD:
			case WOOD_AXE:
				return 2;
			case DIAMOND_HOE:
			case GOLD_AXE:
			case GOLD_HOE:
			case GOLD_SPADE:
			case IRON_SWORD:
			case IRON_SPADE:
			case IRON_AXE:
				return 3;
			case DIAMOND_SPADE:
				return 4;
			case DIAMOND_SWORD:
			case DIAMOND_AXE:
				return 5;
			default:
				return 0;
		}
		return 0;

	}

	public static int getRangedAttack(ItemStack weapon) {
		if (weapon != null)	
		switch (weapon.getType()) {
			case WOOD_SWORD:
				return 1;
			case STONE_AXE:
				return 2;
			case BOW:
			case CARROT_STICK:
			case DIAMOND_HOE:
				return 3;
			case GOLD_PICKAXE:
				return 4;
			default:
				return 0;
			}
		return 0;

	}

	public static int getMagicAttack(ItemStack weapon) {
		if (weapon != null)
			switch (weapon.getType()) {
			case ENDER_PEARL:
			case GOLD_SPADE:
			case STONE_HOE:
				return 2;
			case EYE_OF_ENDER:
			case GOLD_SWORD:
			case WOOD_HOE:
				return 3;
			case BLAZE_ROD:
			case GOLD_AXE:
			case GOLD_HOE:
			case GOLDEN_APPLE:
				return 4;
			case WOOD_SPADE:
				return 5;
			default:
				return 0;
			}
		return 0;
		
	}
}
