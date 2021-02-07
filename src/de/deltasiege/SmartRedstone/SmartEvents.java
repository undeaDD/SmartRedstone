package de.deltasiege.SmartRedstone;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;

import de.deltasiege.Models.Utils;

public class SmartEvents implements Listener {
	public List<Material> materials = Arrays.asList(Material.LEVER, Material.STONE_BUTTON, Material.TARGET);
	public Inventory addMenu;
	public Inventory removeMenu;
	public SmartRedstone plugin;

	public SmartEvents(SmartRedstone plugin) {
		this.plugin = plugin;
		addMenu = Utils.createAddMenu();
		removeMenu = Utils.createRemoveMenu();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getPlayer().isSneaking() && event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getHand() == EquipmentSlot.HAND) {
			if (this.materials.contains(event.getClickedBlock().getType())) {
				event.setCancelled(true);
				// show inventory depending on the smart device not on random value XD
				if (Math.random() < 0.5) {
					event.getPlayer().openInventory(addMenu);
				} else {
					event.getPlayer().openInventory(removeMenu);
				}
			}
		}
	}
	
	@EventHandler
	public void onSmartDeviceMenuInteraction(InventoryClickEvent event) {
		if (event.getInventory().equals(addMenu)) {
			event.setCancelled(true);
			switch (Utils.itemClicked(event.getCurrentItem())) {
				case addItem:
					// add smartdevice 
					event.getWhoClicked().sendMessage("Smart device added");
				case closeItem:
					event.getWhoClicked().closeInventory();
				default:
					return;
			}
		} else if (event.getInventory().equals(removeMenu)) {
			event.setCancelled(true);
			switch (Utils.itemClicked(event.getCurrentItem())) {
				case removeItem:
					// remove smart device
					event.getWhoClicked().sendMessage("Smart device removed");
				case closeItem:
					event.getWhoClicked().closeInventory();
				default:
					return;
			}
		}
	}

	
	
	@EventHandler
	public void onEmitRedstoneEvent(BlockRedstoneEvent event) {
		/*if (this.materials.contains(event.getBlock().getType())) {												// check special blocks
			if (this.plugin.storage.isSmartDevice(event.getBlock().getLocation())) {								// check if device is SmartDevice
				int update = event.getNewCurrent();
				if (event.getOldCurrent() != update) {																// only update if current was changed
					SmartDevice device = this.plugin.storage.getSmartDevice(event.getBlock().getLocation());		// get SmartDevice
					device.state = update;																			// update current of the device
					this.plugin.storage.getAffectedUsers(device);
					this.plugin.remoteManager.push.sendPush(device);												// send bulk push notifications to all remote devices
				}
			}
		}*/
	}
}
