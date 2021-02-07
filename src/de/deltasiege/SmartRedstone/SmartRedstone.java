package de.deltasiege.SmartRedstone;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import de.deltasiege.RemoteManager.RemoteManager;
import de.deltasiege.Storages.StorageWrapper;
import de.deltasiege.Storages.TemporaryStorage;

public class SmartRedstone extends JavaPlugin {
	public StorageWrapper storage;
	public RemoteManager remoteManager;
	public SmartEvents eventListener;
	
	@Override
	public void onEnable() {
		super.onEnable();

		storage = new TemporaryStorage();
		remoteManager = new RemoteManager(this);
		eventListener = new SmartEvents(this);
	}
	
	@SuppressWarnings("restriction")
	@Override
	public void onDisable() {
		super.onDisable();
		HandlerList.unregisterAll(eventListener);
		remoteManager.server.server.stop(1);
		for (Player p : getServer().getOnlinePlayers()) {
			p.closeInventory();
		}
	}
}
