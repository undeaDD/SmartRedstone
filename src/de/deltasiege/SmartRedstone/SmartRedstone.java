package de.deltasiege.SmartRedstone;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import de.deltasiege.RemoteManager.RemoteManager;
import de.deltasiege.Storages.StorageWrapper;
import de.deltasiege.Storages.TemporaryStorage;

public class SmartRedstone extends JavaPlugin {
	public StorageWrapper storage;
	public RemoteManager remoteManager;
	public SmartEvents eventListener;
	public SmartCommands commandHandler;
	
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
		super.onEnable();
	}
	
	@SuppressWarnings("restriction")
	@Override
	public void onDisable() {
		HandlerList.unregisterAll(eventListener);
		remoteManager.server.server.stop(1);

		for (Player p : getServer().getOnlinePlayers()) {
			Inventory temp = p.getOpenInventory().getTopInventory();
			if (temp != null && (temp.equals(eventListener.addMenu) || temp.equals(eventListener.removeMenu))) {
				p.closeInventory();
			}
		}

		super.onDisable();
	}
}
