package com.gmail.snowmanam2.entitymanager.filter;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.snowmanam2.entitymanager.Messages;

public class CmdMode implements CmdChild{

	public void printUsage(CommandSender sender, String label) {
		sender.sendMessage(Messages.get("command.mode.usage", label, listModes()));
	}
	
	private String listModes() {
		List<String> modeStrings = new ArrayList<String>();
		
		for (FilterMode mode : FilterMode.values()) {
			modeStrings.add(mode.toString().toLowerCase());
		}
		
		return String.join("|", modeStrings);
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(Messages.get("command.base.playerOnly"));
			return true;
		}
		
		Player p = (Player) sender;
		
		FilterSettings settings = FilterSystem.get().getPlayerSettings(p);
		
		if (args.length == 0) {
			
			sender.sendMessage(Messages.get("command.mode.display", settings.getMode()));
			
			return true;
		}
		
		FilterMode newMode;
		
		try {
			newMode = FilterMode.valueOf(args[0].toUpperCase());
		} catch (IllegalArgumentException e) {
			sender.sendMessage(Messages.get("command.mode.failureIllegalMode", args[0]));
			printUsage(sender, label);
			return true;
		}
		
		settings.setMode(newMode);
		
		sender.sendMessage(Messages.get("command.mode.success", newMode));
		
		return true;
	}

	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if (args.length == 0) {
			List<String> retval = new ArrayList<String>();
			
			for (FilterMode mode : FilterMode.values()) {
				retval.add(mode.toString().toLowerCase());
			}
			
			return retval;
		} else if (args.length == 1) {
			List<String> retval = new ArrayList<String>();
			
			for (FilterMode mode : FilterMode.values()) {
				if (mode.toString().startsWith(args[0].toUpperCase())) {
					retval.add(mode.toString().toLowerCase());
				}
			}
			
			return retval;
		}
		
		return new ArrayList<String>();
	}

}
