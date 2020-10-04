package com.juubes.nexus.data;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;

import com.juubes.nexus.NexusLocation;
import com.juubes.nexus.logic.Team;

/**
 * It's the implementers job to implement a cache for all the data.
 * 
 */
public abstract class AbstractDatabaseManager {

	// Maps
	public abstract void createMap(String mapID, String displayName, int ticks);

	public abstract boolean isMapCreated(String mapID);

	public abstract String[] getMaps();

	// Lobbies
	public abstract NexusLocation getLobbyForMap(String mapID);

	public abstract void saveLobbyForMap(String mapID, NexusLocation lobby);

	// Teams
	public abstract Set<String> getTeamList(String mapID);

	public abstract void setTeamList(String mapID, Set<String> teamIDs);

	public abstract Team[] getTeams(String mapID);

	// Spawns
	public abstract void saveTeamSpawn(String editWorld, String string, NexusLocation location);

	// Kits
	public abstract Inventory getKitForGame(String mapID);

	public abstract void saveKitForGame(String mapID, Inventory items);

	// Worldnames
	public abstract String getMapDisplayName(String mapID);

	public abstract String getMapID(String name);

	// Playerdata
	public abstract AbstractPlayerData getPlayerData(UUID uuid);

	public abstract void savePlayerData(UUID uuid);

	public abstract UUID getUUIDByLastSeenName(String name);

	public abstract HashMap<UUID, ? extends AbstractPlayerData> getAllPlayerData();

	// Stats
	public abstract AbstractSeasonStats getSeasonStats(String name, int season);

	public abstract AbstractSeasonStats getSeasonStats(UUID id, int season);

	public abstract AbstractTotalStats getTotalStats(String name);

	public abstract AbstractTotalStats getTotalStats(UUID id);

	public abstract void saveSeasonStats(UUID uuid);

	public abstract void saveMapSettings(String mapID);

	public abstract void setTeamDisplayName(String mapID, String teamID, String displayName);

	public abstract void setTeamColor(String mapID, String teamID, ChatColor color);

	// Leaderboard
	public abstract LinkedList<? extends AbstractSeasonStats> getLeaderboard(int count, int season);

	public abstract LinkedList<? extends AbstractTotalStats> getAlltimeLeaderboard(int count);


}
