package de.deltasiege.Storages;
import java.util.HashMap;
import org.bukkit.Location;
import de.deltasiege.Models.SmartDevice;

public class TemporaryStorage implements StorageWrapper {
	private HashMap<String, SmartDevice> data = new HashMap<String, SmartDevice>();
	
	private String getHashKey(Location loc) {
		return loc.getWorld().getName() + "|" + loc.getBlockX() + "|" + loc.getBlockY() + "|" + loc.getBlockZ();
	}
	
	@Override
	public boolean isSmartDevice(Location loc) {
		String key = getHashKey(loc);
		return data.get(key) != null;
	}

	@Override
	public SmartDevice getSmartDevice(Location loc) {
		String key = getHashKey(loc);
		return data.get(key);
	}

	@Override
	public boolean addSmartDevice(SmartDevice device) {
		if (data.get(device.key) == null) {
			data.put(device.key, device);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean removeSmartDevice(SmartDevice device) {
		if (data.get(device.key) != null) {
			data.remove(device.key);
			return true;
		} else {
			return false;
		}
	}
}
