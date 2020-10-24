package com.juubes.nexus.logic;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import com.juubes.nexus.Nexus;
import com.juubes.nexus.data.AbstractPlayerData;
import com.juubes.nexus.events.StartCountdownEvent;

public class GameLogic {
	private final Nexus nexus;
	private final CountdownHandler countdownHandler;

	private Game currentGame;
	private GameState gameState;

	public GameLogic(Nexus nexus) {
		this.nexus = nexus;
		this.countdownHandler = new CountdownHandler(nexus);
		this.gameState = GameState.RUNNING;
	}

	public Game getCurrentGame() {
		return currentGame;
	}

	public void loadNextGame() {
		this.loadNextGame(null);
	}

	public void loadNextGame(String request) {
		currentGame = nexus.getGameLoader().loadGame(request);

		if (currentGame == null)
			throw new NullPointerException();

		countdownHandler.startGameCountdown(20);
		gameState = GameState.COUNTDOWN;

		for (Player p : Bukkit.getOnlinePlayers()) {
			AbstractPlayerData pd = nexus.getDatabaseManager().getPlayerData(p.getUniqueId());
			pd.setTeam(null);
			pd.setLastDamager(null);
		}
		Bukkit.getPluginManager().callEvent(new StartCountdownEvent(nexus.getGameWorldManager().getCurrentMapID()));
		countdownHandler.stopChangeMapCountdown();
	}

	public String getCurrentMapID() {
		return nexus.getGameWorldManager().getCurrentMapID();
	}

	private GameState pausedGameState;

	public boolean isPaused() {
		return gameState == GameState.PAUSED;
	}

	public GameState getGameState() {
		return gameState;
	}

	public void restartGame() {
		gameState = GameState.COUNTDOWN;
		countdownHandler.changeMapCountdown(30);
	}

	public void startGame() {
		gameState = GameState.RUNNING;

		for (Player p : Bukkit.getOnlinePlayers()) {
			AbstractPlayerData pd = nexus.getDatabaseManager().getPlayerData(p.getUniqueId());
			if (pd.getTeam() != null && p.getWorld().equals(getCurrentGame().getWorld()))
				sendPlayerToGame(p, pd.getTeam());
			// TODO: this doesn't belong in Nexus
			p.sendMessage("§eDestroy the Monument - §btuhoa obsidiaanit");
			p.sendMessage("§eEnsimmäinen tiimi, joka tuhoaa vihollisen monumentit, voittaa.");
		}
		countdownHandler.stopStartGameCountdown();
	}

	public void sendToSpectate(Player p) {
		if (p.getWorld() != currentGame.getWorld())
			return;

		AbstractPlayerData pd = nexus.getDatabaseManager().getPlayerData(p.getUniqueId());
		Location lobby = getCurrentGame().getLobby().toLocation(nexus.getGameLogic().currentGame.getWorld());
		if (lobby != null) {
			p.teleport(lobby);
		} else {
			System.err.println("Lobby null for map " + getCurrentGame().getMapDisplayName());
			p.teleport(new Location(getCurrentGame().getWorld(), 0, 100, 0));
		}
		p.setGameMode(GameMode.SPECTATOR);
		p.getInventory().clear();

		// Handle appropriate nametag colours
		p.setDisplayName(pd.getNick());
		p.setCustomName(pd.getNick());
		p.setCustomNameVisible(false);

		if (pd.getPrefix() != null)
			p.setPlayerListName("§8[" + ChatColor.translateAlternateColorCodes('&', pd.getPrefix()) + "§8] " + pd
					.getNick());
		else
			p.setPlayerListName(pd.getNick());
	}

	public void sendPlayerToGame(Player p, Team team) {
		AbstractPlayerData pd = nexus.getDatabaseManager().getPlayerData(p.getUniqueId());
		// Reset properties and teleport to spawn
		p.setFallDistance(0);
		p.setMaxHealth(20);
		p.setHealth(p.getMaxHealth());
		p.setFoodLevel(20);
		p.teleport(team.getSpawn().toLocation(nexus.getGameLogic().currentGame.getWorld()));
		p.setGameMode(GameMode.SURVIVAL);

		p.getInventory().setContents(getCurrentGame().getKit());
		p.getInventory().setArmorContents(getArmorForTeam(p, pd.getTeam()));

		for (Player player : Bukkit.getOnlinePlayers()) {
			player.hidePlayer(p);
		}
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.showPlayer(p);
		}

		updateNameTag(p);
	}

	private ItemStack[] getArmorForTeam(Player p, Team team) {
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

	public boolean isReadyToPlay() {
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

	public void togglePause() {
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
				p.sendMessage("§ePeli on pysäytetty. Kun peli jatkuu, sinut teleportataan spawnille.");
			}
		} else {
			for (Player p : Bukkit.getOnlinePlayers()) {
				AbstractPlayerData pd = nexus.getDatabaseManager().getPlayerData(p.getUniqueId());
				if (pd.getTeam() != null && gameState == GameState.RUNNING) {
					p.teleport(pd.getTeam().getSpawn().toLocation(p.getWorld()));
					p.setGameMode(GameMode.SURVIVAL);
					p.sendMessage("§ePeli ei ole enää pysäytetty. Teleportattu spawnille.");
				} else {
					p.sendMessage("§ePeli ei ole enää pysäytetty.");
				}
			}
		}
	}

	public CountdownHandler getCountdownHandler() {
		return countdownHandler;
	}

	public void updateNameTag(Player p) {
		AbstractPlayerData pd = nexus.getDatabaseManager().getPlayerData(p.getUniqueId());
		p.setDisplayName(pd.getNick());
		p.setCustomName(pd.getNick());
		p.setCustomNameVisible(true);

		if (pd.getPrefix() != null)
			p.setPlayerListName("§8[" + ChatColor.translateAlternateColorCodes('&', pd.getPrefix()) + "§8] " + pd
					.getNick());
		else
			p.setPlayerListName(pd.getNick());

		if (pd.getTeam() != null)
			nexus.getNameTagColorer().changeNameTag(p, pd.getTeam().getChatColor());
	}
}
