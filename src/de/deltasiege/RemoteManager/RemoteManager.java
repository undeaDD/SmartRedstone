package de.deltasiege.RemoteManager;

import de.deltasiege.SmartRedstone.SmartRedstone;

public class RemoteManager {
	public SmartRedstone plugin;
	public WebServer server;
	public PushServer push;
	
	public RemoteManager(SmartRedstone plugin) {
		this.plugin = plugin;
		this.server = new WebServer(this);
		this.push = new PushServer(this);
	}

}
