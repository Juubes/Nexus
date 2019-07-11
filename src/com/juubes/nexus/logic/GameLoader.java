package com.juubes.nexus.logic;

import com.juubes.nexus.Nexus;

public abstract class GameLoader {
	protected final Nexus nexus;

	public GameLoader(Nexus nexus) {
		this.nexus = nexus;
	}

	/**
	 * Is called before the players are teleported to the new gameworld.
	 */
	public abstract Game loadGame(String request);
}
