package com.gmail.snowmanam2.entitymanager.protector;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class ProtectorSystem {
	private static ProtectorSystem i = new ProtectorSystem();
	public static ProtectorSystem get() { return i; }
	
	private ProtectorSystem() {
		
	}
	
	public static void init(JavaPlugin plugin) {
		Bukkit.getServer().getPluginManager().registerEvents(new ProtectorListener(), plugin);
	}
}
