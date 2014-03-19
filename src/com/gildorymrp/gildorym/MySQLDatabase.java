package com.gildorymrp.gildorym;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

import com.gildorymrp.gildorymclasses.CharacterClass;
import com.gildorymrp.charactercards.CharacterBehavior;
import com.gildorymrp.charactercards.CharacterCard;
import com.gildorymrp.charactercards.CharacterMorality;
import com.gildorymrp.charactercards.Gender;
import com.gildorymrp.charactercards.Race;
import com.gildorymrp.charactercards.Subrace;
import com.gildorymrp.gildorymclasses.CharacterProfession;

public class MySQLDatabase {

	private static final String REPLACE_CHAR_STATEMENT =
			"REPLACE INTO characters (" +
					"uid, " +
					"char_name, " +
					"minecraft_account_name, " +
					"age, " +
					"gender, " +
					"description, " +
					"race, " +
					"sub_race, " +
					"health, " +
					"`class`, " +
					"profession1, " +
					"profession2, " +
					"deity, " + 
					"birthday, " +
					"`level`, " +
					"experience, " +
					"stamina, " +
					"magical_stamina, " +
					"lockpick_stamina, " +
					"morality, " +
					"behavior, " +
					"wounds_id, " +
					"x, " +
					"y, " +
					"z, " +
					"world) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

	private static final String INSERT_CHAR_STATEMENT =
			"INSERT INTO characters (" +
					"char_name, " +
					"minecraft_account_name, " +
					"age, " +
					"gender, " +
					"description, " +
					"race, " +
					"sub_race, " +
					"health, " +
					"`class`, " +
					"profession1, " +
					"profession2, " +
					"deity, " + 
					"birthday, " +
					"`level`, " +
					"experience, " +
					"stamina, " +
					"magical_stamina, " +
					"lockpick_stamina, " +
					"morality, " +
					"behavior, " +
					"wounds_id, " +
					"x, " +
					"y, " +
					"z, " +
					"world) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

	private static final String SELECT_CHAR_STATEMENT =
			"SELECT * FROM characters WHERE uid=?";

	private static final String REPLACE_PLAYER_CUR_CHAR =
			"REPLACE INTO players (" +
					"minecraft_account_name," +
					"current_character_uid) VALUES (?, ?);";
	private static final String REPLACE_PLAYER_CUR_CHAR_CREATED_CHAR =
			"REPLACE INTO players (" +
					"minecraft_account_name, " +
					"created_characters_id, " +
					"current_character_uid) VALUES (?, ?, ?);";

	private static final String SELECT_CUR_CHAR_CREATED =
			"SELECT * FROM players WHERE minecraft_account_name=?";

	private static final String SELECT_CREATED_CHARS =
			"SELECT * FROM created_characters WHERE id = ?;";

	private static final String INSERT_CREATED_CHAR =
			"INSERT INTO created_characters (id, char_uid, created_utc, generation_method) " +
					"VALUES(?, ?, ?, ?);";

	private static final String CLEAR_CREATED_CHARS =
			"DELETE FROM created_characters WHERE id = ?;";
	
	private static final String INSERT_WOUND = 
			"INSERT INTO wounds (wound_id, timestamp, damage_type, " +
			"damage_amount, regen_time, notes) VALUES(?, ?, ?, ?, ?, ?);";
	
	private static final String SELECT_WOUNDS_BY_ID =
			"SELECT * FROM wounds WHERE wound_id = ?";
	
	private static final String PRUNE_WOUNDS_BY_ID = 
			"DELETE FROM wounds WHERE wound_id = ? AND regen_time < ?";
	
	private static final String DELETE_WOUND =
			"DELETE FROM wounds WHERE wound_uid = ? LIMIT 1";

	private final String HOSTNAME;
	private final String PORT;
	private final String DATABASE;
	private final String USERNAME;
	private final String PASSWORD;
	private JavaPlugin plugin;
	private Connection conn;

	public MySQLDatabase(JavaPlugin plugin, String hostname, String port, String database, String username, String password) {
		HOSTNAME = hostname;
		PORT = port;
		DATABASE = database;
		USERNAME = username;
		PASSWORD = password;
		this.plugin = plugin;
		conn = null;
	}

	public boolean connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://" + HOSTNAME + ":" + PORT + "/" + DATABASE, USERNAME, PASSWORD);
			return true;
		} catch (SQLException ex) {
			plugin.getLogger().log(Level.SEVERE, "Unable to establish MySQL connection to " + HOSTNAME + ":" + PORT + "/" + DATABASE);
			ex.printStackTrace();
		} catch (Exception ex) {
			plugin.getLogger().log(Level.SEVERE, "Unable to detect JDBC drivers for MySQL!");
			ex.printStackTrace();
		}
		return false;
	}

	/**
	 * Initializes the database as necessary.
	 */
	public void initDatabase() {
		if(!isConnected()) {
			throw new NullPointerException("Not connected to the database");
		}
		try {
			Statement statement = conn.createStatement();
			statement.execute("CREATE TABLE IF NOT EXISTS `characters` (" +
					"`uid` int(11) NOT NULL AUTO_INCREMENT," +
					"`char_name` text," +
					"`minecraft_account_name` text," +
					"`age` int(11) DEFAULT NULL," +
					"`gender` varchar(10) DEFAULT NULL," +
					"`description` text," +
					"`race` varchar(20) DEFAULT NULL," +
					"`sub_race` TEXT DEFAULT NULL," +
					"`health` int(11) DEFAULT NULL," +
					"`class` text," +
					"`profession1` varchar(20)," +
					"`profession2` varchar(20)," +
					"`deity` TEXT DEFAULT NULL, " + 
					"`birthday` int(11) DEFAULT NULL, " +
					"`level` int(11) DEFAULT NULL," +
					"`experience` int(11) DEFAULT NULL," +
					"`stamina` int(11) DEFAULT NULL," +
					"`magical_stamina` int(11) DEFAULT NULL," +
					"`lockpick_stamina` int(11) DEFAULT NULL," +
					"`morality` varchar(10) DEFAULT NULL," +
					"`behavior` varchar(10) DEFAULT NULL," +
					"`wounds_id` int(11) DEFAULT NULL," +
					"`x` double DEFAULT NULL," +
					"`y` double DEFAULT NULL," +
					"`z` double DEFAULT NULL," +
					"`world` varchar(20) DEFAULT 'world', " +
					"PRIMARY KEY (`uid`)" +
					");");

			statement.execute("CREATE TABLE IF NOT EXISTS `players` (" +
					"`minecraft_account_name` text NOT NULL," +
					"`created_characters_id` int(11) DEFAULT NULL," +
					"`current_character_uid` int(11) DEFAULT NULL" +
					");");

			statement.execute("CREATE TABLE IF NOT EXISTS `created_characters` (" +
					"`id` int(11) NOT NULL, " +
					"`char_uid` int(11) NOT NULL DEFAULT -1," +
					"`created_utc` BIGINT NOT NULL DEFAULT -1, " +
					"`generation_method` TEXT DEFAULT NULL" +
					");");
			
			statement.execute("CREATE TABLE IF NOT EXISTS `wounds` (" +
					"`wound_uid` int(11) NOT NULL AUTO_INCREMENT, " +
					"`wound_id` int(11) NOT NULL, " +
					"`timestamp` BIGINT NOT NULL DEFAULT -1," +
					"`damage_type` text DEFAULT NULL, " +
					"`damage_amount` TINYINT DEFAULT NULL," +
					"`regen_time` BIGINT NOT NULL DEFAULT -1," +
					"`notes` text DEFAULT NULL, " +
					"PRIMARY KEY (`wound_uid`)" +
					");");
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
	}

	public boolean isConnected() {
		return (conn != null);
	}

	public void disconnect() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException ex) {
				plugin.getLogger().log(Level.SEVERE, "Unable to disconnect MySQL connection to " + HOSTNAME + ":" + PORT + "/" + DATABASE);
				ex.printStackTrace();
			}
		}
	}

	/**
	 * Saves the specified gildorym character into the 'characters' table,
	 * doing an insert if the characters uid is -1, otherwise doing a replace
	 * 
	 * In the event that an insert is made {@code gChar} will be modified to
	 * resemble the new character id, and the created character id will be created
	 * and accessible by {@code getActive(gChar.getMcName()}
	 * 
	 * @param gChar the character to save / update
	 * @return success if no error has occured
	 */
	public boolean saveCharacter(GildorymCharacter gChar) {
		try {
			int position = 1; // In the event that we are including ID, then we must increase these
			// numbers by this amount.
			PreparedStatement statement = null;
			if(gChar.getUid() != -1) {
				statement = conn.prepareStatement(REPLACE_CHAR_STATEMENT);

				statement.setInt(1, gChar.getUid());
				position = 2;

			}else {
				statement = conn.prepareStatement(INSERT_CHAR_STATEMENT);
			}

			statement.setString(position++, gChar.getName());
			statement.setString(position++, gChar.getMcName());
			statement.setInt(position++, gChar.getCharCard().getAge());
			statement.setString(position++, gChar.getCharCard().getGender().name());
			statement.setString(position++, gChar.getCharCard().getDescription());
			statement.setString(position++, gChar.getCharCard().getRace().name());
			Subrace subrace = 
					gChar.getCharCard().getSubrace() == null ? Subrace.defaultSubrace(gChar.getCharCard().getRace()) :
					gChar.getCharCard().getSubrace();
			statement.setString(position++, subrace.name());
			statement.setInt(position++, gChar.getCharCard().getHealth());
			statement.setString(position++, gChar.getCharClass() != null ? gChar.getCharClass().name() : null);
			statement.setString(position++, gChar.getProfession1() == null ? null : gChar.getProfession1().name());
			statement.setString(position++, gChar.getProfession2() == null ? null : gChar.getProfession2().name());
			statement.setString(position++, gChar.getDeity());
			statement.setInt(position++, gChar.getBirthday());
			statement.setInt(position++, gChar.getLevel());
			statement.setInt(position++, gChar.getExperience());
			statement.setInt(position++, gChar.getStamina());
			statement.setInt(position++, gChar.getMagicalStamina());
			statement.setInt(position++, gChar.getLockpickStamina());
			statement.setString(position++, gChar.getCharCard().getMorality() != null ? gChar.getCharCard().getMorality().name() : null);
			statement.setString(position++, gChar.getCharCard().getBehavior() != null ? gChar.getCharCard().getBehavior().name() : null);
			statement.setInt(position++, gChar.getWoundsID());
			statement.setDouble(position++, gChar.getX());
			statement.setDouble(position++, gChar.getY());
			statement.setDouble(position++, gChar.getZ());
			statement.setString(position++, gChar.getWorld());

			boolean res = statement.execute();

			if(gChar.getUid() == -1) {
				statement = conn.prepareStatement("SELECT LAST_INSERT_ID()");
				ResultSet results = statement.executeQuery();

				results.next();
				int uid = results.getInt(1);
				results.close();
				gChar.setUid(uid);
				
				statement = conn.prepareStatement("SELECT max(created_characters_id) FROM players");
				results = statement.executeQuery();
				
				results.next();
				int id = results.getInt(1) + 1;
				
				this.setPlayerCharactersCreatedAndActive(gChar.getMcName(), id, gChar.getUid());
				results.close();
			}
			return res;

		} catch (SQLException e) {
//			plugin.getLogger().log(Level.SEVERE, "Unable to save character " + gChar + "!");
			e.printStackTrace();
			System.exit(0);
		}
		return false;
	}

	public GildorymCharacter loadCharacter(int uid) {
		PreparedStatement statement;
		try {
			statement = conn.prepareStatement(SELECT_CHAR_STATEMENT);
			statement.setInt(1, uid);

			ResultSet results = statement.executeQuery();

			results.next();

			GildorymCharacter result = new GildorymCharacter(uid);
			result.setName("char_name");
			result.setMcName("minecraft_account_name");
			result.setCharCard(new CharacterCard(
					results.getInt("age"),
					Gender.valueOf(results.getString("gender")),
					results.getString("description"),
					Race.valueOf(results.getString("race")),
					Subrace.valueOf(results.getString("sub_race")),
					results.getInt("health"),
					CharacterClass.valueOf(results.getString("class")), 
					results.getString("behavior") != null ? 
							CharacterBehavior.valueOf(results.getString("behavior")) : CharacterBehavior.NEUTRAL, 
							results.getString("morality") != null ? CharacterMorality.valueOf(results.getString("morality")) : CharacterMorality.NEUTRAL));
			result.setCharClass(CharacterClass.valueOf(results.getString("class")));
			result.setProfession1(results.getString("profession1") == null ? null : CharacterProfession.valueOf(results.getString("profession1")));
			result.setProfession2(results.getString("profession2") == null ? null : CharacterProfession.valueOf(results.getString("profession2")));
			result.setDeity(results.getString("deity"));
			result.setBirthday(results.getInt("birthday"));
			result.setLevel(results.getInt("level"));
			result.setExperience(results.getInt("experience"));
			result.setStamina(results.getInt("stamina"));
			result.setMagicalStamina(results.getInt("magical_stamina"));
			result.setLockpickStamina(results.getInt("lockpick_stamina"));
			result.setWoundsID(results.getInt("wounds_id"));
			result.setX(results.getDouble("x"));
			result.setY(results.getDouble("y"));
			result.setZ(results.getDouble("z"));
			result.setWorld(results.getString("world"));

			results.close();


			return result;
		} catch (SQLException e) {
			plugin.getLogger().log(Level.SEVERE, "Unable to load character with uid " + uid + "!");
			e.printStackTrace();
		}

		return null;
	}

	public boolean setCurrentCharacter(String playerName, int uid) {
		try {
			PreparedStatement statement = conn.prepareStatement(REPLACE_PLAYER_CUR_CHAR);
			statement.setString(1, playerName);
			statement.setInt(2, uid);
			statement.execute();
			return true;
		}catch(SQLException e) {
			plugin.getLogger().log(Level.SEVERE, "Unable to set active character of " + playerName + " to " + uid + "!");
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * createdId ought to match with the players first created characters uid, although this
	 * is not required
	 *
	 * @param playerName the players minecraft account name
	 * @param createdId the id associated with characters created by this player
	 * @param uid the active characters unique identifier
	 */
	public boolean setPlayerCharactersCreatedAndActive(String playerName, int createdId, int uid) {
		try {
			PreparedStatement statement = conn.prepareStatement(REPLACE_PLAYER_CUR_CHAR_CREATED_CHAR);
			statement.setString(1, playerName);
			statement.setInt(2, createdId);
			statement.setInt(3, uid);
			statement.execute();
			return true;
		} catch (SQLException e) {
			plugin.getLogger().log(Level.SEVERE, "Unable to set active character of " + playerName + " to " + uid + "!");
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Returns the active character uid and the list of created
	 * character ids, such that the first (0) position in the array
	 * is the active and the second (1) position in the array is
	 * the id to find all of the created characters by this player
	 *
	 * @param playerName
	 * @return active character uid, created characters id or null
	 */
	public int[] getActive(String playerName) {
		PreparedStatement statement;
		try {
			statement = conn.prepareStatement(SELECT_CUR_CHAR_CREATED);
			statement.setString(1, playerName);

			ResultSet results = statement.executeQuery();

			if(!results.next()) {
				results.close();
				return null;
			}

			int curr = results.getInt("current_character_uid");
			int cre = results.getInt("created_characters_id");

			results.close();
			return new int[]{curr, cre};
		}catch(SQLException e) {
			plugin.getLogger().log(Level.SEVERE, "Unable to get player information about " + playerName);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Returns a list of created characters from the specified id (
	 * which can be retrieved per-player name using getActive(playerName))
	 *
	 * @param createdId the id corresponding with the characters
	 * @return a list of created characters (potentially empty) or null if an error occurs
	 */
	public List<CreatedCharacterInfo> getCreatedCharacterInfo(int createdId) {
		try {
			PreparedStatement statement = conn.prepareStatement(SELECT_CREATED_CHARS);

			statement.setInt(1, createdId);

			ResultSet resultSet = statement.executeQuery();

			List<CreatedCharacterInfo> result = new ArrayList<>();

			while(resultSet.next()) {
				CreatedCharacterInfo cci = new CreatedCharacterInfo(createdId);
				cci.setCharUid(resultSet.getInt("char_uid"));
				cci.setCreatedUTC(resultSet.getLong("created_utc"));
				cci.setGenerationMethod(resultSet.getString("generation_method"));
				result.add(cci);
			}

			resultSet.close();
			return result;
		}catch(SQLException e) {
			plugin.getLogger().log(Level.SEVERE, "Unable to retrieve created character info for id " + createdId);
			e.printStackTrace();
		}
		return null;
	}

	public boolean addCreatedCharacterInfo(CreatedCharacterInfo cci) {
		try {
			PreparedStatement statement = conn.prepareStatement(INSERT_CREATED_CHAR);

			statement.setInt(1, cci.getId());
			statement.setInt(2, cci.getCharUid());
			statement.setLong(3, cci.getCreatedUTC());
			statement.setString(4, cci.getGenerationMethod());

			statement.execute();
			return true;
		}catch(SQLException e) {
			plugin.getLogger().log(Level.SEVERE, "Unable to add created character info " + cci);
			e.printStackTrace();
			return false;
		}
	}

	public boolean clearCreatedCharacterInfo(int id) {
		try {
			PreparedStatement statement = conn.prepareStatement(CLEAR_CREATED_CHARS);

			statement.setInt(1, id);

			statement.execute();
			return true;
		}catch(SQLException e) {
			plugin.getLogger().log(Level.SEVERE, "Unable to retrieve clear created character info for id " + id);
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean inflictWound(GildorymCharacter gChar, Wound w) {
		try {
			PreparedStatement statement;
			if(gChar.getWoundsID() == -1) {
				// Assign a wound id
				statement = conn.prepareStatement("SELECT max(wound_id) FROM wounds");
				
				ResultSet resultSet = statement.executeQuery();
				resultSet.next();
				
				int max = resultSet.getInt(1);
				System.out.println("Assigned wound id " + (max + 1) + " to " + gChar.getName());
				resultSet.close();
				
				gChar.setWoundsID(max + 1);
				saveCharacter(gChar);
				w.setWoundID(max + 1);
				
			}else {
				w.setWoundID(gChar.getWoundsID());
			}
			
			statement = conn.prepareStatement(INSERT_WOUND);

			statement.setInt(1, w.getWoundID());
			statement.setLong(2, w.getTimestamp());
			statement.setString(3, w.getDamageType().name());
			statement.setInt(4, w.getDamageAmount());
			statement.setLong(5, w.getTimeRegen());
			statement.setString(6, w.getNotes());
			
			statement.execute();
			
			statement = conn.prepareStatement("SELECT LAST_INSERT_ID()");
			
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			int uid = resultSet.getInt(1);
			resultSet.close();
			w.setWoundUID(uid);
			return true;
		}catch(SQLException e) {
//			plugin.getLogger().log(Level.SEVERE, "Unable to inflict wound " + w + " on " + gChar.getName() + " [" + gChar.getUid() + "]");
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Removes any wounds that have healed from the gildorym character,
	 * and reloads the wounds. Can be used in replace of loadWounds if
	 * this behavior is preferred
	 * 
	 * @param gChar the character
	 * @return if the call was successful
	 */
	public boolean pruneWounds(GildorymCharacter gChar) {
		if(gChar.getWoundsID() == -1) {
			return true;
			
		}
		try {
			PreparedStatement statement;
			
			statement = conn.prepareStatement(PRUNE_WOUNDS_BY_ID);

			statement.setInt(1, gChar.getWoundsID());
			statement.setLong(2, System.currentTimeMillis());
			
			statement.execute();
			
			loadWounds(gChar);
			if(gChar.getWounds().isEmpty()) {
				gChar.setWoundsID(-1);
				saveCharacter(gChar);
			}
			return true;
		}catch(SQLException e) {
			plugin.getLogger().log(Level.SEVERE, "Unable to prune wounds on " + gChar.getName() + " [" + gChar.getUid() + "]");
			e.printStackTrace();
			return false;
		}
	}

	public boolean loadWounds(GildorymCharacter gChar) {
		if(gChar.getWoundsID() == -1)
			return true;
		
		try {
			PreparedStatement statement;
			
			statement = conn.prepareStatement(SELECT_WOUNDS_BY_ID);
			statement.setInt(1, gChar.getWoundsID());
			
			ResultSet resultSet = statement.executeQuery();
			
			Wound w;
			while(resultSet.next()) {
				w = new Wound(resultSet.getInt("wound_uid"));
				w.setWoundID(resultSet.getInt("wound_id"));
				w.setTimestamp(resultSet.getLong("timestamp"));
				w.setDamageType(DamageType.valueOf(resultSet.getString("damage_type")));
				w.setDamageAmount(resultSet.getInt("damage_amount"));
				w.setTimeRegen(resultSet.getLong("regen_time"));
				w.setNotes(resultSet.getString("notes"));
				
				gChar.addWound(w);
			}
			return true;
		}catch(SQLException e) {
			plugin.getLogger().log(Level.SEVERE, "Unable to load wounds on " + gChar.getName() + " [" + gChar.getUid() + "]");
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Deletes the wound from the character, if that was the last
	 * wound in the characters IN-MEMORY list of wounds, then the wounds
	 * on the character is reloaded from the database. If the list is
	 * still empty, the wound id is set to -1. Because this may take 3 
	 * db calls, its best to use clearWounds when sensible, rather than
	 * iteratively delete the mall
	 * 
	 * @param gChar The character
	 * @param w The wound
	 * @return whether the operation was successful or not
	 */
	public boolean deleteWound(GildorymCharacter gChar, Wound w) {
		try {
			PreparedStatement statement;
			
			statement = conn.prepareStatement(DELETE_WOUND);

			statement.setInt(1, w.getWoundUID());
			
			statement.execute();
			
			gChar.removeWound(w);
			
			if(gChar.getWounds().size() == 0) {
				loadWounds(gChar);
				
				if(gChar.getWounds().size() == 0) {
					gChar.setWoundsID(-1);
					saveCharacter(gChar);
				}
			}
			return true;
		}catch(SQLException e) {
			plugin.getLogger().log(Level.SEVERE, "Unable to prune wounds on " + gChar.getName() + " [" + gChar.getUid() + "]");
			e.printStackTrace();
			return false;
		}
	}

}
