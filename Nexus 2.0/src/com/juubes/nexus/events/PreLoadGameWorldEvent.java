package com.juubes.nexus.events;

import org.bukkit.World;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PreLoadGameWorldEvent extends Event {
	public static final HandlerList HANDLERS = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}

	public World world;

	public PreLoadGameWorldEvent(World world) {
		this.world = world;
	}

	public World getWorld() {
		return world;
	}
}
