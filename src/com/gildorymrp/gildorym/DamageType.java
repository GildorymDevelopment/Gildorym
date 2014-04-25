package com.gildorymrp.gildorym;

public enum DamageType {
	FALL_DAMAGE("FallDamage"),
	PLAYER_ATTACK("PlayerAttack"),
	MONSTER_ATTACK("MonsterAttack"),
	DROWNING("Drowning"),
	OTHER("Other");
	
	private String commandName;
	
	DamageType(String cName) {
		commandName = cName;
	}
	
	public String commandName() {
		return commandName;
	}
	
	public static DamageType commandName(String str) {
		DamageType[] vls = DamageType.values();
		
		for(DamageType dt : vls) {
			if(dt.commandName.equalsIgnoreCase(str))
				return dt;
		}
		return null;
	}
}
