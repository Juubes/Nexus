package com.juubes.nexus;

import java.util.UUID;

import com.juubes.nexus.data.AbstractSeasonStats;

public class TopListEntry {
	public UUID uuid;
	public String name;
	public AbstractSeasonStats stats;

	public TopListEntry(UUID uuid, String lastSeenName, AbstractSeasonStats stats) {
		this.uuid = uuid;
		this.name = lastSeenName;
		this.stats = stats;
	}
}
