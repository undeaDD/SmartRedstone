package de.deltasiege.RemoteManager;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import de.deltasiege.Models.SmartDevice;

public class PushServer {
	public RemoteManager remoteManager;
	
	public PushServer(RemoteManager remoteManager) {
		this.remoteManager = remoteManager;
	}

	public boolean sendPush(SmartDevice device) {
		try {
			URL url = new URL("https://exp.host/--/api/v2/push/send");
			HttpURLConnection http = (HttpURLConnection)url.openConnection();
			
			http.setRequestMethod("POST");
			http.setDoOutput(true);
			http.setRequestProperty("Content-Type", "application/json");
	
			String data = "{\n  \"to\": \"ExponentPushToken[xxxxxxxxxxxxxxxxxxxxxx]\",\n  \"title\":\"hello\",\n  \"body\": \"world\"\n}";
			byte[] out = data.getBytes(StandardCharsets.UTF_8);
	
			OutputStream stream = http.getOutputStream();
			stream.write(out);
	
			int statusCode = http.getResponseCode();
			http.disconnect();

			return statusCode == 200;
		} catch(Exception error) {
			return false;
		}
	}
	
}
