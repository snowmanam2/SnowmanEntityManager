package com.gmail.snowmanam2.entitymanager.filter;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.gmail.snowmanam2.entitymanager.Messages;

public class CmdBase implements CommandExecutor {

	private Map<String, CommandExecutor> children;
	private Map<String, String> aliases;
	
	public CmdBase() {
		children = new HashMap<String, CommandExecutor>();
		aliases = new HashMap<String, String>();
	}
	
	public void addChild(String name, CommandExecutor child) {
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

}
