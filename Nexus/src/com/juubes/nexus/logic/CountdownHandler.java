package com.juubes.nexus.logic;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.juubes.nexus.Nexus;
import com.juubes.nexus.data.AbstractPlayerData;
import com.juubes.nexus.data.PlayerDataHandler;

public class CountdownHandler {
	private final Nexus nexus;

	private int startGame = -1;
	private int changeMap = 0;

	public CountdownHandler(Nexus nexus) {
		this.nexus = nexus;
	}

	
	public void startScheduling() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(nexus, () -> {
			if (Bukkit.getOnlinePlayers().size() == 0)
				return;
			if (changeMap > 0) {
				if (changeMap < 4 || changeMap == 10 || changeMap == 20 || changeMap == 30 || changeMap % 60 == 0)
					Bukkit.broadcastMessage("�eVaihdetaan mappia " + changeMap + " sekunnissa.");
			}
			if (startGame > 0) {
				if (startGame < 4 || startGame == 10 || startGame == 20 || startGame == 30 || startGame % 60 == 0)
					Bukkit.broadcastMessage("�ePeli alkaa " + startGame + " sekunnissa.");
			}
			if (changeMap > 0) {
				changeMap--;
			} else if (changeMap == 0) {
				nexus.getGameLogic().loadNextGame();
				Bukkit.broadcastMessage("�eLiity peliin komennolla /join");
				changeMap = -1;
			}

			if (startGame > 0) {
				startGame--;
			} else if (startGame == 0) {
				for (Team team : nexus.getGameLogic().getCurrentGame().getTeams()) {
					if (team.getPlayers().size() == 0) {
						Bukkit.broadcastMessage("�ePeliss� ei ole tarpeeksi pelaajia.");
						startGame = 30;
						return;
					}
				}
				nexus.getGameLogic().startGame();
				startGame = -1;
			} else if (startGame == 10) {
				for (Player p : Bukkit.getOnlinePlayers()) {
					if (p.getWorld() != nexus.getGameLogic().getCurrentGame().getWorld())
						continue;
					AbstractPlayerData pd = PlayerDataHandler.get(p);
					if (pd.isAutoJoin()) {
						pd.setTeam(nexus.getGameLogic().getCurrentGame().getSmallestTeam());
						p.sendMessage("�eSinut on automaattisesti lis�tty tiimiin " + pd.getTeam().getDisplayName()
								+ "�e, koska liikuit!");
					}
				}
			}
		}, 0, 20);
			
	}

	public void startGameCountdown(int seconds) {
		startGame = seconds;
	}

	public void changeMapCountdown(int seconds) {
		changeMap = seconds;
	}

	public void stopStartGameCountdown() {
		startGame = -1;
	}

	public void stopChangeMapCountdown() {
		changeMap = -1;
	}
}
