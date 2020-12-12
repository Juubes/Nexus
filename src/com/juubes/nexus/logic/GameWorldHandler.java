package com.juubes.nexus.logic;

import org.apache.commons.lang.NotImplementedException;
import org.bukkit.World;

import com.juubes.nexus.Nexus;
import com.juubes.nexus.data.AbstractMap;

import lombok.Getter;
import lombok.NonNull;

public class GameWorldHandler {
	private final Nexus pl;

	@Getter
	private World currentWorld;

	@Getter
	private AbstractMap currentMap;

	public GameWorldHandler(Nexus pl) {
		this.pl = pl;
	}

	public void nextMap(@NonNull String requestedMapID) {
		throw new NotImplementedException();
	}

}
