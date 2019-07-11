package com.juubes.nexus;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.juubes.nexus.commands.AddTeamCommand;
import com.juubes.nexus.commands.EditModeHandler;
import com.juubes.nexus.commands.GetposCommand;
import com.juubes.nexus.commands.JoinCommand;
import com.juubes.nexus.commands.NextmapCommand;
import com.juubes.nexus.commands.PlayTimeCommand;
import com.juubes.nexus.commands.RemoveTeamCommand;
import com.juubes.nexus.commands.SaveKitCommand;
import com.juubes.nexus.commands.SaveMapCommand;
import com.juubes.nexus.commands.SetLobbyCommand;
import com.juubes.nexus.commands.SetPrefixCommand;
import com.juubes.nexus.commands.SetTeamSpawnCommand;
import com.juubes.nexus.commands.SpectateCommand;
import com.juubes.nexus.commands.StartCommand;
import com.juubes.nexus.commands.StatsCommand;
import com.juubes.nexus.commands.WorldsCommand;
import com.juubes.nexus.data.AbstractDatabaseManager;
import com.juubes.nexus.events.AutoJoinMoveListener;
import com.juubes.nexus.logic.GameLoader;
import com.juubes.nexus.logic.GameLogic;
import com.juubes.nexus.logic.GameWorldManager;
import com.juubes.nexus.logic.PauseCommand;

public class Nexus extends JavaPlugin {
	private final Lang lang;
	private final GameWorldManager gameWorldManager;
	private final GameLogic gameLogic;
	private final EditModeHandler editModeHandler;

	private InitOptions options;

	public Nexus() {
		this.editModeHandler = new EditModeHandler(this);
		this.lang = new Lang();
		this.gameWorldManager = new GameWorldManager(this);
		this.gameLogic = new GameLogic(this);
	}

	@Override
	public void onEnable() {
		this.saveDefaultConfig();
		this.makeDefaultFolders();

		JoinCommand njc = new JoinCommand(this);
		getCommand("join").setExecutor(njc);
		getCommand("spec").setExecutor(new SpectateCommand(this));

		PauseCommand nexusCommands = new PauseCommand(this);
		getCommand("pause").setExecutor(nexusCommands);

		GetposCommand gpcmd = new GetposCommand();
		WorldsCommand wcmd = new WorldsCommand();
		getCommand("getpos").setExecutor(gpcmd);
		getCommand("world").setExecutor(wcmd);
		getCommand("worlds").setExecutor(wcmd);

		StatsCommand scmd = new StatsCommand(this);
		getCommand("stats").setExecutor(scmd);
		getCommand("setprefix").setExecutor(new SetPrefixCommand(this));

		NextmapCommand nmCmd = new NextmapCommand(this);
		getCommand("nextmap").setExecutor(nmCmd);
		getCommand("start").setExecutor(new StartCommand(this));
		getCommand("gametime").setExecutor(new PlayTimeCommand(this));

		getCommand("savemap").setExecutor(new SaveMapCommand(this));
		getCommand("editmode").setExecutor(new EditModeHandler(this));
		getCommand("setlobby").setExecutor(new SetLobbyCommand(this));
		getCommand("addteam").setExecutor(new AddTeamCommand(this));
		getCommand("removeteam").setExecutor(new RemoveTeamCommand(this));
		getCommand("setteamspawn").setExecutor(new SetTeamSpawnCommand(this));

		getCommand("savekit").setExecutor(new SaveKitCommand(this));

		Bukkit.getPluginManager().registerEvents(new AutoJoinMoveListener(this), this);

		saveDefaultKitFile();

		gameLogic.getCountdownHandler().startScheduling();
	}

	private void makeDefaultFolders() {
		new File(this.getConfigFolder(), "maps").mkdirs();
		new File(this.getConfigFolder(), "settings").mkdirs();
	}

	public void init(InitOptions options) {
		this.options = options;
		gameLogic.loadNextGame();
	}

	public AbstractDatabaseManager getDatabaseManager() {
		return options.getDatabaseManager();
	}

	public static Nexus getAPI() {
		return (Nexus) Bukkit.getPluginManager().getPlugin("Nexus");
	}

	public InitOptions getInitOptions() {
		return options;
	}

	public boolean isReady() {
		if (options.getDatabaseManager() == null) {
			System.out.println("Setup incomplete: 1");
			return false;
		}
		if (options.getMapIDs() == null) {
			System.out.println("Setup incomplete: 2");
			return false;
		}
		if (!getGameLogic().isReadyToPlay()) {
			System.out.println("Setup incomplete: 3");
			return false;
		}
		return true;
	}

	/**
	 * Saves the config to ../Nexus/ instead of ./plugins/Nexus/
	 */
	@Override
	public void saveDefaultConfig() {
		try {
			File newConf = new File(getConfigFolder(), "config.yml");
			if (!newConf.exists())
				YamlConfiguration.loadConfiguration(new InputStreamReader(getResource("config.yml"))).save(newConf);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveDefaultKitFile() {
		try {
			File newConf = new File(getConfigFolder(), "kits.yml");
			if (!newConf.exists())
				YamlConfiguration.loadConfiguration(new InputStreamReader(getResource("kits.yml"))).save(newConf);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private FileConfiguration nexusConfig;

	@Override
	public FileConfiguration getConfig() {
		if (this.nexusConfig == null)
			this.nexusConfig = YamlConfiguration.loadConfiguration(new File("../Nexus/config.yml"));
		return nexusConfig;
	}

	public File getConfigFolder() {
		return new File("../Nexus");
	}

	public String getDefaultPrefix() {
		return options.getDefaultPrefix();
	}

	public GameLogic getGameLogic() {
		return gameLogic;
	}

	public GameWorldManager getGameWorldManager() {
		return gameWorldManager;
	}

	public int getCurrentSeason() {
		return getConfig().getInt("current-season");
	}

	public Lang getLang() {
		return lang;
	}

	public EditModeHandler getEditModeHandler() {
		return editModeHandler;
	}

	public GameLoader getGameLoader() {
		return options.getGameLoader();
	}
}
