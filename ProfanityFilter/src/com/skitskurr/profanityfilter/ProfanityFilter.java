package com.skitskurr.profanityfilter;

import com.skitskurr.profanityfilter.tree.TreeRun;

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
