package com.gmail.snowmanam2.entitymanager.filter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.snowmanam2.entitymanager.ProtectedMaterials;

public class FilterSystem implements Listener {
	private static FilterSystem i = new FilterSystem();
	public static FilterSystem get() { return i; }
	
	private Map<UUID, FilterSettings> playerSettings;
	
	public FilterSettings getPlayerSettings(Player p) {
		if (!playerSettings.containsKey(p.getUniqueId())) {
			playerSettings.put(p.getUniqueId(), new FilterSettings());
		}
		
		return playerSettings.get(p.getUniqueId());
	}
	
	public void setPlayerSettings(UUID uuid, FilterSettings settings) {
		playerSettings.put(uuid, settings);
	}
	
	public Map<UUID, FilterSettings> getAllPlayerSettings() {
		return playerSettings;
	}
	
	public void init(JavaPlugin plugin) {
		playerSettings = new HashMap<UUID, FilterSettings>();
		
		CmdFilter cmdFilter = new CmdFilter();
		plugin.getCommand("inventoryfilter").setExecutor(cmdFilter);
		plugin.getCommand("inventoryfilter").setTabCompleter(cmdFilter);
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		FilterSettingsConfig.get().load(plugin);
		FilterItemDisplayUtil.init(plugin);
	}
	
	@EventHandler(priority=EventPriority.HIGH)
	public void onPickupItem (PlayerPickupItemEvent e) {
		FilterSettings settings = getPlayerSettings(e.getPlayer());
		
		Material mat = e.getItem().getItemStack().getType();
		
		switch(settings.getMode()) {
		case BLACKLIST:
			if (settings.inMaterialList(mat)) {
				e.setCancelled(true);
			}
			break;
		case DISABLED:
			break;
		case VALUABLE:
			if (!ProtectedMaterials.get().isProtected(mat)) {
				e.setCancelled(true);
			}
			break;
		case WHITELIST:
			if (!settings.inMaterialList(mat)) {
				e.setCancelled(true);
			}
			break;
		default:
			break;
		}
	}
}
