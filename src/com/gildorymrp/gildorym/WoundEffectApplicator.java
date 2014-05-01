package com.gildorymrp.gildorym;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

public class WoundEffectApplicator extends BukkitRunnable {
	private Gildorym gildorym;

	public WoundEffectApplicator(Gildorym gildorym) {
		this.gildorym = gildorym;
	}

	@Override
	public void run() {
		MySQLDatabase sqlDb = gildorym.getMySQLDatabase();
		
		Player[] online = gildorym.getServer().getOnlinePlayers();
		
		for(Player p : online) {
			GildorymCharacter gc = gildorym.getActiveCharacters().get(p.getName());
			sqlDb.pruneWounds(gc);
			List<Wound> wounds = gc.getWounds();
			if(wounds == null)
				return;
			for(Wound w : wounds) {
				if(w.getPotionEffect() > 0 && w.getEffectLevel() >= 0) {
					p.addPotionEffect(new PotionEffect(w.getPotionEffectObj(), 20 * 10, w.getEffectLevel()), true);
				}
			}
		}
	}

	
}
