package de.deltasiege.Storages;
import org.bukkit.Location;
import de.deltasiege.Models.SmartDevice;

public interface StorageWrapper {
	public boolean isSmartDevice(Location loc);
	public SmartDevice getSmartDevice(Location loc);

	public boolean addSmartDevice(SmartDevice device);
	public boolean removeSmartDevice(SmartDevice device);
}
