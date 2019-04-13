package com.juubes.nexus.data;

import java.util.HashMap;
import java.util.UUID;

public class AbstractTotalStats {
//	kills, deaths, monuments, wins, losses, winTime, lossTime, biggestKillStreak;

	protected final HashMap<Integer, ? extends AbstractStats> allStats;
	protected final UUID uuid;

	public AbstractTotalStats(UUID uuid, HashMap<Integer, ? extends AbstractStats> seasonStats) {
		this.uuid = uuid;
		this.allStats = seasonStats;
	}

	public int getKills() {
		int sum = 0;
		for (AbstractStats stats : allStats.values()) {
			sum += stats.kills;
		}
		return sum;
	}

	public int getBiggestKillStreak() {
		int biggest = 0;
		for (AbstractStats stats : allStats.values()) {
			biggest = Math.max(biggest, stats.biggestKillStreak);
		}
		return biggest;
	}

	public int getDeaths() {
		int sum = 0;
		for (AbstractStats stats : allStats.values()) {
			sum += stats.deaths;
		}
		return sum;
	}

	public int getWins() {
		int sum = 0;
		for (AbstractStats stats : allStats.values()) {
			sum += stats.wins;
		}
		return sum;
	}

	public int getLosses() {
		int sum = 0;
		for (AbstractStats stats : allStats.values()) {
			sum += stats.losses;
		}
		return sum;
	}

	public UUID getUUID() {
		return uuid;
	}

	public HashMap<Integer, ? extends AbstractStats> getAllStats() {
		return allStats;
	}
}
