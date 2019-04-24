package com.gmail.snowmanam2.entitymanager.cleaner;

import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;

import com.gmail.snowmanam2.entitymanager.ProtectedMaterials;

public class CleanerTask implements Runnable {

	private int lifetime = 300;
	
	public void setLifetime(int lifetime) {
		this.lifetime = lifetime;
	}
	
	public void run() {
		Iterator<World> itrWorld = Bukkit.getWorlds().iterator();
		
		while (itrWorld.hasNext()) {
			World w = itrWorld.next();
			
			Iterator<Entity> itrEntity = w.getEntities().iterator();
			
			while (itrEntity.hasNext()) {
				Entity e = itrEntity.next();
				
				// TODO: configurable entity lifetimes
				if (e.getTicksLived() > this.lifetime && e instanceof Item) {
					Item item = (Item) e;
					if (!ProtectedMaterials.get().isProtected(item.getItemStack().getType())) {
						e.remove();
					}
				}
			}
		}
	}
}
