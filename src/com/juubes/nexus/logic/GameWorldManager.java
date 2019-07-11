package com.juubes.nexus.logic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;

import com.juubes.nexus.Nexus;
import com.juubes.nexus.events.PreLoadGameWorldEvent;

public class GameWorldManager {
	private final Nexus nexus;
	private final List<String> mapIDs;

	private String currentMapID;

	public GameWorldManager(Nexus nexus) {
		this.nexus = nexus;
		this.mapIDs = new ArrayList<>();
	}

	public String getCurrentMapID() {
		return currentMapID;
	}

	public World loadNextWorld(String request) {
		mapIDs.clear();
		mapIDs.addAll(Arrays.asList(nexus.getInitOptions().getMapIDs()));

		String nextMapID;
		if (request == null)
			do {
				nextMapID = mapIDs.get((int) (Math.random() * mapIDs.size()));
			} while (nextMapID.equals(currentMapID));
		else
			nextMapID = request;

		// First delete and replace with a generated world, then load it
		System.out.println("Next map ID: " + nextMapID);
		File worldAtContainer = new File(Bukkit.getWorldContainer(), nextMapID);
		File backupWorld = new File(nexus.getConfigFolder(), "maps/" + nextMapID);

		try {
			FileUtils.deleteDirectory(worldAtContainer);
			FileUtils.copyDirectory(backupWorld, worldAtContainer);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Failed to load a new world!");
		}

		WorldCreator wc = new WorldCreator(nextMapID);
		wc.type(WorldType.FLAT);
		wc.generatorSettings("2;0;1;");
		World createdWorld = Bukkit.createWorld(wc);
		createdWorld.setStorm(false);
		createdWorld.setThundering(false);
		createdWorld.setTime(0);
		createdWorld.setWeatherDuration(5000000);
		createdWorld.setGameRuleValue("doDaylightCycle", "false");
		createdWorld.setGameRuleValue("randomTickSpeed", "5");

		// Call event for preload
		Bukkit.getPluginManager().callEvent(new PreLoadGameWorldEvent(nextMapID, createdWorld));

		// Teleport players to the next map that was loaded
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.getWorld().equals(Bukkit.getWorlds().get(0)))
				continue;
			Location lobby = nexus.getDatabaseManager().getLobbyForMap(nextMapID);
			if (lobby != null)
				p.teleport(lobby);
			else
				p.teleport(new Location(createdWorld, 0, 100, 0));
		}

		if (currentMapID != null) {
			// Unload without saving
			Bukkit.unloadWorld(currentMapID, false);

			// Delete the worldfolder so it doens't get messy
			try {
				FileUtils.deleteDirectory(new File(Bukkit.getWorldContainer() + "/" + currentMapID));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		currentMapID = nextMapID;
		return createdWorld;
	}
}