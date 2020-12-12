package com.juubes.nexus.data;

import java.util.UUID;

import lombok.AllArgsConstructor;

@AllArgsConstructor
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

}
