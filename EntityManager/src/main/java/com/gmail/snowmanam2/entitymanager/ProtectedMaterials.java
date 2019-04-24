package com.gmail.snowmanam2.entitymanager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public class ProtectedMaterials extends Config {
	private static ProtectedMaterials i = new ProtectedMaterials();
	public static ProtectedMaterials get() { return i; }
	
	private Set<Material> materials;
	
	private ProtectedMaterials() {
		super("protectedmaterials.yml");
	}
	
	public Set<Material> getMaterials() {
		return materials;
	}
	
	public boolean isProtected(Material mat) {
		return materials.contains(mat);
	}
	
	@Override
	public void load(JavaPlugin plugin) {
		super.load(plugin);
		loadMaterials();
	}
	
	private void loadMaterials () {
		materials = new HashSet<Material>();
		
		List<String> materialStrings = getConfig().getStringList("materials");
		
		for (String s : materialStrings) {
			try {
				Material mat = Material.valueOf(s);
				materials.add(mat);
			} catch (IllegalArgumentException e) {
				getPlugin().getLogger().warning("Invalid material '"+s+"'");
			}
		}
	}
}
