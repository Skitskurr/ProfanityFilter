package com.versuchdrei.profanityfilter;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.versuchdrei.profanityfilter.tree.Knot;
import com.versuchdrei.profanityfilter.tree.ProfanityTree;

/**
 * the main class of the profanity filter
 * @author VersuchDrei
 * @version 1.0
 */
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
	
	Knot getRoot() {
		return this.root;
	}
	
}
