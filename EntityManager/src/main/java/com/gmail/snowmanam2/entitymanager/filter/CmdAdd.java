package com.gmail.snowmanam2.entitymanager.filter;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.gmail.snowmanam2.entitymanager.Messages;

public class CmdAdd implements CmdChild {
	
	public void printUsage(CommandSender sender, String label) {
		sender.sendMessage(Messages.get("command.add.usage", label));
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
		
		if (args[0].equalsIgnoreCase("hand")) {
			ItemStack handStack = p.getItemInHand();
			
			mat = handStack.getType();
		} else {
			try {
				mat = Material.valueOf(args[0].toUpperCase());
			} catch (IllegalArgumentException e) {
				sender.sendMessage(Messages.get("command.add.failureInvalidMaterial", args[0]));
				return true;
			}
		}
		
		if (mat.equals(Material.AIR)) {
			sender.sendMessage(Messages.get("command.add.failureAir"));
			return true;
		}
		
		if (!settings.addMaterial(mat)) {
			sender.sendMessage(Messages.get("command.add.failureAlreadyExists", mat.toString()));
			return true;
		}
		
		sender.sendMessage(Messages.get("command.add.success", mat));
		
		return true;
	}

	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		
		List<String> retval = new ArrayList<String>();
		
		if (args.length == 0) {
			for (Material mat : Material.values()) {
				retval.add(mat.toString().toLowerCase());
			}
			
			return retval;
		} else if (args.length == 1) {
			for (Material mat : Material.values()) {
				String matString = mat.toString().toLowerCase();
				
				if (matString.startsWith(args[0].toLowerCase())) {
					retval.add(matString);
				}
			}
			return retval;
		}
		
		return new ArrayList<String>();
	}

}
