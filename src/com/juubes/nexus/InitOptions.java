package com.juubes.nexus;

import java.util.List;

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
    private final String configFolder;

    public InitOptions(GameLoader gameLoader, AbstractDatabaseManager dbManager, List<String> mapIDs,
                       String defaultPrefix, String configFolder) {
        this(gameLoader, dbManager, mapIDs.toArray(new String[mapIDs.size()]), defaultPrefix, configFolder);
    }

    public InitOptions(GameLoader gameLoader, AbstractDatabaseManager dbManager, String[] mapIDs,
                       String defaultPrefix, String configFolder) {
        this.gameLoader = gameLoader;
        this.dbManager = dbManager;
        this.mapIDs = mapIDs;
        this.defaultPrefix = defaultPrefix;
        this.configFolder = configFolder;
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

    public String getNexusConfigFolder() {
        return configFolder;
    }
}
