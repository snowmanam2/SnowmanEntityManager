package com.gmail.snowmanam2.entitymanager;

import org.bukkit.ChatColor;

public class Messages extends Config {
	private static Messages i = new Messages();
	public static Messages get() { return i; }
	
	private Messages() {
		super("messages.yml");
	}
	
	public String getString(String key) {
		if (getConfig() != null) {
			if (getConfig().contains(key)) {
				String message = String.valueOf(getConfig().get(key));
				return ChatColor.translateAlternateColorCodes('&', message);
			} else {
				return "";
			}
		} else {
			return "";
		}
	}
	
	public static String get(String key) {
		return Messages.get().getString(key);
	}
	public static String get(String key, Object... args) {
		String message = Messages.get(key);
		
		for (int i = 0; i < args.length ; i++) {
			String replaceString = "{"+i+"}";
			
			if (message.contains(replaceString)) {
				message = message.replace(replaceString, String.valueOf(args[i]));
			}
		}
		
		return message;
	}
	
}
