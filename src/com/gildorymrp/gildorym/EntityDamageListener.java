package com.gildorymrp.gildorym;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class EntityDamageListener implements Listener {

	private final Integer maxDistance = 16;

	private Gildorym plugin;

	public EntityDamageListener(Gildorym plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		Random randomGenerator = new Random();

		int fall = (int) event.getEntity().getFallDistance() + 1;
		int roll = randomGenerator.nextInt(20) + 1;

		if (event.isCancelled() != true) {
			// Automated Fall Damage & Damage Messages
			if (event.getEntity() instanceof Player) {
				Player damaged = (Player) event.getEntity();
				if (event.getCause() == DamageCause.FALL) {
					if (fall < 5) {
						event.setCancelled(true);
						return;
					} else if (fall >= 5) {
						int noinjury;
						int minorinjury;
						int majorinjury;

						if (fall < 10) {
							noinjury = 11;
							minorinjury = 4;
							majorinjury = 1;
						} else if (fall < 14) {
							noinjury = 15;
							minorinjury = 8;
							majorinjury = 1;
						} else if (fall < 17) {
							noinjury = 19;
							minorinjury = 12;
							majorinjury = 3;
						} else if (fall < 20) {
							noinjury = 21;
							minorinjury = 20;
							majorinjury = 16;
						} else {
							noinjury = 21;
							minorinjury = 21;
							majorinjury = 21;
						}

						if (roll >= noinjury) {
							plugin.onInjury(damaged, "none", 1, fall, roll);
							event.setCancelled(true);
							return;
						} else if (roll >= minorinjury) {
							plugin.onInjury(damaged, "minor", 50, fall, roll);
							return;
						} else if (roll >= majorinjury) {
							plugin.onInjury(damaged, "major", 50, fall, roll);
							return;
						} else {
							plugin.onInjury(damaged, "death", 1, fall, roll);
							return;
						}
					}
					return;
				}

				double percentHealth = ((float) damaged.getHealth() / (float) damaged.getMaxHealth()) * 100.0D;
				if (percentHealth <= 25.0D) {
					for (Player player : event.getEntity().getWorld()
							.getPlayers()) {
						if (event.getEntity().getLocation().distance(player.getLocation()) <= this.maxDistance.intValue()) {
							player.sendMessage(ChatColor.RED + ((Player) event.getEntity()).getDisplayName() + " appears gravely wounded!");
						}
					}
				} else if (percentHealth <= 50.0D) {
					for (Player player : event.getEntity().getWorld()
							.getPlayers()) {
						if (event.getEntity().getLocation().distance(player.getLocation()) <= this.maxDistance.intValue()) {
							player.sendMessage(ChatColor.RED + ((Player) event.getEntity()).getDisplayName() + " appears wounded!");
						}
					}
				}
			}
		}
	}
}
