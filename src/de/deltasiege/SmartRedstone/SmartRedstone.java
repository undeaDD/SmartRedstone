package de.deltasiege.SmartRedstone;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
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
	
	@Override
	public void onDisable() {
		super.onDisable();
	}
}
