package com.gildorymrp.gildorym.stats;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.gildorymrp.charactercards.GildorymCharacterCards;
import com.gildorymrp.charactercards.Race;
import com.gildorymrp.gildorymclasses.CharacterClass;
import com.gildorymrp.gildorymclasses.GildorymClasses;

public class Util {

	private static GildorymClasses gildorymClasses = (GildorymClasses) Bukkit
			.getServer().getPluginManager().getPlugin("GildorymClasses");
	private static GildorymCharacterCards gildorymCharacterCards = (GildorymCharacterCards) Bukkit
			.getServer().getPluginManager().getPlugin("GildorymCharacterCards");

	public static int getLevel(Player player) {
		if (player != null)
			try {
				return gildorymClasses.levels.get(player.getName());
			} catch (Exception NullPointerException) {
				return 1;
			}
		return 1;
	}

	public static CharacterClass getClazz(Player player) {
		if (player != null)
			try {
				return gildorymClasses.classes.get(player.getName());
			} catch (Exception NullPointerException) {
				return null;
			}
		return null;
	}

	public static Race getRace(Player player) {
		if (player != null)
			try {
				return gildorymCharacterCards.getCharacterCards()
						.get(player.getName()).getRace();
			} catch (Exception NullPointerException) {
				return null;
			}
		return null;
	}

}
