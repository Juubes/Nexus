package com.juubes.nexus;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
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
import com.juubes.nexus.logic.GameLogic;
import com.juubes.nexus.logic.GameWorldManager;
import com.juubes.nexus.logic.PauseCommand;

public class Nexus extends JavaPlugin {
	public static final Integer CURRENT_SEASON = 5;
	private static InitOptions options;

	@Override
	public void onEnable() {
		JoinCommand njc = new JoinCommand();
		getCommand("join").setExecutor(njc);
		getCommand("spec").setExecutor(new SpectateCommand());

		PauseCommand nexusCommands = new PauseCommand();
		getCommand("pause").setExecutor(nexusCommands);

		GetposCommand gpcmd = new GetposCommand();
		WorldsCommand wcmd = new WorldsCommand();
		getCommand("getpos").setExecutor(gpcmd);
		getCommand("world").setExecutor(wcmd);
		getCommand("worlds").setExecutor(wcmd);

		StatsCommand scmd = new StatsCommand();
		getCommand("stats").setExecutor(scmd);
		getCommand("setprefix").setExecutor(new SetPrefixCommand());

		NextmapCommand nmCmd = new NextmapCommand();
		getCommand("nextmap").setExecutor(nmCmd);
		getCommand("start").setExecutor(new StartCommand());
		getCommand("gametime").setExecutor(new PlayTimeCommand());

		getCommand("savemap").setExecutor(new SaveMapCommand());
		getCommand("editmode").setExecutor(new EditModeHandler());
		getCommand("setlobby").setExecutor(new SetLobbyCommand());
		getCommand("addteam").setExecutor(new AddTeamCommand());
		getCommand("removeteam").setExecutor(new RemoveTeamCommand());
		getCommand("setteamspawn").setExecutor(new SetTeamSpawnCommand());

		getCommand("savekit").setExecutor(new SaveKitCommand());

		saveDefaultKitFile();
	}

	public static void init(InitOptions options) {
		Nexus.getPlugin().saveDefaultConfig();

		Nexus.options = options;
		GameWorldManager.init(Arrays.asList(options.getMapIDs()));
		GameLogic.init();
	}

	public static AbstractDatabaseManager getDatabaseManager() {
		return options.getDatabaseManager();
	}

	public static Plugin getPlugin() {
		return Bukkit.getPluginManager().getPlugin("Nexus");
	}

	public static boolean ready() {
		if (Nexus.options.getDatabaseManager() == null) {
			System.out.println("Setup incomplete: 1");
			return false;
		}
		if (Nexus.options.getMapIDs() == null) {
			System.out.println("Setup incomplete: 2");
			return false;
		}
		if (!GameLogic.isReadyToPlay()) {
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
				YamlConfiguration.loadConfiguration(new InputStreamReader(getResource(
						"config.yml"))).save(newConf);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveDefaultKitFile() {
		try {
			File newConf = new File(getConfigFolder(), "kits.yml");
			if (!newConf.exists())
				YamlConfiguration.loadConfiguration(new InputStreamReader(getResource("kits.yml")))
						.save(newConf);
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

	public static File getConfigFolder() {
		return new File("../Nexus");
	}
}
