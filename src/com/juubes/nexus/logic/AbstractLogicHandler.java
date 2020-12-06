package com.juubes.nexus.logic;

import java.util.Optional;

import org.apache.commons.lang.NotImplementedException;
import org.bukkit.entity.Player;

import com.juubes.nexus.Nexus;
import com.juubes.nexus.data.AbstractPlayerData;
import com.juubes.nexus.data.AbstractTeam;

/**
 * Handlers game logic like game cycle and players joining teams.
 */
public class AbstractLogicHandler {
	private final Nexus pl;

	public AbstractLogicHandler(Nexus pl) {
		this.pl = pl;
	}

	public void startGame(Optional<String> mapRequest) {
		if (!mapRequest.isPresent())
			throw new NotImplementedException();
	}

	public void togglePause() {
		throw new NotImplementedException();
	}

	public void endGame(AbstractTeam winnerTeam) {
		throw new NotImplementedException();
	}

	public void sendPlayerToGame(Player p) {

	}

	public void sendToSpectate(Player p) {
		AbstractPlayerData pd = pl.getDataHandler().getPlayerData(p.getUniqueId());
		pd.team = null;
	}
}
