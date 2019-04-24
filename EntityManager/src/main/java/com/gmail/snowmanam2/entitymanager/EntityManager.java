package com.gmail.snowmanam2.entitymanager;

import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.snowmanam2.entitymanager.cleaner.CleanerSystem;
import com.gmail.snowmanam2.entitymanager.filter.FilterSettings;
import com.gmail.snowmanam2.entitymanager.filter.FilterSettingsConfig;
import com.gmail.snowmanam2.entitymanager.filter.FilterSystem;
import com.gmail.snowmanam2.entitymanager.protector.ProtectorSystem;


public class EntityManager extends JavaPlugin {
	
	static {
		ConfigurationSerialization.registerClass(FilterSettings.class, "FilterSettings");
	}
	
	private boolean loaded = false;
	
	@Override
	public void onEnable() {
		loaded = false;
		Messages.get().load(this);
		ProtectedMaterials.get().load(this);
		CleanerSystem.init(this);
		ProtectorSystem.init(this);
		FilterSystem.get().init(this);
		
		loaded = true;
	}
	
	@Override
	public void onDisable() {
		if (loaded) {
			FilterSettingsConfig.get().saveFilterSettings();
		}
	}
}
