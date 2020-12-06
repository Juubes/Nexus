package com.juubes.nexus;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.juubes.nexus.data.AbstractDataHandler;
import com.juubes.nexus.events.ConnectionListener;
import com.juubes.nexus.logic.AbstractLogicHandler;

import lombok.Getter;

/**
 * 
 */
public class Nexus extends JavaPlugin {
	// TODO autosave

	@Getter
	private final AbstractDataHandler dataHandler;

	@Getter
	private final AbstractLogicHandler logicHandler;

	public Nexus(AbstractDataHandler pdh, AbstractLogicHandler glh) {
		this.dataHandler = pdh;
		this.logicHandler = glh;
	}

	/**
	 * Loads playerdata for all online players.
	 */
	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(new ConnectionListener(this), this);

		try {
			// TODO
			Bukkit.getPluginCommand("join").setExecutor(null);
			Bukkit.getPluginCommand("spec").setExecutor(null);
			Bukkit.getPluginCommand("top").setExecutor(null);
			Bukkit.getPluginCommand("shop").setExecutor(null);

		} catch (Exception e) {
			this.setEnabled(false);
			this.getLogger().severe("All the nexus commands are not listed in the extending plugin.yml.");
		}

		// Load playerdata; only runs after reloads
		Bukkit.getOnlinePlayers().forEach(p -> dataHandler.loadPlayerData(p.getUniqueId()));
	}

	/**
	 * Calls {@link AbstractDataHandler#savePlayerData(java.util.UUID)} for all
	 * online players.
	 */
	@Override
	public void onDisable() {
		Bukkit.getOnlinePlayers().forEach(p -> dataHandler.savePlayerData(p.getUniqueId()));
	}

	public int getSeason() {
		return getConfig().getInt("season");
	}
}
