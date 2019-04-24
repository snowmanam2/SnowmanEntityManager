package com.gmail.snowmanam2.entitymanager.filter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

@SerializableAs("FilterSettings")
public class FilterSettings implements Cloneable, ConfigurationSerializable{
	private FilterMode mode;
	private Set<Material> materials;
	
	public FilterSettings() {
		mode = FilterMode.DISABLED;
		materials = new HashSet<Material>();
	}
	
	public boolean addMaterial(Material mat) {
		
		if (materials.contains(mat)) return false;
		
		materials.add(mat);
		
		return true;
	}
	
	public boolean removeMaterial (Material mat) {
		if (!materials.contains(mat)) return false;
		
		materials.remove(mat);
		
		return true;
	}
	
	public void clearMaterials () {
		materials.clear();
	}
	
	public List<String> getMaterialListing() {
		List<String> retval = new ArrayList<String>();
		
		for (Material mat : materials) {
			retval.add(mat.toString());
		}
		
		return retval;
	}
	
	public boolean inMaterialList(Material mat) {
		return materials.contains(mat);
	}
	
	public void setMode(FilterMode mode) {
		this.mode = mode;
	}
	
	public FilterMode getMode() {
		return mode;
	}
	
	public Map<String, Object> serialize() {
		LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
		
		result.put("mode", mode.toString());
		List<String> materialList = new ArrayList<String>();
		
		for (Material mat : materials) {
			materialList.add(mat.toString());
		}
		
		result.put("materials", materialList);
		
		return result;
	}
	
	public static FilterSettings deserialize(Map<String, Object> args) {
		FilterMode mode = FilterMode.valueOf((String)args.get("mode"));
		
		FilterSettings retval = new FilterSettings();
		
		retval.setMode(mode);
		
		@SuppressWarnings("unchecked")
		List<String> materialList = (List<String>) args.get("materials");
		
		for (String materialString : materialList) {
			retval.addMaterial(Material.valueOf(materialString));
		}
		
		return retval;
	}
}
