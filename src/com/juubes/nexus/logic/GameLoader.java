package com.juubes.nexus.logic;

import com.juubes.nexus.Nexus;

public abstract class GameLoader {
	protected final Nexus nexus;
	
	public GameLoader(Nexus nexus) {
		this.nexus = nexus;
	}
	
	public abstract Game loadGame(String request);
}
