package com.juubes.nexus.data;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public abstract class AbstractPlayerData {
	public final UUID uuid;

	public String lastSeenName, prefix, nick;
	public Optional<UUID> lastDamager, lastMessager;
	public AbstractTeam team;
	public int emeralds, killStreak;
	public boolean autoJoin;
	public double eloRating;

	/**
	 * Maps season number to stats.
	 */
	public final HashMap<Integer, AbstractSeasonStats> seasonStats = new HashMap<>(5);

	public AbstractPlayerData(UUID uuid, String lastSeenName, int emeralds, String prefix, String nick, int killStreak,
			double eloRating) {
		this.uuid = uuid;
		this.lastSeenName = lastSeenName;
		this.prefix = prefix;
		this.nick = nick;
		this.emeralds = emeralds;
		this.killStreak = killStreak;
		this.eloRating = eloRating;
	}
}
