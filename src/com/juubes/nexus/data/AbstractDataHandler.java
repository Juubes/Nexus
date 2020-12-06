package com.juubes.nexus.data;

import java.util.LinkedList;
import java.util.UUID;

public abstract class AbstractDataHandler {
	// Playerdata
	public abstract void loadPlayerData(UUID uuid);

	public abstract AbstractPlayerData createIfNotExists(UUID uuid);

	public abstract AbstractPlayerData getPlayerData(UUID uuid);

	public abstract void savePlayerData(UUID uuid);

	/**
	 * After this method, the playerdata with the UUID cannot be used; it must be
	 * loaded first.
	 * <p>
	 * By default doesn't save playerdata prior to unloading.
	 */
	public abstract void unloadPlayerdata(UUID uuid);

	// Maps
	public abstract void loadMaps();

	public abstract AbstractMap createMapIfNotExists(String mapID);

	public abstract AbstractMap getMap(String mapID);

	public abstract void saveMap(AbstractMap map);

	/**
	 * 
	 * If the season is 0, return all time high.
	 * 
	 * This playerdata is not loaded and shouldn't be used as such. If you want the
	 * data of an online player, please use {@link #getPlayerData(UUID)}.
	 * 
	 */
	public abstract LinkedList<? extends AbstractPlayerData> getLeaderboard(int count, int season);
}
