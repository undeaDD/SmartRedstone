package de.deltasiege.SmartRedstone;
import java.util.HashMap;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.conversations.MessagePrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class SmartCommands implements CommandExecutor {
	public SmartRedstone plugin;
	
	public SmartCommands(SmartRedstone plugin) {
		this.plugin = plugin;
		plugin.getCommand("sr").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] parameters) {
		if (sender instanceof Player) {
			
			String action = parameters.length > 0 ? parameters[0] : "help";
			
			switch (action) {
			case "tutorial":
				cmdTutorial((Player) sender);
				break;
			case "version":
				cmdVersion((Player) sender);
				break;
			case "feedback":
				cmdFeedback((Player) sender);
				break;
			case "rename":
				if (parameters.length == 2) {
					cmdRename((Player) sender, parameters[1]);
					break;
				}
			case "help":
			default:
				cmdHelp((Player) sender);
				break;
			}
			
			return true;
		} else {
			Utils.log("This command is ingame only");
			return true;
		}
	}
	
	private void cmdHelp(Player player) {
		player.sendMessage(Utils.prefix + " Commands:\n - /sr help -> list of all commands\n - /sr tutorial -> link for a guide and FAQ\n - /sr version -> displays the plugin version\n - /sr feedback -> link for the feedback page");
	}
	
	private void cmdTutorial(Player player) {
		Utils.sendRegisterReminder(player, false);
	}
	
	private void cmdFeedback(Player player) {
		Utils.sendRegisterReminder(player, true);
	}
	
	private void cmdVersion(Player player) {
		player.sendMessage(Utils.prefix + " Version: " + plugin.getDescription().getVersion());
	}
	
	private void cmdRename(Player player, String locKey) {
		ConversationFactory factory = new ConversationFactory(plugin); 
		factory.withFirstPrompt(getNewTitle);
		factory.withLocalEcho(true);
		factory.withModality(true);
		factory.withTimeout(120);
		factory.withInitialSessionData( new HashMap<Object, Object>() {
			static final long serialVersionUID = 5137559615580814387L;
			{
		        put("loc", locKey);
		    }
		});
		factory.buildConversation(player).begin();
	}
	
	Prompt getNewTitle = new StringPrompt() {
	    @Override
	    public String getPromptText(ConversationContext context) {
	        return "------------------\n" + Utils.prefix + " Input new Device name:";
	    }

	    @Override
	    public Prompt acceptInput(ConversationContext context, String input) {
	    	if (input.isEmpty()) {
	    		context.setSessionData("success", false);
	    	} else {
	    		Location loc = Utils.locationFromString((String) context.getSessionData("loc"));
	    		boolean result = plugin.storage.setDeviceTitle((Player) context.getForWhom(), loc, input);
	    		context.setSessionData("success", result);
	    	}
	        return response;
	    }
	};
	
	Prompt response = new MessagePrompt() {
		@Override
		public String getPromptText(ConversationContext context) {
			Boolean temp = (boolean) context.getSessionData("success");
			if (temp != null && temp) {
				Utils.playSound((Player) context.getForWhom(), Sound.ENTITY_VILLAGER_YES);
				return Utils.prefix + " Device renamed " + ChatColor.GREEN + "successfully" + ChatColor.RESET + "\n------------------";
			} else {
				Utils.playSound((Player) context.getForWhom(), Sound.ENTITY_VILLAGER_NO);
				return Utils.prefix + " Device renamed " + ChatColor.RED + "failed" + ChatColor.RESET + ".\n------------------";
			}
		}

		@Override
		protected Prompt getNextPrompt(ConversationContext context) {
			return Prompt.END_OF_CONVERSATION;
		}
	};
}
