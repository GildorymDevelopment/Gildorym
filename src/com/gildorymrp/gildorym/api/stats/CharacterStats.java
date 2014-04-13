package com.gildorymrp.gildorym.api.stats;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CharacterStats {
	
	double meleeAttack = 0;
	double meleeDefence = 0;
	double rangedAttack = 0;
	double rangedDefence = 0;
	double magicAttack = 0;
	double magicDefence = 0;
	double fortitude = 0;
	double reflex = 0;
	double will = 0;
	
	public CharacterStats(Player player) {
		calculateStats(player);
	}
	
	public void calculateStats(Player player) {
		calcMeleeAttack(player);
		calcMeleeDefence(player);
		calcRangedAttack(player);
		calcRangedDefence(player);
		calcMagicAttack(player);
		calcMagicDefence(player);
		calcFortitude(player);
		calcReflex(player);
		calcWill(player);
	}
	
	public double getMeleeAttack() {
		return meleeAttack;
	}
	
	public double getMeleeDefence() {
		return meleeDefence;
	}
	
	public double getRangedAttack() {
		return rangedAttack;
	}
	
	public double getRangedDefence() {
		return rangedDefence;
	}
	
	public double getMagicAttack() {
		return magicAttack;
	}
	
	public double getMagicDefence() {
		return magicDefence;
	}
	
	public double getFortitude() {
		return fortitude;
	}
	
	public double getReflex() {
		return reflex;
	}
	
	public double getWill() {
		return will;
	}
	
	private void calcMeleeAttack(Player player) {
		ItemStack weapon = player.getInventory().getItemInHand();
		
		meleeAttack = 0;
		meleeAttack += ClassStats.getMeleeAttack(player);
		meleeAttack += RaceStats.getMeleeAttack(player);
		meleeAttack += WeaponStats.getMeleeAttack(weapon);
	}

	private void calcMeleeDefence(Player player) {
		meleeDefence = 0;
		meleeDefence += ClassStats.getMeleeDefence(player);
		meleeDefence += RaceStats.getMeleeDefence(player);
		meleeDefence += ArmorStats.getArmorMelee(player);
	}

	private void calcRangedAttack(Player player) {
		ItemStack weapon = player.getInventory().getItemInHand();

		rangedAttack = 0;
		rangedAttack += ClassStats.getRangedAttack(player);
		rangedAttack += RaceStats.getRangedAttack(player);
		rangedAttack += WeaponStats.getRangedAttack(weapon);
	}

	private void calcRangedDefence(Player player) {
		rangedDefence = 0;
		rangedDefence += ClassStats.getRangedDefence(player);
		rangedDefence += RaceStats.getRangedDefence(player);
		rangedDefence += ArmorStats.getArmorRanged(player);
	}

	private void calcMagicAttack(Player player) {
		ItemStack weapon = player.getInventory().getItemInHand();

		magicAttack = 0;
		magicAttack += ClassStats.getMagicAttack(player);
		magicAttack += RaceStats.getMagicAttack(player);
		magicAttack += WeaponStats.getMagicAttack(weapon);
	}

	private void calcMagicDefence(Player player) {
		magicDefence = 0;
		magicDefence += ClassStats.getMagicDefence(player);
		magicDefence += RaceStats.getMagicDefence(player);
	}

	private void calcFortitude(Player player) {
		fortitude = 0;
		fortitude += ClassStats.getFortitude(player);
		fortitude += RaceStats.getFortitude(player);
	}

	private void calcReflex(Player player) {
		reflex = 0;
		reflex += ClassStats.getReflex(player);
		reflex += RaceStats.getReflex(player);
	}

	private void calcWill(Player player) {
		will = 0;
		will += ClassStats.getWill(player);
		will += RaceStats.getWill(player);
	}
	
}
