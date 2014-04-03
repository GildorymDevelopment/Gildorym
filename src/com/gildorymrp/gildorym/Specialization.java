package com.gildorymrp.gildorym;

public class Specialization {
	private int id;
	private int childSpecialization;
	private int baseAttackMod;
	private int fortSave;
	private int refSave;
	private int willSave;
	private int featId;
	
	public Specialization(int id, int childSpecialization, int baseAttackMod, int fortSave, int refSave,
			int willSave, int featId) {
		this.id = id;
		this.childSpecialization = childSpecialization;
		this.baseAttackMod = baseAttackMod;
		this.fortSave = fortSave;
		this.refSave = refSave;
		this.willSave = willSave;
		this.featId = featId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBaseAttackMod() {
		return baseAttackMod;
	}

	public void setBaseAttackMod(int baseAttackMod) {
		this.baseAttackMod = baseAttackMod;
	}
	
	public int getChildSpecialization() {
		return childSpecialization;
	}
	
	public void setChildSpecialization(int childSpecialization) {
		this.childSpecialization = childSpecialization;
	}

	public int getFortSave() {
		return fortSave;
	}

	public void setFortSave(int fortSave) {
		this.fortSave = fortSave;
	}

	public int getRefSave() {
		return refSave;
	}

	public void setRefSave(int refSave) {
		this.refSave = refSave;
	}

	public int getWillSave() {
		return willSave;
	}

	public void setWillSave(int willSave) {
		this.willSave = willSave;
	}

	public int getFeatId() {
		return featId;
	}

	public void setFeatId(int featId) {
		this.featId = featId;
	}

	@Override
	public String toString() {
		return "Specialization [id=" + id + ", baseAttackMod=" + baseAttackMod
				+ ", fortSave=" + fortSave + ", refSave=" + refSave
				+ ", willSave=" + willSave + ", childSpecializaiton=" + childSpecialization 
				+ ", featId=" + featId + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + baseAttackMod;
		result = prime * result + featId;
		result = prime * result + fortSave;
		result = prime * result + id;
		result = prime * result + refSave;
		result = prime * result + willSave;
		result = prime * result + childSpecialization;
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
		Specialization other = (Specialization) obj;
		if (baseAttackMod != other.baseAttackMod)
			return false;
		if (featId != other.featId)
			return false;
		if (fortSave != other.fortSave)
			return false;
		if (id != other.id)
			return false;
		if (refSave != other.refSave)
			return false;
		if (willSave != other.willSave)
			return false;
		if(childSpecialization != other.childSpecialization)
			return false;
		return true;
	}
}
