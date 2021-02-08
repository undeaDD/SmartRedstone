package de.deltasiege.Storages;
import java.util.UUID;
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
	public boolean deviceIsPaired(UUID player, Location loc);
	
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
}
