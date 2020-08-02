package com.skitskurr.profanityfilter;

public class ProfanityFilter {

	public static String filter(final String text) {
		final Main plugin = Main.getCurrent();
		if(plugin == null) {
			return text;
		}
		
		return filter(text, plugin);
	}
	
	static String filter(final String text, final Main plugin) {
		return text;
	}
	
}
