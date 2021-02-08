package de.deltasiege.RemoteManager;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import de.deltasiege.SmartRedstone.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;

@SuppressWarnings("restriction")
public class WebServer implements HttpHandler {
	public RemoteManager remoteManager;
	public HttpServer server;
	
	public WebServer(RemoteManager remoteManager) {
		this.remoteManager = remoteManager;
		
		try {
			server = HttpServer.create(new InetSocketAddress("0.0.0.0", 1337), 0);
			server.createContext("/api", this);
			server.setExecutor(null);
			server.start();
		} catch (IOException error) {
			Utils.log("API-Server could not be created. Plugin will now be disabled");
			remoteManager.plugin.getServer().getPluginManager().disablePlugin(remoteManager.plugin);
		}
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Map<String, String> params = toMap(exchange);
		if (!params.containsKey("action")) {
			exchange.sendResponseHeaders(400, 0);
			exchange.close();
			return;
		}
		
		switch (params.get("action")) {
			case "update":
				UUID uuid = UUID.fromString(params.get("uuid"));
				Location device = Utils.locationFromString(params.get("loc"));
				Integer current = Integer.parseInt(params.get("current"));
				if (this.remoteManager.plugin.storage.deviceIsPaired(uuid, device)) {
					switch (device.getBlock().getType()) {
					case LEVER:
						Bukkit.getScheduler().runTask(this.remoteManager.plugin, new Runnable() {
						    @Override
						    public void run() {
								try {
							    	Utils.updateBlock(device.getBlock(), current != 0);
							    } catch (Exception error) {
									Utils.log("This MC Version is not supported (NMSReflectionException). Plugin will now be disabled");
									remoteManager.plugin.getServer().getPluginManager().disablePlugin(remoteManager.plugin);
								}
						    }
						});
						exchange.sendResponseHeaders(200, 0);
						break;
					case STONE_BUTTON:
						Bukkit.getScheduler().runTask(this.remoteManager.plugin, new Runnable() {
						    @Override
						    public void run() {
						    	try {
							    	Utils.updateBlock(device.getBlock(), true);
							    } catch (Exception error) {
									Utils.log("This MC Version is not supported (NMSReflectionException). Plugin will now be disabled");
									remoteManager.plugin.getServer().getPluginManager().disablePlugin(remoteManager.plugin);
								}
						    }
						});
						Bukkit.getScheduler().runTaskLater(this.remoteManager.plugin, new Runnable() {
						    @Override
						    public void run() {
						    	try {
							    	Utils.updateBlock(device.getBlock(), false);
							    } catch (Exception error) {
									Utils.log("This MC Version is not supported (NMSReflectionException). Plugin will now be disabled");
									remoteManager.plugin.getServer().getPluginManager().disablePlugin(remoteManager.plugin);
								}
						    }
						}, 20L);
						
						exchange.sendResponseHeaders(200, 0);
						break;
					default:
						exchange.sendResponseHeaders(400, 0);
						break;
					}
				} else {
					exchange.sendResponseHeaders(400, 0);
				}
				break;
			case "unpair":
				
			case "list":
				
			default:
				exchange.sendResponseHeaders(400, 0);
				break;
		}
        exchange.close();
	}

	private static Map<String, String> toMap(HttpExchange exchange) {
		String query = exchange.getRequestURI().getQuery();
	    if (query == null || query.isEmpty()) { 
	    	return Collections.emptyMap(); 
	    } else {
	    	return Stream.of(query.split("&")).filter(s -> !s.isEmpty()).map(kv -> kv.split("=", 2)) .collect(Collectors.toMap(x -> x[0], x-> x[1]));
	    }
	}
}