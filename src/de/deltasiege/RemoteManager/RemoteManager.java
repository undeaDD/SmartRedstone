package de.deltasiege.RemoteManager;
import de.deltasiege.MCNettyInjection.JSONAPIChannelDecoder;
import de.deltasiege.MCNettyInjection.NettyInjector;
import de.deltasiege.SmartRedstone.SmartRedstone;
import io.netty.channel.Channel;

public class RemoteManager {
	public SmartRedstone plugin;
	public NettyInjector injector;
	public JSONAPIChannelDecoder serverWrapper;
	public PushServer push;
	
	public RemoteManager(SmartRedstone plugin) {
		this.plugin = plugin;
		this.push = new PushServer(this);

		injector = new NettyInjector() {
	        @Override
	        protected void injectChannel(Channel channel) {
	            channel.pipeline().addFirst(new JSONAPIChannelDecoder(plugin.remoteManager));
	        }
	    };
	    injector.inject();
	}
	
	public void onDisable() {
		injector.close();
	}

}
