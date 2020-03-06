package com.juubes.nexus.data;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import com.juubes.nexus.Nexus;
import com.juubes.nexus.logic.GameState;
import com.juubes.nexus.logic.Team;

public abstract class AbstractPlayerData {
	protected final Nexus nexus;
	protected final UUID uuid;
	protected final HashMap<String, Integer> reviewedMaps;

	protected String lastSeenName;
	protected String prefix;
	protected int emeralds;
	protected String nick;
	protected Player lastDamager, lastMessager;
	protected Team team;
	protected int killStreak;
	protected boolean autoJoin;

	public AbstractPlayerData(Nexus nexus, UUID uuid, String lastSeenName, String prefix, int emeralds, String nick,
			int killStreak) {
		this.nexus = nexus;
		this.uuid = uuid;
		this.reviewedMaps = new HashMap<>();

		this.lastSeenName = lastSeenName;
		this.prefix = prefix;
		this.emeralds = emeralds;
		this.nick = nick;
		this.killStreak = killStreak;
		this.autoJoin = false;
	}

	public abstract void save();

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		if (!Bukkit.getPlayer(uuid).isOnline())
			throw new NullPointerException("Playerdata for " + lastSeenName
					+ " couldn't be bound to an online player.");

		Player p = Bukkit.getPlayer(uuid);
		this.team = team;
		if (team == null)
			nexus.getGameLogic().sendToSpectate(p);
		else {
			if (nexus.getGameLogic().getGameState().equals(GameState.RUNNING))
				nexus.getGameLogic().sendPlayerToGame(p, team);
			else {
				nexus.getGameLogic().updateNameTag(p);
			}
		}

		if (team != null) {
			p.sendMessage("§eOlet nyt tiimissä " + team.getChatColor() + "§l" + team.getDisplayName());
		} else {
			// If Nexus isn't ready, make sure everyone is in GM 3
			p.setGameMode(GameMode.SPECTATOR);
			p.teleport(nexus.getGameLogic().getCurrentGame().getLobby());
			p.sendMessage("§eOlet nyt spectaaja.");
			p.sendMessage("§eVoit liittyä komennolla /join");
			nexus.getGameLogic().updateNameTag(p);
		}
	}

	public String getLastSeenName() {
		return lastSeenName;
	}

	public String getPrefix() {
		if (prefix == null)
			return Nexus.getAPI().getDefaultPrefix();
		return prefix;
	}

	public String getRawPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	/**
	 * @return null if no nick
	 */
	public String getRawNick() {
		return nick;
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
				return "§7" + getLastSeenName();
			else
				return team.getChatColor() + getLastSeenName();
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
		if (lastDamager == null)
			return null;
		if (!lastDamager.isOnline())
			return null;
		return lastDamager;
	}

	public void setLastDamager(Player lastDamager) {
		this.lastDamager = lastDamager;
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

	public UUID getUUID() {
		return uuid;
	}

	public void setAutoJoin(boolean b) {
		this.autoJoin = b;
	}

	/**
	 * Gets the cached current season's stats
	 */
	public abstract AbstractSeasonStats getSeasonStats();

	public abstract AbstractSeasonStats getSeasonStats(int season);

	public abstract AbstractTotalStats getTotalStats();

	@Override
	public String toString() {
		return "AbstractPlayerData for " + uuid + ", " + lastSeenName;
	}

	public boolean isAutoJoin() {
		return autoJoin;
	}
}
