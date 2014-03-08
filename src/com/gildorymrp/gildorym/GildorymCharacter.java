package com.gildorymrp.gildorym;

import java.util.Arrays;

import com.gildorymrp.charactercards.CharacterCard;
import com.gildorymrp.gildorymclasses.CharacterBehavior;
import com.gildorymrp.gildorymclasses.CharacterClass;
import com.gildorymrp.gildorymclasses.CharacterMorality;
import com.gildorymrp.gildorymclasses.CharacterProfession;

/**
 * Everything that describes a character in gildorym
 * 
 * @author Timothy
 */
public class GildorymCharacter {
	private int uid;
	private String name;
	private String mcName;
	private CharacterCard charCard;
	private CharacterProfession[] professions;
	private CharacterClass charClass;
	private int level;
	private int experience;
	private int stamina;
	private int magicalStamina;
	private CharacterMorality morality;
	private CharacterBehavior behavior;
	private double x, y, z;
	private String world;
	
	public GildorymCharacter(int uid) {
		this.uid = uid;
	}
	
	public GildorymCharacter(int uid, String name, String mcName,
			CharacterCard CharCard, CharacterProfession[] professions,
			CharacterClass CharClass, int level, int experience, int stamina,
			int magicalStamina, CharacterMorality morality,
			CharacterBehavior behavior, double x, double y, double z, String world) {
		this(uid);
		this.name = name;
		this.mcName = mcName;
		this.charCard = CharCard;
		this.professions = professions;
		this.charClass = CharClass;
		this.level = level;
		this.experience = experience;
		this.stamina = stamina;
		this.magicalStamina = magicalStamina;
		this.morality = morality;
		this.behavior = behavior;
		this.x = x;
		this.y = y;
		this.z = z;
		this.world = world;
	}
	
	
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMcName() {
		return mcName;
	}
	public void setMcName(String mcName) {
		this.mcName = mcName;
	}
	public CharacterCard getCharCard() {
		return charCard;
	}
	public void setCharCard(CharacterCard charCard) {
		this.charCard = charCard;
	}
	public CharacterProfession[] getProfessions() {
		return professions;
	}
	public void setProfessions(CharacterProfession[] professions) {
		this.professions = professions;
	}
	public CharacterClass getCharClass() {
		return charClass;
	}
	public void setCharClass(CharacterClass charClass) {
		this.charClass = charClass;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getExperience() {
		return experience;
	}
	public void setExperience(int experience) {
		this.experience = experience;
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
	public CharacterMorality getMorality() {
		return morality;
	}
	public void setMorality(CharacterMorality morality) {
		this.morality = morality;
	}
	public CharacterBehavior getBehavior() {
		return behavior;
	}
	public void setBehavior(CharacterBehavior behavior) {
		this.behavior = behavior;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getZ() {
		return z;
	}
	public void setZ(double z) {
		this.z = z;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GildorymCharacter other = (GildorymCharacter) obj;
		if (behavior != other.behavior)
			return false;
		if (charCard == null) {
			if (other.charCard != null)
				return false;
		} else if (!charCard.equals(other.charCard))
			return false;
		if (charClass != other.charClass)
			return false;
		if (experience != other.experience)
			return false;
		if (level != other.level)
			return false;
		if (magicalStamina != other.magicalStamina)
			return false;
		if (mcName == null) {
			if (other.mcName != null)
				return false;
		} else if (!mcName.equals(other.mcName))
			return false;
		if (morality != other.morality)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (!Arrays.equals(professions, other.professions))
			return false;
		if (stamina != other.stamina)
			return false;
		if (uid != other.uid)
			return false;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GildorymCharacter [uid=" + uid + ", name=" + name + ", mcName="
				+ mcName + ", charCard=" + charCard + ", professions="
				+ Arrays.toString(professions) + ", charClass=" + charClass
				+ ", level=" + level + ", experience=" + experience
				+ ", stamina=" + stamina + ", magicalStamina=" + magicalStamina
				+ ", morality=" + morality + ", behavior=" + behavior + ", x="
				+ x + ", y=" + y + ", z=" + z + "]";
	}

	public String getWorld() {
		return world;
	}

	public void setWorld(String world) {
		this.world = world;
	}
}
