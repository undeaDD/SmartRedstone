package de.deltasiege.Models;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public class Utils {

	public static ItemStack paper = createItemStack(ItemResult.infoItem);
	public static ItemStack plus = createItemStack(ItemResult.addItem);
	public static ItemStack minus = createItemStack(ItemResult.removeItem);
	public static ItemStack barrier = createItemStack(ItemResult.closeItem);
	
	
	private static ItemStack createItemStack(ItemResult result) {
		ItemStack item = null;
		SkullMeta smeta;
		ItemMeta imeta;
		GameProfile profile;
		Method method;

		switch (result) {
		case infoItem:
			item = new ItemStack(Material.PAPER);
			imeta = item.getItemMeta();
			imeta.setDisplayName(ChatColor.RESET + "" + ChatColor.WHITE + "Informations");
			imeta.setLore(Arrays.asList("work in progress", "...", "..."));
			item.setItemMeta(imeta);
			break;
		case addItem:
			item = new ItemStack(Material.PLAYER_HEAD);
			smeta = (SkullMeta) item.getItemMeta();
			smeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GREEN + "Add to App");
	        try {
	        	profile = new GameProfile(UUID.randomUUID(), null);
		        profile.getProperties().put("textures", new Property("textures", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWZmMzE0MzFkNjQ1ODdmZjZlZjk4YzA2NzU4MTA2ODFmOGMxM2JmOTZmNTFkOWNiMDdlZDc4NTJiMmZmZDEifX19"));
	            method = smeta.getClass().getDeclaredMethod("setProfile", GameProfile.class);
	            method.setAccessible(true);
	            method.invoke(smeta, profile);
	        } catch (Exception error) {
	            error.printStackTrace();
	        }
	       
	        item.setItemMeta(smeta);
			break;
		case removeItem:
			item = new ItemStack(Material.PLAYER_HEAD);
			smeta = (SkullMeta) item.getItemMeta();
			smeta.setDisplayName(ChatColor.RESET + "" + ChatColor.RED + "Remove from App");
	        try {
	        	profile = new GameProfile(UUID.randomUUID(), null);
		        profile.getProperties().put("textures", new Property("textures", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGU0YjhiOGQyMzYyYzg2NGUwNjIzMDE0ODdkOTRkMzI3MmE2YjU3MGFmYmY4MGMyYzViMTQ4Yzk1NDU3OWQ0NiJ9fX0="));
	            method = smeta.getClass().getDeclaredMethod("setProfile", GameProfile.class);
	            method.setAccessible(true);
	            method.invoke(smeta, profile);
	        } catch (Exception error) {
	            error.printStackTrace();
	        }
	       
	        item.setItemMeta(smeta);
			break;
		case closeItem:
			item = new ItemStack(Material.BARRIER);
			imeta = item.getItemMeta();
			imeta.setDisplayName(ChatColor.RESET + "" + ChatColor.WHITE + "Close Menu");
			item.setItemMeta(imeta);
			break;
		}

		return item;
	}
	
	public static Inventory createAddMenu() {
		Inventory menu = Bukkit.createInventory(null, InventoryType.HOPPER, "[" + ChatColor.RED + "SmartDevice" + ChatColor.RESET + "]");
		menu.setItem(0, paper);
		menu.setItem(2, plus);
		menu.setItem(4, barrier);
		return menu;
	}
	
	

	public static Inventory createRemoveMenu() {
		Inventory menu = Bukkit.createInventory(null, InventoryType.HOPPER, "[" + ChatColor.RED + "SmartDevice" + ChatColor.RESET + "]");
		menu.setItem(0, paper);
		menu.setItem(2, minus);
		menu.setItem(4, barrier);
		return menu;
	}
	
	public static ItemResult itemClicked(ItemStack current) {
		if (current.equals(plus)) {
			return ItemResult.addItem;
		} else if (current.equals(minus)) {
			return ItemResult.removeItem;
		} else if (current.equals(barrier)) {
			return ItemResult.closeItem;
		} else {
			return ItemResult.infoItem;
		}
	}

	
	public static enum ItemResult {
		addItem, removeItem, closeItem, infoItem
	}
}
