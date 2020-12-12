package com.juubes.nexus.data;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public abstract class AbstractPlayerData {
	public final UUID uuid;

	public String lastSeenName, prefix;
	public Optional<UUID> lastDamager, lastMessager;
	public AbstractTeam team;
	public int emeralds, killStreak;
	public boolean autoJoin;
	public double eloRating;

	/**
	 * Maps season number to stats.
	 */
	protected final HashMap<Integer, ? extends AbstractSeasonStats> seasonStats;

	public AbstractPlayerData(UUID uuid, String lastSeenName, int emeralds, String prefix, int killStreak,
			double eloRating, HashMap<Integer, ? extends AbstractSeasonStats> seasonStats) {
		this.uuid = uuid;
		this.lastSeenName = lastSeenName;
		this.prefix = prefix;
		this.emeralds = emeralds;
		this.killStreak = killStreak;
		this.eloRating = eloRating;
		this.seasonStats = seasonStats;
	}
}
