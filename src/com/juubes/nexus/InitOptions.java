package com.juubes.nexus;

import com.juubes.nexus.data.AbstractDatabaseManager;
import com.juubes.nexus.logic.GameLoader;

public class InitOptions {
	public AbstractDatabaseManager getDbManager() {
		return dbManager;
	}

	private final AbstractDatabaseManager dbManager;
	private final String[] mapIDs;
	private final String defaultPrefix;
	private final GameLoader gameLoader;

	public InitOptions(GameLoader gameLoader, AbstractDatabaseManager dbManager, String[] mapIDs,
			String defaultPrefix) {
		this.gameLoader = gameLoader;
		this.dbManager = dbManager;
		this.mapIDs = mapIDs;
		this.defaultPrefix = defaultPrefix;
	}

	public AbstractDatabaseManager getDatabaseManager() {
		return this.dbManager;
	}

	public String[] getMapIDs() {
		return mapIDs;
	}

	public String getDefaultPrefix() {
		return defaultPrefix;
	}

	public GameLoader getGameLoader() {
		return gameLoader;
	}

}
