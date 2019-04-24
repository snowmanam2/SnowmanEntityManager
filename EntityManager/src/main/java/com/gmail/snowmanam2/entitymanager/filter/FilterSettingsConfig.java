package com.gmail.snowmanam2.entitymanager.filter;

import java.util.Map;
import java.util.UUID;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.snowmanam2.entitymanager.Config;

public class FilterSettingsConfig extends Config {
	private static FilterSettingsConfig i = new FilterSettingsConfig();
	public static FilterSettingsConfig get() { return i; }
	
	private FilterSettingsConfig() {
		super("filtersettings.yml");
	}
	
	@Override
	public void load(JavaPlugin plugin) {
		super.load(plugin);
		
		loadFilterSettings();
	}
	
	private void loadFilterSettings () {
		getPlugin().getLogger().info("Loading filter settings...");
		ConfigurationSection section = getConfig().getConfigurationSection("settings");
		
		if (section == null) {
			getPlugin().getLogger().info("No settings to load.");
			return;
		}
		
		for (String key : section.getKeys(false)) {
			UUID uuid = UUID.fromString(key);
			
			FilterSettings settings = (FilterSettings) section.get(key);
			
			FilterSystem.get().setPlayerSettings(uuid, settings);
		}
		getPlugin().getLogger().info("Filter settings loaded.");
	}
	
	public void saveFilterSettings() {
		getPlugin().getLogger().info("Saving filter settings...");
		
		ConfigurationSection section = getConfig().createSection("settings");
		
		Map<UUID, FilterSettings> settings = FilterSystem.get().getAllPlayerSettings();
		
		for (UUID key : settings.keySet()) {
			section.set(key.toString(), settings.get(key));
		}
		
		saveConfig();
		getPlugin().getLogger().info("Filter settings saved.");
	}
}
