package de.deltasiege.Storages;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.entity.HumanEntity;
import de.deltasiege.SmartRedstone.SmartRedstone;
import de.deltasiege.SmartRedstone.Utils;

public class TemporaryStorage implements StorageWrapper {
	HashMap<String, ArrayList<UUID>> deviceData = new HashMap<String, ArrayList<UUID>>();
	HashMap<String, Integer> deviceStates = new HashMap<String, Integer>();
	HashMap<UUID, String> playerData = new HashMap<UUID, String>();
	
	public TemporaryStorage(SmartRedstone plugin) {
		Utils.log("Temporary Storage loaded");
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
			Utils.sendRegisterReminder(player);
		}
		
		String key = getWorldKey(loc);
		ArrayList<UUID> old = deviceData.get(key);
		if (old == null) { old = new ArrayList<UUID>(); }
		
		if (old.contains(player.getUniqueId())) {
			return false;
		} else {
			old.add(player.getUniqueId());
			deviceData.put(key, old);
			return true;
		}
	}

	@Override
	public boolean unpairDevice(HumanEntity player, Location loc) {
		if (!playerData.containsKey(player.getUniqueId())) {
			playerData.put(player.getUniqueId(), "");
			Utils.sendRegisterReminder(player);
		}
		
		String key = getWorldKey(loc);
		ArrayList<UUID> old = deviceData.get(key);
		if (old == null) { old = new ArrayList<UUID>(); }
		
		if (old.contains(player.getUniqueId())) {
			old.remove(player.getUniqueId());
			
			if (old.isEmpty()) {
				deviceData.remove(key);
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
			// send push to players devices
			Utils.log("sending Push to: " + players.toString());
			deviceStates.put(key, newCurrent);
			return true;
		}
	}

	@Override
	public List<Map<String, Object>> getPairedDevices(HumanEntity player) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		UUID tempId = player.getUniqueId();
		for (Map.Entry<String, ArrayList<UUID>> entry : deviceData.entrySet()) {
			for (UUID id : entry.getValue()) {
				if (id.equals(tempId)) {
					Map<String, Object> obj = new HashMap<String, Object>();
					Location loc = Utils.locationFromString(entry.getKey());
					obj.put("loc", entry.getKey());
					obj.put("type", loc.getBlock().getType().toString());
					obj.put("current", deviceStates.get(entry.getKey()));
					obj.put("count", entry.getValue().size());
					result.add(obj);
					break;
				}
			}
		}
		return result;
	}

}
