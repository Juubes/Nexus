package com.juubes.nexus;

import com.juubes.nexus.data.AbstractTotalStats;

public class TopListEntryTotal {
	public String name;
	public AbstractTotalStats stats;

	public TopListEntryTotal(String lastSeenName, AbstractTotalStats stats) {
		this.name = lastSeenName;
		this.stats = stats;
	}
}
