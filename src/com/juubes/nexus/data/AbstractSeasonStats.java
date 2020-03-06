package com.juubes.nexus.data;

import java.util.UUID;

public abstract class AbstractSeasonStats {
	private final UUID uuid;
	private final int season;
	private final int statsID;

	public int kills, deaths, wins, losses, longestKillStreak;
	public long playTimeWon;
	public long playTimeLost;

	/**
	 * Generates zero stats with the StatsID, UUID and season.
	 */
	public AbstractSeasonStats(int statsID, UUID uuid, int season) {
		this(statsID, uuid, season, 0, 0, 0, 0, 0, 0, 0);
	}

	public AbstractSeasonStats(int statsID, UUID uuid, int season, int kills, int deaths, int wins, int losses,
			long playTimeWon2, long playTimeLost2, int longestKillStreak) {
		this.statsID = statsID;
		this.season = season;
		this.uuid = uuid;

		this.kills = kills;
		this.deaths = deaths;
		this.wins = wins;
		this.losses = losses;
		this.playTimeWon = playTimeWon2;
		this.playTimeLost = playTimeLost2;
		this.longestKillStreak = longestKillStreak;
	}

	public UUID getUUID() {
		return uuid;
	}

	public int getSeason() {
		return season;
	}

	public int getSeasonStatsID() {
		return statsID;
	}

}
