package com.gildorymrp.gildorym;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import net.ess3.api.IEssentials;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.gildorymrp.charactercards.CharacterBehavior;
import com.gildorymrp.charactercards.CharacterCard;
import com.gildorymrp.charactercards.CharacterMorality;
import com.gildorymrp.charactercards.Gender;
import com.gildorymrp.charactercards.Race;
import com.gildorymrp.charactercards.Subrace;
import com.gildorymrp.gildorymclasses.CharacterClass;

public class Gildorym extends JavaPlugin {

	public Economy economy;
	private Map<String, GildorymCharacter> activeCharacters = new HashMap<String, GildorymCharacter>();
	
	private MySQLDatabase sqlDB;
	
	
	public void onEnable() {
		this.economy = this.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class).getProvider();
		if (this.economy == null) {
			this.getLogger().severe("Could not find a compatible economy, disabling!");
			this.setEnabled(false);
		}
				
		this.getCommand("newcharacter").setExecutor(new NewCharacterCommand(this, (IEssentials) this.getServer().getPluginManager().getPlugin("Essentials")));
		this.getCommand("setname").setExecutor(new SetNameCommand(this));
		this.getCommand("setnameother").setExecutor(new SetNameOtherCommand(this));
		this.getCommand("rollinfo").setExecutor(new RollInfoCommand(this));
		this.getCommand("radiusemote").setExecutor(new RadiusEmoteCommand());
		RollCommand rc = new RollCommand(this);
		this.getCommand("roll").setExecutor(rc);
		this.getCommand("dmroll").setExecutor(rc);
		MetaEditorCommands mec = new MetaEditorCommands();
		this.getCommand("renameitem").setExecutor(mec);
		this.getCommand("setlore").setExecutor(mec);
		this.getCommand("addlore").setExecutor(mec);
		this.getCommand("removelore").setExecutor(mec);
		this.getCommand("signitem").setExecutor(mec);
		
		this.activeCharacters = loadActiveCharacters();
		
		this.getServer().getPluginManager().registerEvents(new EntityDamageListener(this), this);
		this.getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
		this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
		this.getServer().getPluginManager().registerEvents(new SignChangeListener(), this);
		
		String dbHost = this.getConfig().getString("database.host");
		String port = this.getConfig().getString("database.port");
		String db = this.getConfig().getString("database.database");
		String user = this.getConfig().getString("database.username");
		String pass = this.getConfig().getString("database.password");
		
		sqlDB = new MySQLDatabase(this, dbHost, port, db, user, pass);
		sqlDB.connect();
		sqlDB.initDatabase();
		
		getLogger().info("Successfully connected to MySQL Database");
		
	}
	
	public void onDisable() {
		Set<String> keys = activeCharacters.keySet();
		
		for(String str : keys) {
			GildorymCharacter gChar = activeCharacters.get(str);
			sqlDB.saveCharacter(gChar);
		}
	}
	
	public Map<String, GildorymCharacter> getActiveCharacters() {
		return activeCharacters;
	}
	
	public Map<String, GildorymCharacter> loadActiveCharacters() {
		Map<String, GildorymCharacter> activeCharacters = new HashMap<String, GildorymCharacter>();
		for (Player player : this.getServer().getOnlinePlayers()) {
			GildorymCharacter gChar = loadCharacter(player);
			if (gChar != null) {
				activeCharacters.put(player.getName(), gChar);
			}
		}
		return activeCharacters;
	}
	
	/**
	 * Loads the character of the specified player.<br>
	 * <br>
	 * <ul>
	 * 	<li>
	 * 		If the active character is valid ({@code not NULL}):<br>
	 *			- The current active character is returned
	 *	</li>
	 *	<li>
	 *		If the active character is not valid ({@code NULL}):<br>
	 *			<ol>
	 *				<li>
	 *					If the created list is not valid ({@code NULL or EMPTY}):
	 *						<p>- A new reasonable default character, which is then stored (with the newly generated uid set appropriately) and returned</p>
	 *				</li>
	 *				<li>
	 *					If the created list is valid ({@code not NULL and not EMPTY}):
	 *						<p>- Their oldest character, which is set as the new active character and returned</p>
	 *				</li>
	 *			</ol>
	 *	</li>
	 * </ul>
	 * 
	 * @param player the player to load the character for
	 * @return the active character for {@code player}
	 */
	public GildorymCharacter loadCharacter(Player player) {
		// Check if they have a character in the database, if so, retrieves their active character and returns it.
		// If they do not have a character in the database, a new character in their name should be added to the database and returned.
		
		GildorymCharacter result = null;
		int[] activeAndCreated = sqlDB.getActive(player.getName());
		
		int active = activeAndCreated != null ? activeAndCreated[0] : 0;
		if(active == 0) {
			if(activeAndCreated != null) {
				/*
				 * If they do not have a valid active character, AND they have 
				 * created characters in the past, AND those characters haven't been
				 * deleted (meaning they are still some in the database), then set
				 * the last result (the youngest) created character as the active character
				 * and return that
				 * 
				 * Likely scenario: Latest character died.
				 */
				int createdId = activeAndCreated[1];
				if(createdId != -1) {
					List<CreatedCharacterInfo> cciList = sqlDB.getCreatedCharacterInfo(createdId);
					if(cciList.size() > 0) {
						CreatedCharacterInfo cci = cciList.get(cciList.size() - 1); 
						result = sqlDB.loadCharacter(cci.getCharUid());
					}
				}
			}
			
			if(result == null) {
				/*
				 * Otherwise, if they do not have an active character AND they do not have
				 * any created characters, create a reasonable default character, store it,
				 * and return it
				 * 
				 * Likely scenario: First time on the server / only character died
				 */
				result = createDefaultCharacter(player);
				
				if(!sqlDB.saveCharacter(result))
					throw new AssertionError("Assertion failed: saving the character was not successful");
				
				// Get the created character id, which will have just been generated if this is the first
				// time the player has joined the server
				
				int createdCharacterId = sqlDB.getActive(player.getName())[1];
				
				CreatedCharacterInfo cci = new CreatedCharacterInfo(result.getUid(), createdCharacterId, System.currentTimeMillis(), -1, "AUTO-GENERATED");
				sqlDB.addCreatedCharacterInfo(cci);
			}
		}else {
			result = sqlDB.loadCharacter(activeAndCreated[0]);
		}
		// It should only return null due to an SQLException or something similar
		return result;
	}

	public void onInjury(Player injured, String type, int dieSize, int fallDistance, int roll) {
		ChatColor b = ChatColor.BLUE;
		ChatColor r = ChatColor.RED;
		ChatColor w = ChatColor.WHITE;
		ChatColor dr = ChatColor.DARK_RED;
		ChatColor g = ChatColor.GOLD;
		
		int severity, fallInFeet, rollPercent, severityPercent, x, y, z, injuryRoll;
		int damageValue = 0;
		
		fallInFeet = fallDistance * 3;
		severity = (new Random()).nextInt(dieSize) + 1;
		rollPercent = roll * 5;
		severityPercent = severity * 2;
		x = injured.getLocation().getBlockX();
		y = injured.getLocation().getBlockY();
		z = injured.getLocation().getBlockZ();
		injuryRoll = (new Random()).nextInt(3) + 1;
		
		String message, reflexAlert, injuryAlert, alert, injury, deathLocation, damage;

		message = "You have fallen " + w + "" + fallInFeet + "";
		reflexAlert = r + "Reflex: " + w + rollPercent + r + "%";
		injuryAlert = r + "  Injury: " + w + severityPercent + r + "%";
		deathLocation = injured.getWorld().getName() + ": x: " + x + " y: " + y + " z: " + z;
		alert = injured.getName() + b + " has just fallen " + w + fallDistance + b + " blocks, recieving ";
		
		
		if(type.equalsIgnoreCase("none")){
			message += b + "feet";
			injury = " escaping without injury.";
			injured.sendMessage(reflexAlert);
			injured.sendMessage(b+ message + b + injury);
			
		} else if (type.equalsIgnoreCase("major")) {
			message += r + " feet, recieving ";
			injury = dr + "error";
			damage = dr + "error";
			
			if (severity < 16) {
				damage = w + " 4" + r + " damage";
				damageValue = 4;
				
				switch (injuryRoll) {
				case 1: injury = "a punctured organ. (anything but the heart);";
					break;
				case 2: injury = "a completely crushed limb.;";
					break;
				case 3: injury = "a cracked skull or cracked vertebra.;";
					break;
				default: 
					break;
				}
				injured.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 35000, 3), true); 
				injured.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 35000, 3), true);
				
			} else if (severity < 31) {
				damage = w + " 3" + r + " damage";
				damageValue = 3;
				
				switch (injuryRoll) {
				case 1: injury = "a crushed limb (Such as hand or foot);";
					break;
				case 2: injury = "shattered bones.;";
					break;
				case 3: injury = "a severe concussion.;";
					break;
				default: 
					break;
				}
				injured.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 35000, 3), true);
				
			} else if (severity < 51) {
				damage = w + " 2" + r + " damage";
				damageValue = 2;
				
				switch (injuryRoll) {
				case 1: injury = "a cleanly broken bone;";
					break;
				case 2: injury = "a torn major muscle.;";
					break;
				case 3: injury = "the loss of a minor limb.;";
					break;
				default: 
					break;
				}
				injured.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 35000, 2), true);
				
			}
			injured.sendMessage(reflexAlert + injuryAlert);
			injured.sendMessage(r + message + r + injury + damage);
			
			for (Player player : Bukkit.getServer().getOnlinePlayers()) {
				if (player.hasPermission("gildorym.falldamage.alert")) {
					player.sendMessage(alert + r + injury + " major");
					player.sendMessage(reflexAlert + r + "  Injury: " + w + severity);
				}
			}
		} else if (type.equalsIgnoreCase("minor")) {
			message += g + " feet, recieving ";
			injury = dr + "error";
			damage = dr + "error";
			
			if (severity < 11) {
				damage = w + " 1" + g + " damage";
				damageValue = 1;
				
				switch (injuryRoll) {
				case 1: injury = "a minor fracture;";
					break;
				case 2: injury = "bruised ribs.;";
					break;
				case 3: injury = "dislocation.;";
					break;
				default: 
					break;
				}
				injured.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 35000, 1), true);
				
			} else if (severity < 21) {
				damage = w + " 1" + g + " damage";
				damageValue = 1;
				
				switch (injuryRoll) {
				case 1: injury = "a minor torn ligement;";
					break;
				case 2: injury = "chipped bone.;";
					break;
				case 3: injury = "light concussion.;";
					break;
				default: 
					break;
				}
				injured.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 35000, 1), true);
				
			} else if (severity < 35) {
				damage = w + " 1" + g + " damage";
				damageValue = 1;
				
				switch (injuryRoll) {
				case 1: injury = "a mild sprain;";
					break;
				case 2: injury = "a mild laceration.;";
					break;
				case 3: injury = "a badly pulled muscle.;";
					break;
				default: 
					break;
				}
				injured.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 15000, 1), true);
				
			} else if (severity < 50) {
				damage = w + " 1" + g + " damage";
				damageValue = 1;
				
				switch (injuryRoll) {
				case 1: injury = "bruises;";
					break;
				case 2: injury = "mild lacerations.;";
					break;
				case 3: injury = "minor sprain.;";
					break;
				default: 
					break;
				}
				injured.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 5000, 0), true);
			} else if (severity == 50) {
				damage = "";
				
				injury = "only a few scratches.";
				
			}
			injured.sendMessage(reflexAlert + injuryAlert);
			injured.sendMessage(g + message + g + injury + damage);
			
			for (Player player : Bukkit.getServer().getOnlinePlayers()) {
				if (player.hasPermission("gildorym.falldamage.alert")) {
					player.sendMessage(alert + g + injury + " minor");
					player.sendMessage(reflexAlert + r + "  Injury: " + w + severity);
				}
			}
		} else if(type.equalsIgnoreCase("death")){
			message += dr + " feet, ";
			injury = "and died";
			injured.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 999999999, 10), true);
			
			injured.sendMessage(dr + message + dr + injury);
			for (Player player : Bukkit.getServer().getOnlinePlayers()) {
				if (player.hasPermission("gildorym.falldamage.alert")) {
					player.sendMessage(alert + dr + injury);
					player.sendMessage(reflexAlert);
					player.sendMessage(deathLocation);
				}
			}
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "warp deathbox " + injured.getName());
		} else {
			injured.sendMessage(ChatColor.RED + "Error : Injury type "
					+ ChatColor.RESET + type + ChatColor.RED
					+ " does not exist.");
		}
		
		if (damageValue > 0) {
			CharacterCard characterCard = getActiveCharacters().get(injured.getName()).getCharCard();
			if (damageValue >= characterCard.getHealth()) {
				characterCard.setHealth(0);
				for (Player player : Bukkit.getServer().getOnlinePlayers()) {
					if (player.hasPermission("gildorym.falldamage.alert")) {
						player.sendMessage(injured.getName() + b + "'s health was reduced to 0 by the fall!");
					}
				}
			} else {
				characterCard.setHealth(characterCard.getHealth() - damageValue);
			}
		}

	}


	public static GildorymCharacter createDefaultCharacter(Player player) {
		return new GildorymCharacter(-1, // uid
				"", // mc name
				player.getName(), // name
				new CharacterCard(0, // age
						Gender.UNKNOWN, // gender 
						"", // description
						Race.UNKNOWN, // race
						Subrace.UNKNOWN, // subrace 
						1, // level
						CharacterClass.UNKNOWN, // class
						CharacterBehavior.UNKNOWN, // behavior
						CharacterMorality.UNKNOWN),  // morality
						null, // profession 1
						null, // profession 2
						new Specialization(-1, // specialization id
								-1, // child specialization
								0, // base attack
								0, // fort save
								0, // ref save
								0, // will save
								-1), // feat id
						CharacterClass.UNKNOWN, // class
						null, // deity
						1, // birthday
						0, // level
						0, // experience
						new GildorymStats(
								-1, // stats id
								0, // stamina 
								0, // magical stamina 
								0  // lockpick stamina
						), 
						player.getLocation().getX(), // x
						player.getLocation().getY(), // y
						player.getLocation().getZ(), // z
						player.getWorld().getName(), // world 
						-1, // wounds id
						null); // init wounds
	}

	public MySQLDatabase getMySQLDatabase() {
		return sqlDB;
	}
	
}
