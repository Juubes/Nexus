package com.juubes.nexus.logic;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import com.juubes.nexus.events.StartCountdownEvent;
import com.juubes.nexus.playerdata.AbstractPlayerData;

public class GameLogic {
	private static Game currentGame;
	private static GameState gameState = GameState.STOPPED;
	private static CountdownHandler countdownHandler;

	public static void init() {
		countdownHandler = new CountdownHandler();
		loadNextGame();
	}

	public static Game getCurrentGame() {
		return currentGame;
	}

	public static void loadNextGame() {
		loadNextGame(null);
	}

	public static void loadNextGame(String request) {
		currentGame = new Game(request);
		countdownHandler.startGameCountdown(20);
		gameState = GameState.COUNTDOWN;

		for (Player p : Bukkit.getOnlinePlayers()) {
			AbstractPlayerData pd = AbstractPlayerData.get(p);
			pd.setTeam(null);
		}

		Bukkit.getPluginManager().callEvent(new StartCountdownEvent(GameWorldManager
				.getCurrentMapID()));
		countdownHandler.stopChangeMapCountdown();
	}

	public static String getCurrentMapID() {
		return GameWorldManager.getCurrentMapID();
	}

	private static GameState pausedGameState;

	public static boolean isPaused() {
		return gameState == GameState.PAUSED;
	}

	public static GameState getGameState() {
		return gameState;
	}

	public static void restartGame() {
		gameState = GameState.COUNTDOWN;
		countdownHandler.changeMapCountdown(30);
	}

	public static void startGame() {
		gameState = GameState.RUNNING;

		for (Player p : Bukkit.getOnlinePlayers()) {
			AbstractPlayerData pd = AbstractPlayerData.get(p);
			if (pd.getTeam() != null && p.getWorld().equals(getCurrentGame().getWorld()))
				sendPlayerToGame(p, pd.getTeam());
		}
		countdownHandler.stopStartGameCountdown();
	}

	public static void sendToSpectate(Player p) {
		AbstractPlayerData pd = AbstractPlayerData.get(p);
		Location lobby = GameLogic.getCurrentGame().getLobby();
		if (lobby != null) {
			p.teleport(lobby);
		} else {
			System.err.println("Lobby null for map " + GameLogic.getCurrentGame()
					.getMapDisplayName());
			p.teleport(new Location(GameLogic.getCurrentGame().getWorld(), 0, 100, 0));
		}
		p.setGameMode(GameMode.SPECTATOR);
		p.getInventory().clear();

		// Handle appropriate nametag colours
		p.setDisplayName(pd.getNick());
		p.setPlayerListName("§8[" + ChatColor.translateAlternateColorCodes('&', pd.getPrefix())
				+ "§8] " + pd.getNick());
		p.setCustomName(pd.getNick());
		p.setCustomNameVisible(false);

	}

	public static void sendPlayerToGame(Player p, Team team) {
		AbstractPlayerData pd = AbstractPlayerData.get(p);
		// Reset properties and teleport to spawn
		p.setFallDistance(0);
		p.setMaxHealth(20);
		p.setHealth(p.getMaxHealth());
		p.setFoodLevel(20);
		p.teleport(team.getSpawn());
		p.setGameMode(GameMode.SURVIVAL);

		p.getInventory().setContents(GameLogic.getCurrentGame().getKit());
		p.getInventory().setArmorContents(getArmorForTeam(p, pd.getTeam()));

		updateNameTag(p);
	}

	private static ItemStack[] getArmorForTeam(Player p, Team team) {
		ItemStack[] items = new ItemStack[4];

		ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
		LeatherArmorMeta meta = (LeatherArmorMeta) helmet.getItemMeta();
		meta.setColor(team.getLeatherColor());
		helmet.setItemMeta(meta);
		items[3] = helmet;

		ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
		LeatherArmorMeta chestMeta = (LeatherArmorMeta) chestplate.getItemMeta();
		chestMeta.setColor(team.getLeatherColor());
		chestplate.setItemMeta(chestMeta);
		items[2] = chestplate;

		ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
		LeatherArmorMeta legMeta = (LeatherArmorMeta) leggings.getItemMeta();
		legMeta.setColor(team.getLeatherColor());
		leggings.setItemMeta(legMeta);
		items[1] = leggings;

		ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
		LeatherArmorMeta bootMeta = (LeatherArmorMeta) boots.getItemMeta();
		bootMeta.setColor(team.getLeatherColor());
		boots.setItemMeta(bootMeta);
		items[0] = boots;
		return items;
	}

	public static boolean isReadyToPlay() {
		if (getCurrentGame() == null) {
			System.err.println("Current game == null");
			return false;
		}
		if (getCurrentGame().getLobby() == null) {
			System.err.println("Lobby == null");
			return false;
		}
		if (getCurrentGame().getTeams() == null) {
			System.err.println("Teams == null");
			return false;
		}
		if (getCurrentGame().getTeams().length < 2) {
			System.err.println("Teams.size() == " + getCurrentGame().getTeams().length);
			return false;
		}

		for (Team team : getCurrentGame().getTeams()) {
			if (team.getSpawn() == null) {
				System.err.println(team.getID() + ".spawn == null");
				return false;
			}
		}

		if (getCurrentGame().getWorld() == null) {
			System.err.println("World == null");
			return false;
		}
		if (getCurrentGame().getKit() == null) {
			System.err.println("Kit == null");
			return false;
		}
		if (getCurrentGame().getMapDisplayName() == null) {
			System.err.println("Map displayname == null");
			return false;
		}
		return true;
	}

	public static void togglePause() {
		if (gameState != GameState.RUNNING && gameState != GameState.COUNTDOWN)
			throw new IllegalStateException("GameState is " + gameState.name());

		if (gameState != GameState.PAUSED) {
			pausedGameState = gameState;
			gameState = GameState.PAUSED;
		} else {
			gameState = pausedGameState;
		}

		boolean paused = gameState == GameState.PAUSED;
		if (paused) {
			for (Player p : Bukkit.getOnlinePlayers()) {
				p.setGameMode(GameMode.SPECTATOR);
				p.sendMessage(
						"§ePeli on pysäytetty. Kun peli jatkuu, sinut teleportataan spawnille.");
			}
		} else {
			for (Player p : Bukkit.getOnlinePlayers()) {
				AbstractPlayerData pd = AbstractPlayerData.get(p);
				if (pd.getTeam() != null) {
					p.teleport(pd.getTeam().getSpawn());
					p.setGameMode(GameMode.SURVIVAL);
					p.sendMessage("§ePeli ei ole enää pysäytetty. Teleportattu spawnille.");
				}
			}
		}
	}

	public static CountdownHandler getCountdownHandler() {
		return countdownHandler;
	}

	public static void updateNameTag(Player p) {
		AbstractPlayerData pd = AbstractPlayerData.get(p);
		p.setDisplayName(pd.getNick());
		p.setPlayerListName("§8[" + ChatColor.translateAlternateColorCodes('&', pd.getPrefix())
				+ "§8] " + pd.getNick());
		p.setCustomName(pd.getNick());
		p.setCustomNameVisible(true);
	}

}
