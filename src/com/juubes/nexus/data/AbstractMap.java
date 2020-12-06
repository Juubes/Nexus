package com.juubes.nexus.data;

import java.util.LinkedHashSet;

import com.juubes.nexus.NexusLocation;

import lombok.Getter;
import lombok.Setter;

public class AbstractMap {
	@Getter
	private final String id;

	@Getter
	@Setter
	private String displayName;

	@Getter
	@Setter
	private NexusLocation lobby;

	@Getter
	@Setter
	private int ticks;

	@Getter
	private final LinkedHashSet<AbstractTeam> teams = new LinkedHashSet<>();

	public AbstractMap(String id, String displayName, NexusLocation lobby, int ticks) {
		this.id = id;
		this.displayName = displayName;
		this.lobby = lobby;
		this.ticks = ticks;
	}

}
