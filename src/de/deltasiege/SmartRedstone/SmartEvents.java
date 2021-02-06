package de.deltasiege.SmartRedstone;
import java.util.Set;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import de.deltasiege.Models.SmartDevice;

public class SmartEvents implements Listener {
	public Set<Material> materials = Set.of(Material.LEVER);
	public SmartRedstone plugin;

	public SmartEvents(SmartRedstone plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getPlayer().isSneaking() && event.getAction() == Action.RIGHT_CLICK_BLOCK) {					    // sneak right click ?
			if (this.materials.contains(event.getClickedBlock().getType())) {										// block is SmartDevice 
				event.getPlayer().sendMessage("smart device found ... !?!?!?!?");
			}
		}
	}

	@EventHandler
	public void onEmitRedstoneEvent(BlockRedstoneEvent event) {
		if (this.materials.contains(event.getBlock().getType())) {													// check special blocks
			if (this.plugin.storage.isSmartDevice(event.getBlock().getLocation())) {								// check if device is SmartDevice
				int update = event.getNewCurrent();
				if (event.getOldCurrent() != update) {																// only update if current was changed
					SmartDevice device = this.plugin.storage.getSmartDevice(event.getBlock().getLocation());		// get SmartDevice
					device.state = update;																			// update current of the device
					this.plugin.remoteManager.push.sendPush(device);												// send bulk push notifications to all remote devices
				}
			}
		}
	}
}
