package com.gildorymrp.gildorym;

public class Wound {
	private int woundUid;
	private int woundID;
	
	private long timestamp;
	private DamageType damageType;
	private int damageAmount;
	private long timeRegen; // When the wound is regened, e.g. if it was inflicted at 5 and it took 5 seconds to heal, timeRegen would be 10 (5 + 5)
	private String notes;
	
	public Wound(int uid) {
		this.woundUid = uid;
	}
	
	public Wound(long timestamp, DamageType damageType, int damageAmount,
			long timeRegen, String notes) {
		this.timestamp = timestamp;
		this.damageType = damageType;
		this.damageAmount = damageAmount;
		this.timeRegen = timeRegen;
		this.notes = notes;
	}



	public int getWoundID() {
		return woundID;
	}
	public void setWoundID(int woundID) {
		this.woundID = woundID;
	}
	public int getWoundUID() {
		return woundUid;
	}
	public void setWoundUID(int woundUid) {
		this.woundUid = woundUid;
	}
	
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public DamageType getDamageType() {
		return damageType;
	}
	public void setDamageType(DamageType damageType) {
		this.damageType = damageType;
	}
	public int getDamageAmount() {
		return damageAmount;
	}
	public void setDamageAmount(int damageAmount) {
		this.damageAmount = damageAmount;
	}
	public long getTimeRegen() {
		return timeRegen;
	}
	public void setTimeRegen(long timeRegen) {
		this.timeRegen = timeRegen;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Override
	public String toString() {
		return "Wound [woundUID=" + woundUid + ", woundID=" + woundID +
				", timestamp=" + timestamp + ", damageType=" + damageType + 
				", damageAmount=" + damageAmount + ", timeRegen=" + timeRegen + 
				", notes=" + notes + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + woundUid;
		result = prime * result + damageAmount;
		result = prime * result
				+ ((damageType == null) ? 0 : damageType.hashCode());
		result = prime * result + ((notes == null) ? 0 : notes.hashCode());
		result = prime * result + (int) (timeRegen ^ (timeRegen >>> 32));
		result = prime * result + (int) (timestamp ^ (timestamp >>> 32));
		result = prime * result + woundID;
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
		Wound other = (Wound) obj;
		if(woundUid != other.woundUid)
			return false;
		if (damageAmount != other.damageAmount)
			return false;
		if (damageType != other.damageType)
			return false;
		if (notes == null) {
			if (other.notes != null)
				return false;
		} else if (!notes.equals(other.notes))
			return false;
		if (timeRegen != other.timeRegen)
			return false;
		if (timestamp != other.timestamp)
			return false;
		if (woundID != other.woundID)
			return false;
		return true;
	}
}
