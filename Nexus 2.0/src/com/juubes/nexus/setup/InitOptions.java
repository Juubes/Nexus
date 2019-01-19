package com.juubes.nexus.setup;

public class InitOptions {
	public AbstractDatabaseManager getDbManager() {
		return dbManager;
	}

	private AbstractDatabaseManager dbManager;
	private String[] mapIDs;

	public AbstractDatabaseManager getDatabaseManager() {
		return this.dbManager;
	}

	public String[] getMapIDs() {
		return mapIDs;
	}

	public void setDatabaseManager(AbstractDatabaseManager dbManager) {
		this.dbManager = dbManager;
	}

	public void setMapIDs(String[] mapIDs) {
		this.mapIDs = mapIDs;
	}

}
