package de.deltasiege.SmartRedstone;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SmartCommands implements CommandExecutor {
	public SmartRedstone plugin;
	
	public SmartCommands(SmartRedstone plugin) {
		this.plugin = plugin;
		plugin.getCommand("sd").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] parameters) {
		if (sender instanceof Player) {
			sender.sendMessage("/sd command executed ...");
			return true;
		}
		
		return false;
	}
	
}
