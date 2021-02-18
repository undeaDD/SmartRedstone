package de.deltasiege.RemoteManager;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Location;

import de.deltasiege.SmartRedstone.Utils;

public class PushServer implements Runnable {
	public RemoteManager remoteManager;
	private HashMap<String, String> sink = new HashMap<String, String>();
	
	public PushServer(RemoteManager remoteManager) {
		this.remoteManager = remoteManager;
		this.remoteManager.plugin.getServer().getScheduler().runTaskTimer(this.remoteManager.plugin, this, 20, 18000);
	}

	public void sendPush(List<String> pushTokens, Location loc, int current) {
		String data = Utils.locationToString(loc) + "," + current;
		for (String token : pushTokens) {
			sink.put(token, data);
		}
	}

	@Override
	public void run() {
		if (sink.isEmpty()) { return; }
		Utils.log("sending push messages to: " + sink.size() + " devices");

		// sink max size 100 <- expo server limit
		
		String requestBody = "[";
		Iterator<Entry<String, String>> iterator = sink.entrySet().iterator();
		while(iterator.hasNext()) {
			Entry<String, String> item = iterator.next();
			requestBody += "{\n \"to\": \"" + item.getKey() + "\",\n \"title\":\"[SmartRedstone] Device updated\",\n \"body\": \"Check the App for Details\",\n \"priority\": \"high\",\n \"sound\": \"default\",\n \"badge\": 0,\n \"data\": {\"loc\": \"" + item.getValue() + "\"}\n}";
			if (iterator.hasNext()) { requestBody += ",\n"; } else { requestBody += "]"; }
		}
		
		sink = new HashMap<String, String>();
		
		try {
			URL url = new URL("https://exp.host/--/api/v2/push/send");
			HttpURLConnection http = (HttpURLConnection)url.openConnection();
			http.setRequestMethod("POST");
			http.setDoOutput(true);
			http.setRequestProperty("host", "exp.host");
			http.setRequestProperty("accept", "application/json");
			http.setRequestProperty("accept-encoding", "gzip, deflate");
			http.setRequestProperty("Content-Type", "application/json");
			OutputStream stream = http.getOutputStream();
			stream.write(requestBody.getBytes(StandardCharsets.UTF_8));
			http.disconnect();
			return;
		} catch(Exception error) {
			error.printStackTrace();
		}
	}
	
}
