package com.juubes.nexus.events;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.juubes.nexus.Nexus;
import com.juubes.nexus.data.AbstractPlayerData;

public class AutoJoinMoveListener implements Listener {
	private final Nexus nexus;

	public AutoJoinMoveListener(Nexus nexus) {
		this.nexus = nexus;
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		AbstractPlayerData pd = nexus.getDatabaseManager().getPlayerData(e.getPlayer());
		Location locFrom = e.getFrom();
		Location locTo = e.getTo();

		// Let's just try for one... All the same
		if (locFrom.getX() != locTo.getX() || locFrom.getY() != locFrom.getY() || locFrom.getZ() != locFrom.getZ())
			pd.setAutoJoin(true);
	}
}
