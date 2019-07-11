package com.juubes.nexus.data;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.UUID;

public class AbstractTotalStats {
	protected final HashMap<Integer, ? extends AbstractSeasonStats> allStats;
	protected final UUID uuid;

	public AbstractTotalStats(UUID uuid, HashMap<Integer, ? extends AbstractSeasonStats> seasonStats) {
		this.uuid = uuid;
		this.allStats = seasonStats;
	}

	public int getKills() {
		int sum = 0;
		for (AbstractSeasonStats stats : allStats.values()) {
			sum += stats.kills;
		}
		return sum;
	}

	public int getBiggestKillStreak() {
		int biggest = 0;
		for (AbstractSeasonStats stats : allStats.values()) {
			biggest = Math.max(biggest, stats.longestKillStreak);
		}
		return biggest;
	}

	public int getDeaths() {
		int sum = 0;
		for (AbstractSeasonStats stats : allStats.values()) {
			sum += stats.deaths;
		}
		return sum;
	}

	public int getWins() {
		int sum = 0;
		for (AbstractSeasonStats stats : allStats.values()) {
			sum += stats.wins;
		}
		return sum;
	}

	public int getLosses() {
		int sum = 0;
		for (AbstractSeasonStats stats : allStats.values()) {
			sum += stats.losses;
		}
		return sum;
	}

	public long getPlayTimeWon() {
		int sum = 0;
		for (AbstractSeasonStats stats : allStats.values()) {
			sum += stats.playTimeWon;
		}
		return sum;
	}

	public long getPlayTimeLost() {
		int sum = 0;
		for (AbstractSeasonStats stats : allStats.values()) {
			sum += stats.playTimeLost;
		}
		return sum;
	}

	public double getKDRatio() {
		NumberFormat f = NumberFormat.getInstance();
		f.setMaximumFractionDigits(2);
		f.setMinimumFractionDigits(2);

		int kills = this.getKills();
		int deaths = this.getDeaths();

		String KD = f.format((double) kills / (double) deaths);
		if (kills < 1 || deaths < 1)
			KD = "0.00";
		return Double.parseDouble(KD);
	}

	public UUID getUUID() {
		return uuid;
	}

}
