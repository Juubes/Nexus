package com.juubes.nexus.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.juubes.nexus.Nexus;

public class ConnectionListener implements Listener {
	private final Nexus pl;

	public ConnectionListener(Nexus pl) {
		this.pl = pl;
	}

	@EventHandler
	public void onAsyncJoin(AsyncPlayerPreLoginEvent e) {
		pl.getDataHandler().createIfNotExists(e.getUniqueId());
		pl.getDataHandler().loadPlayerData(e.getUniqueId());
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		
		// Put player in spectator mode
		pl.getLogicHandler().sendToSpectate(p);
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		pl.getDataHandler().savePlayerData(p.getUniqueId());
		pl.getDataHandler().unloadPlayerdata(p.getUniqueId());
	}
}
