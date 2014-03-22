package com.gildorymrp.gildorym;

public class GildorymStats {
	private int statsUid;
	private int stamina;
	private int magicalStamina;
	private int lockpickStamina;
	
	public GildorymStats(int uid) {
		this.statsUid = uid;
	}
	
	/**
	 * @param uid stats uid
	 * @param stamina
	 * @param magicalStamina
	 * @param lockpickStamina
	 */
	public GildorymStats(int uid, int stamina, int magicalStamina, int lockpickStamina) {
		this(uid);
		this.stamina = stamina;
		this.magicalStamina = magicalStamina;
		this.lockpickStamina = lockpickStamina;
	}
	
	public int getStatsUid() {
		return statsUid;
	}

	public void setStatsUid(int statsUid) {
		this.statsUid = statsUid;
	}

	public int getStamina() {
		return stamina;
	}

	public void setStamina(int stamina) {
		this.stamina = stamina;
	}

	public int getMagicalStamina() {
		return magicalStamina;
	}

	public void setMagicalStamina(int magicalStamina) {
		this.magicalStamina = magicalStamina;
	}

	public int getLockpickStamina() {
		return lockpickStamina;
	}

	public void setLockpickStamina(int lockpickStamina) {
		this.lockpickStamina = lockpickStamina;
	}

	@Override
	public String toString() {
		return "GildorymStats [statsUid=" + statsUid + ", stamina=" + stamina
				+ ", magicalStamina=" + magicalStamina + ", lockpickStamina="
				+ lockpickStamina + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + lockpickStamina;
		result = prime * result + magicalStamina;
		result = prime * result + stamina;
		result = prime * result + statsUid;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GildorymStats other = (GildorymStats) obj;
		if (lockpickStamina != other.lockpickStamina)
			return false;
		if (magicalStamina != other.magicalStamina)
			return false;
		if (stamina != other.stamina)
			return false;
		if (statsUid != other.statsUid)
			return false;
		return true;
	}
}
