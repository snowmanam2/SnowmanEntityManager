package com.gmail.snowmanam2.entitymanager.cleaner;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class CleanerSystem {
	private static CleanerSystem i = new CleanerSystem();
	public static CleanerSystem get() { return i; }
	private CleanerSystem() {
		
	}
	
	public static void init(JavaPlugin plugin) {
		BukkitScheduler scheduler = plugin.getServer().getScheduler();
		Long interval = plugin.getConfig().getLong("cleaner.tickinterval", 400L);
		int lifetime = plugin.getConfig().getInt("cleaner.lifetime", 300);
		CleanerTask task = new CleanerTask();
		task.setLifetime(lifetime);
		scheduler.scheduleSyncRepeatingTask(plugin, task, 0L, interval);
	}
}
