package com.juubes.nexus;

import com.juubes.nexus.data.AbstractDatabaseManager;

public class InitOptions {
	public AbstractDatabaseManager getDbManager() {
		return dbManager;
	}

	private final AbstractDatabaseManager dbManager;
	private final String[] mapIDs;
	private final String defaultPrefix;

	public InitOptions(AbstractDatabaseManager dbManager, String[] mapIDs, String defaultPrefix) {
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

}
