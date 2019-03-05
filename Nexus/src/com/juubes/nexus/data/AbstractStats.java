package com.juubes.nexus.data;

import java.util.UUID;

public abstract class AbstractStats {
	protected UUID uuid;
	protected int season;
	public long playTimeWon;
	public long playTimeLost;
	protected int statsID;

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

	public int getStatsID() {
		return statsID;
	}
}
