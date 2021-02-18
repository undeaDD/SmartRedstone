package de.deltasiege.Storages;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.bukkit.Location;
import org.bukkit.entity.HumanEntity;
import de.deltasiege.SmartRedstone.SmartRedstone;
import de.deltasiege.SmartRedstone.Utils;

public class TemporaryStorage implements StorageWrapper {
	private HashMap<String, ArrayList<UUID>> deviceData = new HashMap<String, ArrayList<UUID>>(); // loc -> uuid[]
	private HashMap<String, Integer> deviceStates = new HashMap<String, Integer>(); 			  // loc -> current
	private HashMap<UUID, String> playerData = new HashMap<UUID, String>();						  // uuid -> expo pushkey
	private HashMap<String, String> deviceNames = new HashMap<String, String>();				  // loc,UUID -> deviceName
	public SmartRedstone plugin; 
	
	public TemporaryStorage(SmartRedstone plugin) {
		this.plugin = plugin;
		Utils.log("using: Temporary Storage");
	}
	
	private String getWorldKey(Location loc) {
		return Utils.locationToString(loc);
	}

	@Override
	public boolean deviceIsPaired(HumanEntity player, Location loc) {
		String key = getWorldKey(loc);
		if (deviceData.containsKey(key)) {
			return deviceData.get(key).contains(player.getUniqueId());
		} else {
			return false;
		}
	}

	@Override
	public boolean pairDevice(HumanEntity player, Location loc) {
		if (!playerData.containsKey(player.getUniqueId())) {
			playerData.put(player.getUniqueId(), "");
			Utils.sendRegisterReminder(player, false);
		}
		
		String key = getWorldKey(loc);
		
		ArrayList<UUID> old = deviceData.get(key);
		if (old == null) { old = new ArrayList<UUID>(); }
		
		if (old.contains(player.getUniqueId())) {
			return false;
		} else {
			old.add(player.getUniqueId());
			deviceData.put(key, old);
			if (deviceStates.get(key) == null) {
				int current = Utils.locToCurrent(loc);
				deviceStates.put(key, current);
			}
			return true;
		}
	}

	@Override
	public boolean unpairDevice(HumanEntity player, Location loc) {
		if (!playerData.containsKey(player.getUniqueId())) {
			playerData.put(player.getUniqueId(), "");
			Utils.sendRegisterReminder(player, false);
		}
		
		String key = getWorldKey(loc);
		ArrayList<UUID> old = deviceData.get(key);
		if (old == null) { old = new ArrayList<UUID>(); }
		
		if (old.contains(player.getUniqueId())) {
			old.remove(player.getUniqueId());
			
			if (old.isEmpty()) {
				deviceData.remove(key);
				deviceStates.remove(key);
			} else {
				deviceData.put(key, old);
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean deviceStateUpdated(Location loc, int newCurrent) {
		String key = getWorldKey(loc);
		List<UUID> players = deviceData.get(key);
		
		if (players == null) {
			return false;
		} else {
			System.out.println("lul");
			deviceStates.put(key, newCurrent);
			List<String> temp = players.stream().map(uuid -> playerData.get(uuid)).filter(push -> !push.isEmpty()).collect(Collectors.toList());
			System.out.println(temp.size());
			plugin.remoteManager.push.sendPush(temp, loc, newCurrent);
			return true;
		}
	}

	@Override
	public List<Map<String, Object>> getPairedDevices(HumanEntity player) {
		ArrayList<Location> toRemove = new ArrayList<Location>();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		UUID tempId = player.getUniqueId();

		for (Map.Entry<String, ArrayList<UUID>> entry : deviceData.entrySet()) {
			Location loc = Utils.locationFromString(entry.getKey());
			if (!Utils.materials.contains(loc.getBlock().getType())) {
				toRemove.add(loc);
				break;
			}
			for (UUID id : entry.getValue()) {
				if (id.equals(tempId)) {
					Map<String, Object> obj = new HashMap<String, Object>();
					
					obj.put("loc", entry.getKey());
					obj.put("type", loc.getBlock().getType().toString());
					obj.put("current", deviceStates.get(entry.getKey()));
					
					String title = deviceNames.get(entry.getKey() + "," + tempId.toString());
					if (title != null && !title.isEmpty()) {
						obj.put("title", title);
					} else {
						obj.put("title", loc.getBlock().getType().toString());
					}
					obj.put("count", entry.getValue().size());
					result.add(obj);
					break;
				}
			}
		}
		
		if (!toRemove.isEmpty()) {
			Location[] locs = new Location[toRemove.size()];
			locs = toRemove.toArray(locs);
			this.plugin.storage.removeInvalidBlocks(locs);
		}
		
		return result;
	}

	@Override
	public void removeInvalidBlocks(Location[] invalidBlocks) {
		for (Location loc : invalidBlocks) {
			String key = Utils.locationToString(loc);
			deviceData.remove(key);
			deviceStates.remove(key);
		}
	}

	@Override
	public boolean setDeviceTitle(HumanEntity player, Location loc, String newTitle) {		
		String key = getWorldKey(loc);
		if (deviceIsPaired(player, loc)) {
			deviceNames.put(key + "," + player.getUniqueId().toString(), newTitle);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void updatePushToken(HumanEntity player, String newtoken) {
		playerData.put(player.getUniqueId(), newtoken);
	}

}
