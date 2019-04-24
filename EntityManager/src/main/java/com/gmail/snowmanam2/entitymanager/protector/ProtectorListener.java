package com.gmail.snowmanam2.entitymanager.protector;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.gmail.snowmanam2.entitymanager.ProtectedMaterials;

public class ProtectorListener implements Listener {
	@EventHandler
    public void onDamage (EntityDamageEvent event) {
    	
    	if (event.getCause() == DamageCause.VOID) {
    		return;
    	}
    	
    	if (event.getEntityType() == EntityType.DROPPED_ITEM) {
    		Item entityItem = (Item) event.getEntity();
    		Material entityMaterial = entityItem.getItemStack().getType();
    		
    		if (ProtectedMaterials.get().isProtected(entityMaterial)) {
    			event.setCancelled(true);
    		}
    	}
    }
}
