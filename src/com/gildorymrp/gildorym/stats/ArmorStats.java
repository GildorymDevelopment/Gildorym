package com.gildorymrp.gildorym.stats;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ArmorStats {
	
	public double getArmorMelee(Player player){
		Material helmet = player.getInventory().getHelmet().getType();
		Material chest = player.getInventory().getChestplate().getType();
		Material legs = player.getInventory().getLeggings().getType();
		Material feet = player.getInventory().getBoots().getType();
		double armor = getHelmetMelee(helmet) + getChestMelee(chest) + getLegMelee(legs) + getFootMelee(feet);
		
		return armor;
	}
	
	public double getArmorRanged(Player player){
		Material helmet = player.getInventory().getHelmet().getType();
		Material chest = player.getInventory().getChestplate().getType();
		Material legs = player.getInventory().getLeggings().getType();
		Material feet = player.getInventory().getBoots().getType();
		double armor = getHelmetRanged(helmet) + getChestRanged(chest) + getLegRanged(legs) + getFootRanged(feet);
		
		return armor;
	}
	
	public double getHelmetMelee(Material helmet){
		if (helmet != null)
			switch(helmet){
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
		return 0;
		
	}
	
	public double getChestMelee(Material chest){
		if (chest != null)
			switch(chest){
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
		return 0;
	}
	
	public double getLegMelee(Material legs){
		if(legs != null)
			switch(legs){
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
		return 0;
	}
	
	public double getFootMelee(Material feet){
		if(feet != null)
			switch(feet){
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
		return 0;
	}
	
	public double getHelmetRanged(Material helmet){
		if (helmet != null)
			switch(helmet){
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
		return 0;
	}
	
	public double getChestRanged(Material chest){
		if (chest != null)
			switch(chest){
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
		return 0;
	}
	
	public double getLegRanged(Material legs){
		if(legs != null)
			switch(legs){
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
		return 0;
	}
	
	public double getFootRanged(Material feet){
		if(feet != null)
			switch(feet){
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
		return 0;
	}
	
}
