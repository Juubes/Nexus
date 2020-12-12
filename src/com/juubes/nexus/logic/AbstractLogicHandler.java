package com.juubes.nexus.logic;

import java.util.Optional;

import org.apache.commons.lang.NotImplementedException;
import org.bukkit.entity.Player;

import com.juubes.nexus.Nexus;
import com.juubes.nexus.data.AbstractPlayerData;
import com.juubes.nexus.data.AbstractTeam;

import lombok.Getter;

/**
 * Handlers game logic like game cycle and players joining teams.
 */
public class AbstractLogicHandler {
	private final Nexus pl;

	@Getter
	private GameState gameState;

	public AbstractLogicHandler(Nexus pl) {
		this.pl = pl;
	}

	public void startGame(Optional<String> mapRequest) {
		if (!mapRequest.isPresent()) {
			pl.getGameWorldHandler().nextMap(mapRequest.get());
		}

		this.gameState = GameState.RUNNING;
	}

	public void togglePause() {
		throw new NotImplementedException();
	}

	public void endGame(AbstractTeam winnerTeam) {
		throw new NotImplementedException();
	}

	public void sendPlayerToGame(Player p) {
		AbstractPlayerData pd = pl.getDataHandler().getPlayerData(p.getUniqueId());

	}

	public void sendToSpectate(Player p) {
		AbstractPlayerData pd = pl.getDataHandler().getPlayerData(p.getUniqueId());
		pd.team = null;
		// TODO
	}

}
