package com.gmail.snowmanam2.entitymanager.filter;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.snowmanam2.entitymanager.Messages;

public class CmdClear implements CmdChild {

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(Messages.get("command.base.playerOnly"));
			return true;
		}
		
		Player p = (Player) sender;
		
		FilterSettings settings = FilterSystem.get().getPlayerSettings(p);
		
		settings.clearMaterials();
		
		sender.sendMessage(Messages.get("command.clear.success"));
		
		return true;
	}

	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		// TODO Auto-generated method stub
		return new ArrayList<String>();
	}

}
