package com.skitskurr.profanityfilter;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

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
