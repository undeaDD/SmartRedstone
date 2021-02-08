package de.deltasiege.Storages;
import org.bukkit.Location;
import org.bukkit.entity.HumanEntity;

import de.deltasiege.SmartRedstone.Utils;

public class TemporaryStorage implements StorageWrapper {

	public TemporaryStorage() {
		System.out.println(Utils.prefix + " Temporary Storage loaded");
	}

	@Override
	public boolean deviceIsPaired(HumanEntity player, Location loc) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pairDevice(HumanEntity player, Location loc) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean unpairDevice(HumanEntity player, Location loc) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deviceStateUpdated(Location loc, int newCurrent) {
		// TODO Auto-generated method stub
		return false;
	}

}
