package de.deltasiege.Storages;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.entity.HumanEntity;
import com.pablo67340.SQLiteLib.Main.SQLiteLib;
import de.deltasiege.SmartRedstone.SmartRedstone;
import de.deltasiege.SmartRedstone.Utils;

public class LocalSQLStorage implements StorageWrapper {
	public SmartRedstone plugin;
	public SQLiteLib sql;

	public LocalSQLStorage(SmartRedstone plugin) {
		this.plugin = plugin;
		this.sql.initializeDatabase(plugin, "database", "CREATE TABLE IF NOT EXISTS smart_devices");
		Utils.log("Local SQL Storage loaded");
	}

	@Override
	public boolean deviceIsPaired(UUID player, Location loc) {
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
