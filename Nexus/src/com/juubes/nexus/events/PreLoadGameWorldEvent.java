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
	public String mapID;

	public PreLoadGameWorldEvent(String mapID, World world) {
		this.world = world;
		this.mapID = mapID;
	}

	public World getWorld() {
		return world;
	}

	public String getMapID() {
		return mapID;
	}
}
