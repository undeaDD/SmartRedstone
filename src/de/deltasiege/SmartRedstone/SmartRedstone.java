package de.deltasiege.SmartRedstone;
import org.bukkit.plugin.java.JavaPlugin;
import de.deltasiege.RemoteManager.RemoteManager;
import de.deltasiege.Storages.StorageWrapper;
import de.deltasiege.Storages.TemporaryStorage;
import de.deltasiege.bStats.Metrics;

public class SmartRedstone extends JavaPlugin {
	public StorageWrapper storage;
	public SmartEvents eventListener;
	public RemoteManager remoteManager;
	public SmartCommands commandHandler;
	
	public static int PLUGIN_ID = 10318;
	public Metrics metrics;
	
	@Override
	public void onEnable() {
		Utils.setUpNMS(this);
		storage = new TemporaryStorage(this);
		remoteManager = new RemoteManager(this);
		eventListener = new SmartEvents(this);
		commandHandler = new SmartCommands(this);
		metrics = new Metrics(this, PLUGIN_ID);
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		remoteManager.onDisable();
		eventListener.onDisable();
		super.onDisable();
	}
}
