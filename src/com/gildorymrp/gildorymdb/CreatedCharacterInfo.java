package com.gildorymrp.gildorymdb;

public class CreatedCharacterInfo {
	private int id;
	private int charUid;
	private long createdUTC;
	private String approvedBy;
	
	public CreatedCharacterInfo(int id) {
		this.id = id;
	}
	
	/**
	 * Creates information about a created character
	 * @param id
	 * @param charUid
	 * @param createdUTC
	 * @param approvedBy
	 */
	public CreatedCharacterInfo(int id, int charUid, long createdUTC,
			String approvedBy) {
		this.id = id;
		this.charUid = charUid;
		this.createdUTC = createdUTC;
		this.approvedBy = approvedBy;
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

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	@Override
	public String toString() {
		return "CreatedCharacterInfo [id=" + id + ", charUid=" + charUid
				+ ", createdUTC=" + createdUTC + ", approvedBy=" + approvedBy
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((approvedBy == null) ? 0 : approvedBy.hashCode());
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
		if (approvedBy == null) {
			if (other.approvedBy != null)
				return false;
		} else if (!approvedBy.equals(other.approvedBy))
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
