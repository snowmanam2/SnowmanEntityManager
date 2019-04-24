package com.gmail.snowmanam2.entitymanager.filter;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.snowmanam2.entitymanager.Messages;

public class CmdRemove implements CommandExecutor {
	
	public void printUsage(CommandSender sender, String label) {
		sender.sendMessage(Messages.get("command.remove.usage", label));
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(Messages.get("command.base.playerOnly"));
			return true;
		}
		
		if (args.length == 0) {
			printUsage(sender, label);
			return true;
		}
		
		Player p = (Player) sender;
		
		FilterSettings settings = FilterSystem.get().getPlayerSettings(p);
		
		Material mat;
		
		try {
			mat = Material.valueOf(args[0].toUpperCase());
		} catch (IllegalArgumentException e) {
			sender.sendMessage(Messages.get("command.remove.failureInvalidMaterial"));
			return true;
		}
		
		if (!settings.removeMaterial(mat)) {
			sender.sendMessage(Messages.get("command.remove.failureNoExist"));
			return true;
		}
		
		sender.sendMessage(Messages.get("command.remove.success", mat));
		
		return true;
	}

}
