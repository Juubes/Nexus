package com.juubes.nexus.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class StartCountdownEvent extends Event {
    public static final HandlerList HANDLERS = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public String mapID;

    public StartCountdownEvent(String mapID) {
        this.mapID = mapID;
    }

    public String getMapID() {
        return mapID;
    }
}
