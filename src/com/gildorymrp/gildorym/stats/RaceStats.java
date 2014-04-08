package com.gildorymrp.gildorym.stats;

import org.bukkit.entity.Player;

import com.gildorymrp.charactercards.Race;

public class RaceStats {

	private Util Util;
	private Stats Stats;
	
	double meleeAttack, meleeDefence, rangedAttack, rangedDefence, magicAttack, magicDefence;
	int saveGood;
	int saveBad;
	int babGood = 0;
	int babAvg = 0;
	int babPoor = 0;
	int level;
	Race race;
	
	public double getMeleeAttack(Player player){
		level = Util.getLevel(player);
		race = Util.getRace(player);
		babGood = Stats.getBabGood(level);
		babAvg = Stats.getBabAvg(level);
		babPoor = Stats.getBabPoor(level);
		meleeAttack = 0;
		
		switch(race){
		case GNOME:
			meleeAttack -= 2;
			break;
		case HALFLING:
			meleeAttack -= 2;
			break;
		case HALFORC:
			meleeAttack += 2;
			if (level > 1)
				meleeAttack += (0.125 * level);
			break;
		default:
			break;
		}
		
		return meleeAttack;
	}
	
	public double getMeleeDefence(Player player){
		level = Util.getLevel(player);
		race = Util.getRace(player);
		babGood = Stats.getBabGood(level);
		babAvg = Stats.getBabAvg(level);
		babPoor = Stats.getBabPoor(level);
		meleeDefence = 0;
		
		return meleeDefence;
	}
	
	public double getRangedAttack(Player player){
		level = Util.getLevel(player);
		race = Util.getRace(player);
		babGood = Stats.getBabGood(level);
		babAvg = Stats.getBabAvg(level);
		babPoor = Stats.getBabPoor(level);
		rangedAttack = 0;
		
		switch (race){
		case ELF:
			rangedAttack += 2;
		case GNOME:
			rangedAttack -= 2;
			if (level > 1)
				rangedAttack -= 0.125 * level;
		case HALFLING:
			rangedAttack += 2;
			if (level > 1)
				rangedAttack += 0.125 * level;
			break;
		case HALFORC:
			rangedAttack += 2;
			if (level > 1)
				rangedAttack += 0.125 * level;
		default:
			break;
		}
		
		return rangedAttack;
	}
	
	public double getRangedDefence(Player player){
		level = Util.getLevel(player);
		race = Util.getRace(player);
		babGood = Stats.getBabGood(level);
		babAvg = Stats.getBabAvg(level);
		babPoor = Stats.getBabPoor(level);
		rangedDefence = 0;
		
		return rangedDefence;
	}
	
	public double getMagicAttack(Player player){
		level = Util.getLevel(player);
		race = Util.getRace(player);
		babGood = Stats.getBabGood(level);
		babAvg = Stats.getBabAvg(level);
		babPoor = Stats.getBabPoor(level);
		magicAttack = 0;
		
		switch (race){
		case DWARF:
			magicAttack -= 2;
			if (level > 1)
				magicAttack -= (0.125 * level);
			break;
		case HALFORC:
			magicAttack -= 4;
			if (level > 1)
				magicAttack -= (0.25 * level);
			break;
		default:
			break;
		}
		
		return magicAttack;
	}
	
	public double getMagicDefence(Player player){
		level = Util.getLevel(player);
		race = Util.getRace(player);
		babGood = Stats.getBabGood(level);
		babAvg = Stats.getBabAvg(level);
		babPoor = Stats.getBabPoor(level);
		magicDefence = 0;
		
		switch (race){
		case HALFORC:
			magicDefence -= 2;
			if (level > 1)
				magicDefence -= (0.125 * level);
			break;
		default:
			break;
		}
		
		return magicDefence;
	}
	
	public int getFortitude(Player player){
		race = Util.getRace(player);
		switch (race){
		case DWARF:
			return 1;
		case GNOME:
			return 1;
		default:
			return 0;
		}
		
	}
	
	public int getReflex(Player player){
		race = Util.getRace(player);
		switch (race){
		case ELF:
			return 1;
		case HALFLING:
			return 1;
		default:
			return 0;
		}
		
	}
	
	public int getWill(Player player){
		race = Util.getRace(player);
		
		return 0;
		
	}
	
}
