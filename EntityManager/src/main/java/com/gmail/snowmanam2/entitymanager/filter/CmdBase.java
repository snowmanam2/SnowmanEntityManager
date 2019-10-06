package com.gmail.snowmanam2.entitymanager.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import com.gmail.snowmanam2.entitymanager.Messages;

public class CmdBase implements CommandExecutor, TabCompleter {

	private Map<String, CmdChild> children;
	private Map<String, String> aliases;
	
	public CmdBase() {
		children = new HashMap<String, CmdChild>();
		aliases = new HashMap<String, String>();
	}
	
	public void addChild(String name, CmdChild child) {
		children.put(name, child);
	}
	
	public void addAlias(String command, String alias) {
		aliases.put(alias, command);
	}
	
	public void printUsage(CommandSender sender, String label) {
		sender.sendMessage(Messages.get("command.base.usage", label, String.join(" | ", children.keySet())));
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (args.length > 0) {
			String subcommand = args[0].toLowerCase();
			
			if (aliases.containsKey(subcommand)) {
				subcommand = aliases.get(subcommand);
			}
			
			if (children.containsKey(subcommand)) {
                String[] newArgs = new String[args.length - 1];
                System.arraycopy(args, 1, newArgs, 0, newArgs.length);
				children.get(subcommand).onCommand(sender, command, label+" "+subcommand, newArgs);
			} else {
				sender.sendMessage(Messages.get("command.base.notfound", subcommand));
				printUsage(sender, label);
			}
		} else {
			printUsage(sender, label);
		}
		
		return true;
	}

	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		
		if (args.length == 0) {
			return new ArrayList<String>(children.keySet());
		} else if (args.length == 1) {
			List<String> retval = new ArrayList<String>();
			
			for (String key : children.keySet()) {
				if (key.startsWith(args[0])) {
					retval.add(key);
				}
			}
			
			return retval;
		} else {
			String subcommand = args[0].toLowerCase();
			
			if (aliases.containsKey(subcommand)) {
				subcommand = aliases.get(subcommand);
			}
			
			if (!children.containsKey(subcommand)) return new ArrayList<String>();
			
			CmdChild child = children.get(subcommand);
			
            String[] newArgs = new String[args.length - 1];
            System.arraycopy(args, 1, newArgs, 0, newArgs.length);
			
			return child.onTabComplete(sender, command, alias, newArgs);
		}
	}

}
