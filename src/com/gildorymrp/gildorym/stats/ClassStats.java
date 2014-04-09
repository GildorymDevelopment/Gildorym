package com.gildorymrp.gildorym.stats;

import org.bukkit.entity.Player;

import com.gildorymrp.gildorymclasses.CharacterClass;

public class ClassStats {

	public static double getMeleeAttack(Player player){
		int level = Util.getLevel(player);
		CharacterClass clazz = Util.getClazz(player);
		int babGood = Stats.getBabGood(level);
		int babAvg = Stats.getBabAvg(level);
		int babPoor = Stats.getBabPoor(level);
		
		switch(clazz){
		case BARBARIAN:
			return 5 + babGood;
		case BARD:
			return 3 + babAvg;
		case CLERIC:
			return 3 + babAvg;
		case DRUID:
			return 3 + babGood;
		case FIGHTER:
			return 5 + babGood;
		case MONK:
			return 5 + babGood;
		case PALADIN:
			return 4 + babAvg;
		case RANGER:
			return 3 + babAvg;
		case ROGUE:
			return 5 + babGood;
		case SORCERER:
			return 2 + babPoor;
		case WIZARD:
			return 1 + babPoor;
		default:
			return 0.0;
		}
	}
	
	public static double getMeleeDefence(Player player){
		int level = Util.getLevel(player);
		CharacterClass clazz = Util.getClazz(player);
		int babGood = Stats.getBabGood(level);
		int babAvg = Stats.getBabAvg(level);
		int babPoor = Stats.getBabPoor(level);
		boolean isArmored = true;
		double meleeDefence;
		
		if (ArmorStats.getArmorMelee(player) == 0 && ArmorStats.getArmorRanged(player) == 0)
			isArmored = false;
		
		switch(clazz){
		case BARBARIAN:
			return 5 + babAvg;
		case BARD:
			return 3 + babAvg;
		case CLERIC:
			return 1 + babPoor;
		case DRUID:
			return 1 + babPoor;
		case FIGHTER:
			return 3 + babAvg;
		case MONK:
			meleeDefence = 3 + babAvg;
			if(!isArmored)
				meleeDefence += (0.25 * level);
			break;
		case PALADIN:
			return 2 + babAvg;
		case RANGER:
			return 4 + babGood;
		case ROGUE:
			return 3 + babGood;
		case SORCERER:
			return 1 + babAvg;
		case WIZARD:
			return 2 + babAvg;
		default:
			return 0;
		}
		
		return meleeDefence;
	}
	
	public static double getRangedAttack(Player player){
		int level = Util.getLevel(player);
		CharacterClass clazz = Util.getClazz(player);
		int babGood = Stats.getBabGood(level);
		int babAvg = Stats.getBabAvg(level);
		int babPoor = Stats.getBabPoor(level);
		
		switch(clazz){
		case BARBARIAN:
			return 5 + babAvg;
		case BARD:
			return 3 + babAvg;
		case CLERIC:
			return 1 + babPoor;
		case DRUID:
			return 1 + babPoor;
		case FIGHTER:
			return 3 + babAvg;
		case MONK:
			return 3 + babAvg;
		case PALADIN:
			return 2 + babAvg;
		case RANGER:
			return 4 + babGood;
		case ROGUE:
			return 3 + babGood;
		case SORCERER:
			return 1 + babAvg;
		case WIZARD:
			return 2 + babAvg;
		default:
			return 0.0;
		}
	}
	
	public static double getRangedDefence(Player player){
		int level = Util.getLevel(player);
		CharacterClass clazz = Util.getClazz(player);
		int babGood = Stats.getBabGood(level);
		int babAvg = Stats.getBabAvg(level);
		int babPoor = Stats.getBabPoor(level);
		double rangedDefence = 0;
		boolean isArmored = true;
		
		if (ArmorStats.getArmorMelee(player) == 0 && ArmorStats.getArmorRanged(player) == 0)
			isArmored = false;
		
		switch(clazz){
		case BARBARIAN:
			return 3 + babAvg;
		case BARD:
			return 1 + babAvg;
		case CLERIC:
			return 1 + babAvg;
		case DRUID:
			return 1 + babPoor;
		case FIGHTER:
			return 3 + babGood;
		case MONK:
			rangedDefence = 3 + babGood;
				if (!isArmored)
					rangedDefence += (0.25 * level);
				break;
		case PALADIN:
			return 2 + babGood;
		case RANGER:
			return 2 + babAvg;
		case ROGUE:
			return 3 + babAvg;
		case SORCERER:
			return 1 + babAvg;
		case WIZARD:
			return 2 + babAvg;
		default:
			return 0.0;	
		}
		
		return rangedDefence;
	}
	
	public static double getMagicAttack(Player player){
		int level = Util.getLevel(player);
		CharacterClass clazz = Util.getClazz(player);
		int babGood = Stats.getBabGood(level);
		int babAvg = Stats.getBabAvg(level);
	
		switch(clazz){
		case BARBARIAN:
			return 0.0D;
		case BARD:
			return 2 + babAvg;
		case CLERIC:
			return 4 + babGood;
		case DRUID:
			return 4 + babGood;
		case FIGHTER:
			return 0.0D;
		case MONK:
			return 0.0D;
		case PALADIN:
			return 3 + babAvg;
		case RANGER:
			return 1 + babAvg;
		case ROGUE:
			return 0.0D;
		case SORCERER:
			return 5 + babGood;
		case WIZARD:
			return 5 + babGood;
		default:
			return 0.0;
		}
	}
	
	public static double getMagicDefence(Player player){
		int level = Util.getLevel(player);
		CharacterClass clazz = Util.getClazz(player);
		int babGood = Stats.getBabGood(level);
		int babAvg = Stats.getBabAvg(level);
		int babPoor = Stats.getBabPoor(level);
		
		switch(clazz){
		case BARBARIAN:
			return 1 + babPoor;
		case BARD:
			return 1 + babAvg;
		case CLERIC:
			return 3 + babGood;
		case DRUID:
			return 3 + babGood;
		case FIGHTER:
			return 1 + babPoor;
		case MONK:
			return 1 + babPoor;
		case PALADIN:
			return 3 + babAvg;
		case RANGER:
			return 2 + babAvg;
		case ROGUE:
			return 2 + babAvg;
		case SORCERER:
			return 5 + babGood;
		case WIZARD:
			return 5 + babGood;
		default:
			return 0;	
		}
	}
	
	public static int getFortitude(Player player){
		int level = Util.getLevel(player);
		CharacterClass clazz = Util.getClazz(player);
		int saveGood = Stats.getSaveGood(level);
		int saveBad = Stats.getSaveBad(level);
		
		switch(clazz){
		case BARBARIAN:
			return saveGood;
		case BARD:
			return saveBad;
		case CLERIC:
			return saveGood;
		case DRUID:
			return saveGood;
		case FIGHTER:
			return saveGood;
		case MONK:
			return saveGood;
		case PALADIN:
			return saveGood;
		case RANGER:
			return saveGood;
		case ROGUE:
			return saveBad;
		case SORCERER:
			return saveBad;
		case WIZARD:
			return saveBad;
		default:
			return 0;
		}
	}
	
	public static int getReflex(Player player){
		int level = Util.getLevel(player);
		CharacterClass clazz = Util.getClazz(player);
		int saveGood = Stats.getSaveGood(level);
		int saveBad = Stats.getSaveBad(level);
		
		switch(clazz){
		case BARBARIAN:
			return saveBad;
		case BARD:
			return saveGood;
		case CLERIC:
			return saveBad;
		case DRUID:
			return saveBad;
		case FIGHTER:
			return saveBad;
		case MONK:
			return saveGood;
		case PALADIN:
			return saveBad;
		case RANGER:
			return saveGood;
		case ROGUE:
			return saveGood;
		case SORCERER:
			return saveBad;
		case WIZARD:
			return saveBad;
		default:
			return 0;
		}
	}
	
	public static int getWill(Player player){
		int level = Util.getLevel(player);
		CharacterClass clazz = Util.getClazz(player);
		int saveGood = Stats.getSaveGood(level);
		int saveBad = Stats.getSaveBad(level);
		
		switch(clazz){
		case BARBARIAN:
			return saveBad;
		case BARD:
			return saveGood;
		case CLERIC:
			return saveGood;
		case DRUID:
			return saveGood;
		case FIGHTER:
			return saveBad;
		case MONK:
			return saveGood;
		case PALADIN:
			return saveBad;
		case RANGER:
			return saveBad;
		case ROGUE:
			return saveBad;
		case SORCERER:
			return saveGood;
		case WIZARD:
			return saveGood;
		default:
			return 0;
		}
	}
}
