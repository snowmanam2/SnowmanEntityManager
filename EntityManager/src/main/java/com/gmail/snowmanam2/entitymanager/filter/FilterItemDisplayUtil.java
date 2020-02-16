package com.gmail.snowmanam2.entitymanager.filter;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.snowmanam2.entitymanager.Messages;
import com.massivecraft.massivecore.mixin.MixinMessage;
import com.massivecraft.massivecore.mson.Mson;

public class FilterItemDisplayUtil {
	private static boolean useMson = false;
	
	public static void init(JavaPlugin plugin) {
		if (plugin.getServer().getPluginManager().getPlugin("MassiveCore") == null) {
			useMson = false;
			return;
		} else {
			useMson = true;
		}
	}
	
	public static void displayItem(CommandSender sender, String rawText) {
		if (useMson) {
			Mson mson = Mson.parse("");
			mson = mson.add(
					Mson.parse(Messages.get("command.list.item", rawText))
					);
			mson = mson.add(
					Mson.parse(" "+"<red>x")
					.command("/if remove "+rawText)
					);
		
			MixinMessage.get().messageOne(sender, mson);
		} else {
			sender.sendMessage(Messages.get("command.list.item", rawText));
		}
	}
}
