package com.versuchdrei.profanityfilter;

import com.versuchdrei.profanityfilter.tree.TreeRun;

/**
 * an API class to access the profanity filter from other plugins
 * @author VersuchDrei
 * @version 1.0
 */
public class ProfanityFilter {

	public static String filter(final String text) {
		final Main plugin = Main.getCurrent();
		if(plugin == null) {
			return text;
		}
		
		return filter(text, plugin);
	}
	
	static String filter(final String text, final Main plugin) {
		return new TreeRun(text, plugin.getRoot()).run();
	}
	
}
