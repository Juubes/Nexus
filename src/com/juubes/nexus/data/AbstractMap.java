package com.juubes.nexus.data;

import java.util.LinkedHashSet;

import com.juubes.nexus.NexusLocation;

public class AbstractMap {
	public final String id;
	public String displayName;
	public NexusLocation lobby;
	public int ticks;

	public final LinkedHashSet<? extends AbstractTeam> teams = new LinkedHashSet<>();

	public AbstractMap(String id, String displayName, NexusLocation lobby, int ticks) {
		this.id = id;
		this.displayName = displayName;
		this.lobby = lobby;
		this.ticks = ticks;
	}

}
