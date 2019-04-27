package com.gmail.snowmanam2.entitymanager.filter;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.snowmanam2.entitymanager.Messages;

public class CmdRemove implements CmdChild {
	
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

	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if (!(sender instanceof Player)) return null;
		
		Player p = (Player) sender;
		
		FilterSettings settings = FilterSystem.get().getPlayerSettings(p);
		
		if (args.length == 0) {
			return settings.getMaterialListing();
		} else if (args.length == 1) {
			List<String> retval = new ArrayList<String>();
			
			for (String item : settings.getMaterialListing()) {
				if (item.startsWith(args[0].toUpperCase())) {
					retval.add(item);
				}
			}
			
			return retval;
		}
		
		return new ArrayList<String>();
	}

}
