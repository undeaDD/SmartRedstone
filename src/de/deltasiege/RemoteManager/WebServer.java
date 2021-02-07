package de.deltasiege.RemoteManager;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

@SuppressWarnings("restriction")
public class WebServer {
	public RemoteManager remoteManager;
	public HttpServer server;
	
	public WebServer(RemoteManager remoteManager) {
		this.remoteManager = remoteManager;
		
		try {
			server = HttpServer.create(new InetSocketAddress("0.0.0.0", 1337), 0);
			server.createContext("/test", new TestHandler());
			server.setExecutor(null);
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("WebServer could not be created. Your current java jre version does not support HttpServers");
			remoteManager.plugin.getServer().getPluginManager().disablePlugin(remoteManager.plugin);
		}
	}

	static class TestHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange exchange) throws IOException {
			String response = "test this bitch";
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
		}
		
	}

}
