package de.deltasiege.SmartRedstone;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.zip.GZIPOutputStream;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Switch;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class Utils {
	public static List<Material> materials = Arrays.asList(Material.LEVER, Material.STONE_BUTTON, Material.COMPARATOR);
	
	// Log Utils
	
	public static String prefix = ChatColor.RESET + "[" + ChatColor.RED + "SmartRedstone" + ChatColor.RESET + "]";
	
	public static void log(String msg) {
		System.out.println(ChatColor.stripColor(prefix + " " + msg.trim()));
	}
	
	public static int parseInt(String possibleInteger) {
		try { return Integer.parseInt(possibleInteger); } catch (Exception error) { return 0; }
	}
	
	// NMS Block updating Utils
	
	public static void updateBlock(Block device, boolean state) throws Exception {
		BlockData blockData = device.getState().getBlockData();
		Switch switchData = (Switch) blockData;
		BlockFace bf = ((Directional) blockData).getFacing();
		Block behind = device.getRelative(bf.getOppositeFace());
		switchData.setPowered(state);
		device.setBlockData(switchData, true);
		device.getState().update(true, true);
		behind.getState().update(true, true);

		applyPhysics.invoke(getHandle.invoke(CraftWorld.cast(device.getWorld()), new Object[]{}), BlockPosition.getConstructor(double.class, double.class, double.class).newInstance(device.getX(), device.getY(), device.getZ()), getNMSBlock.invoke(CraftBlock.cast(device), new Object[]{}));
		applyPhysics.invoke(getHandle.invoke(CraftWorld.cast(behind.getWorld()), new Object[]{}), BlockPosition.getConstructor(double.class, double.class, double.class).newInstance(behind.getX(), behind.getY(), behind.getZ()), getNMSBlock.invoke(CraftBlock.cast(behind), new Object[]{}));
	}
	
	private static Class<?> BlockPosition, CraftBlock, CraftWorld, WorldServer;
	private static Method getNMSBlock, getHandle, applyPhysics;
	
	public static void setUpNMS(SmartRedstone plugin) {
		try { 
			BlockPosition = getNMSClass("net.minecraft.server.%s.BlockPosition");
			CraftBlock = getNMSClass("org.bukkit.craftbukkit.%s.block.CraftBlock");
			CraftWorld = getNMSClass("org.bukkit.craftbukkit.%s.CraftWorld");
			WorldServer = getNMSClass("net.minecraft.server.%s.WorldServer");
			getNMSBlock = CraftBlock.getDeclaredMethod("getNMSBlock", new Class<?>[]{});
			getNMSBlock.setAccessible(true);
			getHandle = CraftWorld.getDeclaredMethod("getHandle", new Class<?>[]{});
			applyPhysics = WorldServer.getMethod("applyPhysics", BlockPosition, getNMSClass("net.minecraft.server.%s.Block"));
		} catch (Exception error) {
			Utils.log("This MC Version is not supported (NMSReflectionException). Plugin will now be disabled");
			plugin.getServer().getPluginManager().disablePlugin(plugin);
		}
	}
	
	private static Class<?> getNMSClass(String name) throws ClassNotFoundException {
		return Class.forName(String.format(name, Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3]));
	}
	
	// Location Utils
	
	public static Location locationFromString(String input) {
		String[] temp = input.split(",");
		return new Location(Bukkit.getServer().getWorld(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2]), Integer.parseInt(temp[3]));
	}
	
	public static String locationToString(Location location) {
		return location.getWorld().getName() + "," + location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ();
	}
	
	// Tellraw Utils
	
	public static void sendRegisterReminder(HumanEntity player, boolean isFeedbackLink) {
		TextComponent a = new TextComponent("[");
		a.setColor(net.md_5.bungee.api.ChatColor.RESET);
		
		TextComponent b = new TextComponent("SmartRedstone");
		b.setColor(net.md_5.bungee.api.ChatColor.RED);
		a.addExtra(b);
		
		TextComponent c = new TextComponent("] ");
		c.setColor(net.md_5.bungee.api.ChatColor.RESET);
		a.addExtra(c);
		
		TextComponent d = new TextComponent(isFeedbackLink ? "Click here to send feedback." : "Click here to open the tutorial.");
		d.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, isFeedbackLink ? "https://www.google.de" : "https://www.google.de"));
		d.setColor(net.md_5.bungee.api.ChatColor.RESET);
		d.setUnderlined(true);
		a.addExtra(d);

		try {
			player.spigot().sendMessage(a);
		} catch (Exception error) { /* Catching spigot call from AppUser :3 */ }
	}
	
	// Play Sound Utils
	
	public static void playSound(HumanEntity human, Sound sound) {
		if (human instanceof Player) {
			Player player = (Player) human;
			player.playSound(player.getLocation(), sound, 1, 1);
		}
	}
	
	// ItemStack Utils
	
	public static ItemStack nametag = createItemStack(ItemResult.renameItem);
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
		case renameItem:
			item = new ItemStack(Material.NAME_TAG);
			imeta = item.getItemMeta();
			imeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GREEN + "Rename Device");
			item.setItemMeta(imeta);
			break;
		case infoItem:
			item = new ItemStack(Material.PAPER);
			imeta = item.getItemMeta();
			imeta.setDisplayName(prefix);
			imeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.WHITE + "- Control Devices via App", ChatColor.RESET + "" + ChatColor.WHITE + "- Get notified via App", ChatColor.RESET + "" + ChatColor.WHITE + "- MultiServer support", "Click to show tutorial ..."));
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
		Inventory menu = Bukkit.createInventory(null, InventoryType.HOPPER, prefix);
		menu.setItem(0, paper);
		menu.setItem(2, plus);
		menu.setItem(4, barrier);
		return menu;
	}

	public static Inventory createRemoveMenu() {
		Inventory menu = Bukkit.createInventory(null, InventoryType.HOPPER, prefix);
		menu.setItem(0, nametag);
		menu.setItem(2, minus);
		menu.setItem(4, barrier);
		return menu;
	}
	
	public static ItemResult itemClicked(ItemStack current) {
		if (current == null) {
			return null;
		} else if (current.equals(nametag)) {
			return ItemResult.renameItem;
		} else if (current.equals(plus)) {
			return ItemResult.addItem;
		} else if (current.equals(minus)) {
			return ItemResult.removeItem;
		} else if (current.equals(barrier)) {
			return ItemResult.closeItem;
		} else if (current.equals(paper)) {
			return ItemResult.infoItem;
		} else {
			return null;
		}
	}

	public static enum ItemResult {
		addItem, removeItem, closeItem, infoItem, renameItem
	}

	public static int locToCurrent(Location loc) {
		switch (loc.getBlock().getType()) {
		case LEVER:
		case STONE_BUTTON:
			Switch block = (Switch) loc.getBlock().getBlockData();
			return block.isPowered() ? 15 : 0;
		case COMPARATOR:
			return loc.getBlock().getBlockPower();
		default:
			return 0;
		}
	}
	
	public static String compressString(String srcTxt) throws Exception {
		ByteArrayOutputStream rstBao = new ByteArrayOutputStream();
		GZIPOutputStream zos = new GZIPOutputStream(rstBao);
		zos.write(srcTxt.getBytes());
		zos.flush(); zos.close();
		byte[] bytes = rstBao.toByteArray();
		rstBao.flush(); rstBao.close();
		return new String(Base64.getEncoder().encode(bytes));
	}
}
