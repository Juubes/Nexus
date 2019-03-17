package com.juubes.nexus.data;

import java.util.UUID;

public abstract class AbstractStats {
	private final UUID uuid;
	private final int season;
	public long playTimeWon;
	public long playTimeLost;
	public int statsID;

	public AbstractStats(int statsID, UUID uuid, int season) {
		this.statsID = statsID;
		this.season = season;
		this.uuid = uuid;
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
