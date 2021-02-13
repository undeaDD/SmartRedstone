package de.deltasiege.SmartRedstone;
import org.bukkit.plugin.java.JavaPlugin;
import de.deltasiege.RemoteManager.RemoteManager;
import de.deltasiege.Storages.StorageWrapper;
import de.deltasiege.Storages.TemporaryStorage;
import de.deltasiege.bStats.Metrics;

public class SmartRedstone extends JavaPlugin {
	public StorageWrapper storage;
	public RemoteManager remoteManager;
	public SmartEvents eventListener;
	public SmartCommands commandHandler;
	public Metrics metrics;
   
	@Override
	public void onEnable() {
		try { Utils.setUpNMS(); } catch (Exception error) {
			Utils.log("This MC Version is not supported (NMSReflectionException). Plugin will now be disabled");
			getServer().getPluginManager().disablePlugin(this);
		}

		storage = new TemporaryStorage(this);
		remoteManager = new RemoteManager(this);
		eventListener = new SmartEvents(this);
		commandHandler = new SmartCommands(this);
		metrics = new Metrics(this, 10318);
		super.onEnable();
	}
	
	@SuppressWarnings("restriction")
	@Override
	public void onDisable() {
		remoteManager.onDisable();
		eventListener.onDisable();
		super.onDisable();
	}
}
