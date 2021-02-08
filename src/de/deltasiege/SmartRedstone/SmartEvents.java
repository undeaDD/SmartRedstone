package de.deltasiege.SmartRedstone;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;

public class SmartEvents implements Listener {
	HashMap<UUID, Location> tempStorage = new HashMap<UUID, Location>();
	public List<Material> materials = Arrays.asList(Material.LEVER, Material.STONE_BUTTON, Material.COMPARATOR);
	public Inventory addMenu;
	public Inventory removeMenu;
	
	public SmartRedstone plugin;

	public SmartEvents(SmartRedstone plugin) {
		this.plugin = plugin;
		this.addMenu = Utils.createAddMenu();
		this.removeMenu = Utils.createRemoveMenu();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getPlayer().isSneaking() && event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getHand() == EquipmentSlot.HAND) {
			if (this.materials.contains(event.getClickedBlock().getType())) {
				event.setCancelled(true);
				tempStorage.put(event.getPlayer().getUniqueId(), event.getClickedBlock().getLocation());
				Utils.playSound(event.getPlayer(), Sound.BLOCK_BARREL_OPEN);
				if (this.plugin.storage.deviceIsPaired(event.getPlayer().getUniqueId(), event.getClickedBlock().getLocation())) {
					event.getPlayer().openInventory(removeMenu);
				} else {
					event.getPlayer().openInventory(addMenu);
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
					if (this.plugin.storage.pairDevice(event.getWhoClicked(), this.tempStorage.get(event.getWhoClicked().getUniqueId()))) {
						event.getWhoClicked().sendMessage(Utils.prefix + " Device " + ChatColor.GREEN + "paired" + ChatColor.RESET + " successfully");
						event.getWhoClicked().closeInventory();
						Utils.playSound(event.getWhoClicked(), Sound.ENTITY_VILLAGER_YES);
						return;
					} else {
						event.getWhoClicked().sendMessage(Utils.prefix + " Device could not be paired");
					}		
				case closeItem:
					event.getWhoClicked().closeInventory();
					Utils.playSound(event.getWhoClicked(), Sound.BLOCK_BARREL_CLOSE);
				default:
					return;
			}
		} else if (event.getInventory().equals(removeMenu)) {
			event.setCancelled(true);
			switch (Utils.itemClicked(event.getCurrentItem())) {
				case removeItem:
					if (this.plugin.storage.unpairDevice(event.getWhoClicked(), this.tempStorage.get(event.getWhoClicked().getUniqueId()))) {
						event.getWhoClicked().sendMessage(Utils.prefix + " Device " + ChatColor.RED + "unpaired" + ChatColor.RESET + " successfully");
						event.getWhoClicked().closeInventory();
						Utils.playSound(event.getWhoClicked(), Sound.ENTITY_VILLAGER_NO);
						return;
					} else {
						event.getWhoClicked().sendMessage(Utils.prefix + " Device could not be unpaired");
					}
				case closeItem:
					event.getWhoClicked().closeInventory();
					Utils.playSound(event.getWhoClicked(), Sound.BLOCK_BARREL_CLOSE);
				default:
					return;
			}
		}
	}

	@EventHandler
	public void onEmitRedstoneEvent(BlockRedstoneEvent event) {
		if (this.materials.contains(event.getBlock().getType())) {												
			int update = event.getNewCurrent();					
			if (event.getOldCurrent() != update) {	
				if (this.plugin.storage.deviceStateUpdated(event.getBlock().getLocation(), update)) {
					if (event.getBlock().getType() == Material.STONE_BUTTON && event.getNewCurrent() == 0) {
						return; // trigger particles & sounds for button only once
					}
					Location tempLoc = event.getBlock().getLocation().add(0.5, 0.5, 0.5);
					event.getBlock().getWorld().playSound(tempLoc, Sound.ENTITY_ARROW_HIT_PLAYER, 0.2F, 1);
					event.getBlock().getWorld().spawnParticle(Particle.CLOUD, tempLoc, 5, 0.1D, 0.1D, 0.1D, 0.1D);
				}
			}
		}
	}
}
