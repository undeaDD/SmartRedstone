package de.deltasiege.Storages;

import org.bukkit.Location;

import de.deltasiege.Models.SmartDevice;

public class FileStorage implements StorageWrapper {

	@Override
	public boolean isSmartDevice(Location loc) {
		System.out.println("not implemented yet");
		return false;
	}

	@Override
	public SmartDevice getSmartDevice(Location loc) {
		System.out.println("not implemented yet");
		return null;
	}

	@Override
	public boolean addSmartDevice(SmartDevice device) {
		System.out.println("not implemented yet");
		return false;
	}

	@Override
	public boolean removeSmartDevice(SmartDevice device) {
		System.out.println("not implemented yet");
		return false;
	}

}
