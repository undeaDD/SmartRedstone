package de.deltasiege.Storages;

import com.pablo67340.SQLiteLib.Main.SQLiteLib;

import de.deltasiege.SmartRedstone.SmartRedstone;

public class SQLiteWrapper{
	private SQLiteLib sql;
	private SmartRedstone plugin;
	
	public SQLiteWrapper(SmartRedstone plugin) {
		this.plugin = plugin;
		sql.initializeDatabase(plugin, "SmartRedstoneDB", "CREATE TABLE IF NOT EXISTS smart_devices");
	}
}
