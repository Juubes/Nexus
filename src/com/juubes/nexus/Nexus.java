package com.juubes.nexus;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.juubes.nexus.commands.JoinCommand;
import com.juubes.nexus.data.AbstractDataHandler;
import com.juubes.nexus.data.AbstractMap;
import com.juubes.nexus.events.ConnectionListener;
import com.juubes.nexus.logic.AbstractLogicHandler;
import com.juubes.nexus.logic.GameWorldHandler;

public abstract class Nexus extends JavaPlugin {

	private final GameWorldHandler gameWorldHandler;

	public Nexus() {
		this.gameWorldHandler = new GameWorldHandler(this);
	}

	/**
	 * Loads playerdata for all online players.
	 */
	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(new ConnectionListener(this), this);

		try {
			// TODO
			Bukkit.getPluginCommand("join").setExecutor(new JoinCommand(this));
			Bukkit.getPluginCommand("spec").setExecutor(null);
			Bukkit.getPluginCommand("top").setExecutor(null);
			Bukkit.getPluginCommand("shop").setExecutor(null);
		} catch (Exception e) {
			this.getLogger().severe("All the nexus commands are not listed in the extending plugin.yml.");
			this.getLogger().severe("Startup is not possible.");
		}

		// Load playerdata; only runs after reloads
		Bukkit.getOnlinePlayers().forEach(p -> this.getDataHandler().loadPlayerData(p.getUniqueId()));

	}

	/**
	 * Calls {@link AbstractDataHandler#savePlayerData(java.util.UUID)} for all
	 * online players.
	 */
	@Override
	public void onDisable() {
		Bukkit.getOnlinePlayers().forEach(p -> this.getDataHandler().savePlayerData(p.getUniqueId()));
	}

	public abstract AbstractDataHandler getDataHandler();

	public abstract AbstractLogicHandler getLogicHandler();

	public GameWorldHandler getGameWorldHandler() {
		return gameWorldHandler;
	}

	public int getSeason() {
		return getConfig().getInt("season");
	}
}
