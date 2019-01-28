package com.juubes.nexus.setup;

import java.util.Set;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;

import com.juubes.nexus.logic.Team;
import com.juubes.nexus.playerdata.AbstractPlayerData;
import com.juubes.nexus.playerdata.AbstractStats;

public interface AbstractDatabaseManager {

	// Maps
	public abstract void createMap(String mapID, String displayName, int ticks);

	public abstract boolean isMapCreated(String mapID);

	public abstract String[] getMaps();

	// Lobbies
	public abstract Location getLobbyForMap(String mapID);

	public abstract void saveLobbyForMap(String mapID, Location lobby);

	// Teams
	public abstract Set<String> getTeamList(String mapID);

	public abstract void setTeamList(String mapID, Set<String> teamIDs);

	public abstract Team[] getTeams(String mapID);

	// Spawns
	public abstract void saveTeamSpawn(String editWorld, String string, Location location);

	// Kits
	public abstract Inventory getKitForGame(String mapID);

	public abstract void saveKitForGame(String mapID, Inventory items);

	// Worldnames
	public abstract String getMapDisplayName(String mapID);

	public abstract String getMapID(String name);

	// Playerdata
	public abstract AbstractPlayerData getPlayerData(UUID id);

	public abstract void savePlayerData(AbstractPlayerData data);

	// Stats
	public abstract AbstractStats getSeasonStats(UUID id, int currentSeason);

	public abstract AbstractStats getTotalStats(UUID id);

	public abstract AbstractStats getSeasonStats(String name, int currentSeason);

	public abstract AbstractStats getTotalStats(String name);

	public abstract void saveSeasonStats(AbstractStats stats, int currentSeason);

	public abstract void saveMapSettings(String mapID);

	public abstract void setTeamDisplayName(String mapID, String teamID, String displayName);

	public abstract void setTeamColor(String mapID, String teamID, ChatColor color);

}
