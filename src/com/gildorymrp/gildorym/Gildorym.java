package com.gildorymrp.gildorym;

import java.util.Random;

import org.bukkit.plugin.java.JavaPlugin;

public class Gildorym extends JavaPlugin {
	
	public void onEnable() {
		this.getCommand("newcharacter").setExecutor(new NewCharacterCommand());
		this.getCommand("setname").setExecutor(new SetNameCommand());
		this.getCommand("rollinfo").setExecutor(new RollInfoCommand());
		this.getCommand("roll").setExecutor(new RollCommand());
		MetaEditorCommands mec = new MetaEditorCommands();
		this.getCommand("renameitem").setExecutor(mec);
		this.getCommand("setlore").setExecutor(mec);
		this.getCommand("addlore").setExecutor(mec);
		this.getCommand("removelore").setExecutor(mec);
		this.getCommand("signitem").setExecutor(mec);
		this.getServer().getPluginManager().registerEvents(new EntityDamageByEntityListener(this), this);
		this.getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
	}

	public boolean doDurabilityDamage(int durability, int maxDurability, int damage) {
		Random randomGenerator = new Random();

		int dieSize = maxDurability - durability;
		int die = randomGenerator.nextInt(dieSize) + 1;
		
		//damaged.sendMessage("dieSize: " + dieSize + " die: " + die);
			if (die <= damage || die == 1) {
				return true;
			} else {
				return false;
			}
	}
	
	

}
