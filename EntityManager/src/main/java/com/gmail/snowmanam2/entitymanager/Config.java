package com.gmail.snowmanam2.entitymanager;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Config {
	private File configFile = null;
	private FileConfiguration config = null;
	private String filename = "";
	private JavaPlugin plugin = null;
	
	public Config(String filename) {
		this.filename = filename;
	}
	
	public JavaPlugin getPlugin() {
		return this.plugin;
	}
	
	public FileConfiguration getConfig() {
		return this.config;
	}
	
	public void saveConfig() {
		try {
			this.config.save(configFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void reload() {
		this.load(this.plugin);
	}
	
	public void load(JavaPlugin plugin) {
		this.plugin = plugin;
		
		if (configFile == null) {
			configFile = new File(plugin.getDataFolder(), filename);
		}
		config = YamlConfiguration.loadConfiguration(configFile);

		// Look for defaults in the jar
		Reader defConfigStream = null;
		try {
			defConfigStream = new InputStreamReader(plugin.getResource(filename), "UTF8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			config.addDefaults(defConfig);
		} else {
			plugin.getLogger().warning("Wasn't able to get default config file!");
		}
		
		config.options().copyDefaults(true);
		
		try {
			config.save(configFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
