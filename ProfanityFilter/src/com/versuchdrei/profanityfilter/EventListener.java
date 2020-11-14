package com.versuchdrei.profanityfilter;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * the event listener of the profanity filter, 
 * handles text messages and filters out profane words
 * @author VersuchDrei
 * @version 1.0
 */
public class EventListener implements Listener{
	
	private final Main plugin;
	
	public EventListener(final Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onChat(final AsyncPlayerChatEvent event) {
		event.setMessage(ProfanityFilter.filter(event.getMessage(), this.plugin));
	}

}
