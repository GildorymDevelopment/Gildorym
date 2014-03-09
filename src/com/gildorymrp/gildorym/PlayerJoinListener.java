package com.gildorymrp.gildorym;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
	private Gildorym plugin;

	public PlayerJoinListener(Gildorym plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerJoin(PlayerJoinEvent event) {
		GildorymCharacter gChar = plugin.loadCharacter(event.getPlayer());
		if (gChar != null) {
			plugin.getActiveCharacters().put(event.getPlayer().getName(), gChar);
		}
	}
	
}
