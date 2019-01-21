package com.juubes.nexus.playerdata;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import com.juubes.nexus.logic.GameLogic;
import com.juubes.nexus.logic.GameState;
import com.juubes.nexus.logic.Team;

public abstract class AbstractPlayerData {

	protected Player p;
	protected String name;
	protected String prefix;
	protected UUID id;
	protected HashMap<String, Integer> reviewedMaps = new HashMap<>();
	protected int emeralds;
	protected String nick;
	protected Player lastDamager, lastMessager;
	protected Team team;
	protected int killStreak;

	/**
	 * @return a default playerdata
	 */
	public AbstractPlayerData() {
	}

	public abstract void save();

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		if (this.p == null)
			throw new RuntimeException("Playerdata not bound to an online player");
		this.team = team;
		if (team == null)
			GameLogic.sendToSpectate(p);
		else if (GameLogic.getGameState().equals(GameState.RUNNING)) {
			GameLogic.sendPlayerToGame(p, team);
		}
		if (team != null) {
			p.sendMessage("§eOlet nyt tiimissä " + team.getChatColor() + "§l" + team
					.getDisplayName());
		} else {
			// If Nexus isn't ready, make sure everyone is in GM 3
			p.setGameMode(GameMode.SPECTATOR);
			p.teleport(GameLogic.getCurrentGame().getLobby());
			p.sendMessage("§eOlet nyt spectaaja.");
		}
	}

	public static AbstractPlayerData get(Player p) {
		return PlayerDataHandler.get(p);
	}

	public Player getPlayer() {
		return p;
	}

	public String getName() {
		return name;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		if (prefix == null || prefix.equals("null"))
			this.prefix = null;
		else
			this.prefix = prefix;
	}

	public int getEmeralds() {
		return emeralds;
	}

	public void setEmeralds(int emeralds) {
		this.emeralds = emeralds;
	}

	public String getNick() {
		if (nick == null) {
			if (team == null)
				return "§7" + p.getName();
			else
				return team.getChatColor() + p.getName();
		} else {
			if (team == null)
				return "§7" + nick;
			else
				return team.getChatColor() + nick;
		}
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public Player getLastDamager() {
		if (lastDamager != null && !lastDamager.isOnline())
			return null;
		return lastDamager;
	}

	public void setLastDamager(Player lastDamager) {
		this.lastDamager = lastDamager;
	}

	public UUID getID() {
		return id;
	}

	public Player getLastMessager() {
		return lastMessager;
	}

	public int getKillStreak() {
		return killStreak;
	}

	public void setKillStreak(int killStreak) {
		this.killStreak = killStreak;
	}

	public void setLastMessager(Player lastMessager) {
		this.lastMessager = lastMessager;
	}

	/**
	 * Gets the cached current season's stats
	 */
	public abstract AbstractStats getSeasonStats();

	public abstract AbstractStats getSeasonStats(int season);

	public abstract AbstractStats getTotalStats();

	@Override
	public String toString() {
		return "AbstractPlayerData for " + id + ", " + name;
	}
}
