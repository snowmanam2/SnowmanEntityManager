package com.gmail.snowmanam2.entitymanager.filter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.snowmanam2.entitymanager.Messages;

public class CmdList implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(Messages.get("command.base.playerOnly"));
			return true;
		}
		
		Player p = (Player) sender;
		
		FilterSettings settings = FilterSystem.get().getPlayerSettings(p);
		
		sender.sendMessage(Messages.get("command.list.header"));
		
		for (String item : settings.getMaterialListing()) {
			sender.sendMessage(Messages.get("command.list.item", item));
		}
		
		return true;
	}

}
