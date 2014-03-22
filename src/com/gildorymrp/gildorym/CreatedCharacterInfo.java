package com.gildorymrp.gildorym;

public class CreatedCharacterInfo {
	private int id;
	private int charUid;
	private long createdUTC;
	private long storedUTC;
	private String generationMethod;
	
	public CreatedCharacterInfo(int id) {
		this.id = id;
		this.storedUTC = -1;
	}
	
	/**
	 * Creates information about a created character
	 * @param id
	 * @param charUid
	 * @param createdUTC
	 * @param storedUTC
	 * @param generationMethod
	 */
	public CreatedCharacterInfo(int id, int charUid, long createdUTC, long storedUTC,
			String generationMethod) {
		this.id = id;
		this.charUid = charUid;
		this.createdUTC = createdUTC;
		this.storedUTC = storedUTC;
		this.generationMethod = generationMethod;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCharUid() {
		return charUid;
	}

	public void setCharUid(int charUid) {
		this.charUid = charUid;
	}

	public long getCreatedUTC() {
		return createdUTC;
	}

	public void setCreatedUTC(long createdUTC) {
		this.createdUTC = createdUTC;
	}

	public long getStoredUTC() {
		return storedUTC;
	}

	public void setStoredUTC(long storedUTC) {
		this.storedUTC = storedUTC;
	}

	public String getGenerationMethod() {
		return generationMethod;
	}

	public void setGenerationMethod(String generationMethod) {
		this.generationMethod = generationMethod;
	}

	@Override
	public String toString() {
		return "CreatedCharacterInfo [id=" + id + ", charUid=" + charUid
				+ ", createdUTC=" + createdUTC + ", storedUTC=" + storedUTC
				+ ", generationMethod=" + generationMethod + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + charUid;
		result = prime * result + (int) (createdUTC ^ (createdUTC >>> 32));
		result = prime
				* result
				+ ((generationMethod == null) ? 0 : generationMethod.hashCode());
		result = prime * result + id;
		result = prime * result + (int) (storedUTC ^ (storedUTC >>> 32));
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
		CreatedCharacterInfo other = (CreatedCharacterInfo) obj;
		if (charUid != other.charUid)
			return false;
		if (createdUTC != other.createdUTC)
			return false;
		if (generationMethod == null) {
			if (other.generationMethod != null)
				return false;
		} else if (!generationMethod.equals(other.generationMethod))
			return false;
		if (id != other.id)
			return false;
		if (storedUTC != other.storedUTC)
			return false;
		return true;
	}
}
