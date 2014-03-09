package com.gildorymrp.gildorym;

public class CreatedCharacterInfo {
	private int id;
	private int charUid;
	private long createdUTC;
	private String generationMethod;
	
	public CreatedCharacterInfo(int id) {
		this.id = id;
	}
	
	/**
	 * Creates information about a created character
	 * @param id
	 * @param charUid
	 * @param createdUTC
	 * @param generationMethod
	 */
	public CreatedCharacterInfo(int id, int charUid, long createdUTC,
			String generationMethod) {
		this.id = id;
		this.charUid = charUid;
		this.createdUTC = createdUTC;
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

	public String getgenerationMethod() {
		return generationMethod;
	}

	public void setgenerationMethod(String generationMethod) {
		this.generationMethod = generationMethod;
	}

	@Override
	public String toString() {
		return "CreatedCharacterInfo [id=" + id + ", charUid=" + charUid
				+ ", createdUTC=" + createdUTC + ", generationMethod=" + generationMethod
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((generationMethod == null) ? 0 : generationMethod.hashCode());
		result = prime * result + charUid;
		result = prime * result + (int) (createdUTC ^ (createdUTC >>> 32));
		result = prime * result + id;
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
		if (generationMethod == null) {
			if (other.generationMethod != null)
				return false;
		} else if (!generationMethod.equals(other.generationMethod))
			return false;
		if (charUid != other.charUid)
			return false;
		if (createdUTC != other.createdUTC)
			return false;
		if (id != other.id)
			return false;
		return true;
	}
}
