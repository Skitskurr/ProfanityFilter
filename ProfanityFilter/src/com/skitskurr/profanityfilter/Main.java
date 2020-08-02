package com.skitskurr.profanityfilter;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.skitskurr.profanityfilter.tree.Knot;
import com.skitskurr.profanityfilter.tree.ProfanityTree;

public class Main extends JavaPlugin{
	
	private static Main current;
	
	private Knot root = new Knot();

	@Override
	public void onEnable() {
		super.saveDefaultConfig();
		Main.current = this;
		root = ProfanityTree.setupTree(super.getConfig());
		
		Bukkit.getPluginManager().registerEvents(new EventListener(this), this);
	}
	
	static Main getCurrent() {
		return Main.current;
	}
	
}
