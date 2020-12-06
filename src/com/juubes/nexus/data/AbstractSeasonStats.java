package com.juubes.nexus.data;

import java.util.UUID;

public abstract class AbstractSeasonStats {
	public final UUID uuid;
	public final int season;

	public int kills, deaths, wins, losses, longestKillStreak;
	public long playTimeWon, playTimeLost;

	/**
	 * Default constructor. Set's everything to 0.
	 */
	public AbstractSeasonStats(UUID uuid, int season) {
		this.uuid = uuid;
		this.season = season;
	}

	public AbstractSeasonStats(UUID uuid, int season, int kills, int deaths, int wins, int losses,
			int longestKillStreak, long playTimeWon, long playTimeLost) {
		this.uuid = uuid;
		this.season = season;
		this.kills = kills;
		this.deaths = deaths;
		this.wins = wins;
		this.losses = losses;
		this.longestKillStreak = longestKillStreak;
		this.playTimeWon = playTimeWon;
		this.playTimeLost = playTimeLost;
	}

}
