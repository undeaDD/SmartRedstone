package de.deltasiege.Storages;
import java.util.List;
import java.util.Map;
import org.bukkit.Location;
import org.bukkit.entity.HumanEntity;

public interface StorageWrapper {
	/**
	 * Check if Player is paired with the SmartDevice at the Location
	 * 
	 * @param  UUID player   - Player UUID to check against
	 * @param  Location loc  - Location of the SmartDevice 
	 * 
	 * @return boolean       - is the device paired or not
	 */
	public boolean deviceIsPaired(HumanEntity player, Location loc);
	
	/**
	 * Player wants to pair the SmartDevice at the Location
	 * 
	 * @param  Player player - Player to check against
	 * @param  Location loc  - Location of the SmartDevice
	 * 
	 * @return boolean       - was the pairng successfull or not
	 */
	public boolean pairDevice(HumanEntity player, Location loc);
	
	/**
	 * Player wants to unpair the SmartDevice at the Location
	 * 
	 * @param  Player player - Player to check against
	 * @param  Location loc  - Location of the SmartDevice
	 * 
	 * @return boolean       - was the unpairng successfull or not
	 */
	public boolean unpairDevice(HumanEntity player, Location loc);
	
	/**
	 * Called when SmartDevice updates Redstone current ( 0 - 15 )
	 * Store updated Current and notify all affected Players
	 * 
	 * @param Location loc   - Location of the SmartDevice
	 * @param int newCurrent - new Redstone signal strength
	 * 
	 * @return boolean       - was anyone notified ? ( is anyone even affected )
	 */
	public boolean deviceStateUpdated(Location loc, int newCurrent);

	/**
	 * Player wants to rename one of his Devices
	 * 
	 * @param Player player  - Player to check against
	 * @param Location loc   - Location of the SmartDevice
	 * 
	 * @return boolean       - was the device renamed ?
	 */
	public boolean setDeviceTitle(HumanEntity player, Location loc, String newTitle);
	
	/**
	 * Player wants to list all his paired SmartDevices
	 * 
	 * @param  Player player - Player to check against
	 * 
	 * @return jsonarray     - JsonArray ( consisting of JsonObjects )
	 */
	public List<Map<String, Object>> getPairedDevices(HumanEntity player);
	
	/**
	 * App requested invalid ( broken / replaced ) Blocks. Remove them!
	 * 
	 * @param  Location[] invalidBlocks - Blocks that are no longer SmartDevices and need to be removed from the Storage
	 */
	public void removeInvalidBlocks(Location[] invalidBlocks);
	
	/**
	 * App sends push token to server
	 * 
	 * @param  Player player - Player to check against
	 * @param  String token  - new push token to store
	 * 
	 */
	public void updatePushToken(HumanEntity player, String newtoken);
}
