package com.gildorymrp.gildorym.stats;

import org.bukkit.Material;

public class WeaponStats {

	public int getMeleeAttack(Material weapon) {
		switch (weapon) {
		case STONE_SPADE:
		case WOOD_PICKAXE:
		case STONE_PICKAXE:
		case IRON_PICKAXE:
		case IRON_HOE:
			return 1;
		case WOOD_SWORD:
			return 1;
		case STONE_SWORD:
		case WOOD_AXE:
		case DIAMOND_PICKAXE:
			return 2;
		case STONE_AXE:
			return 2;
		case IRON_SWORD:
		case IRON_SPADE:
		case IRON_AXE:
		case DIAMOND_HOE:
			return 3;
		case DIAMOND_SPADE:
			return 4;
		case DIAMOND_SWORD:
		case DIAMOND_AXE:
			return 5;
		case WOOD_HOE:
			return 1;
		case STONE_HOE:
			return 2;
		case GOLD_SWORD:
			return 2;
		case GOLD_SPADE:
			return 3;
		case GOLD_AXE:
		case GOLD_HOE:
			return 3;
		default:
			return 0;
		}

	}

	public int getRangedAttack(Material weapon) {
		switch (weapon) {
		case WOOD_SWORD:
			return 1;
		case STONE_AXE:
			return 2;
		case DIAMOND_HOE:
			return 3;
		case BOW:
		case CARROT_STICK:
			return 3;
		case GOLD_PICKAXE:
			return 4;
		default:
			return 0;
		}

	}

	public int getMagicAttack(Material weapon) {
		switch (weapon) {
		case WOOD_HOE:
			return 3;
		case STONE_HOE:
			return 2;
		case GOLD_SWORD:
			return 3;
		case GOLD_SPADE:
			return 2;
		case GOLD_AXE:
		case GOLD_HOE:
			return 4;
		case ENDER_PEARL:
			return 2;
		case EYE_OF_ENDER:
			return 3;
		case BLAZE_ROD:
		case GOLDEN_APPLE:
			return 4;
		case WOOD_SPADE:
			return 5;
		default:
			return 0;
		}

	}
}
