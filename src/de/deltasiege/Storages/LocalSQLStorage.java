package de.deltasiege.Storages;
import java.util.List;
import java.util.Map;
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
		this.sql.initializeDatabase(plugin, "database", "CREATE TABLE IF NOT EXISTS `smart_devices` ("
				+ "  `device_id` INT NOT NULL AUTO_INCREMENT,"
				+ "  `type` VARCHAR(10) NULL,"
				+ "  `state` CHAR(1) NULL,"
				+ "  PRIMARY KEY (`device_id`));"
				+ "CREATE TABLE IF NOT EXISTS `users` ("
				+ "  `uuid` VARCHAR(36) NOT NULL,"
				+ "  `device_id` INT NOT NULL,"
				+ "  `is_owner` CHAR(1) NULL,"
				+ "  PRIMARY KEY (`uuid`, `device_id`));"
				);

		Utils.log("Local SQL Storage loaded");
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

	@Override
	public List<Map<String, Object>> getPairedDevices(HumanEntity player) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeInvalidBlocks(Location[] invalidBlocks) {
		// TODO Auto-generated method stub
		return;
	}

	@Override
	public boolean setDeviceTitle(HumanEntity player, Location loc, String newTitle) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void updatePushToken(HumanEntity player, String newtoken) {
		// TODO Auto-generated method stub
		return;
	}
}
