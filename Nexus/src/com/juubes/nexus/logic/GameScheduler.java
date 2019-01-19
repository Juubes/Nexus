package com.juubes.nexus.logic;

import org.bukkit.Bukkit;

import com.juubes.nexus.Nexus;

public class GameScheduler {
	private static short startGameCounter = 0;
	private static short changeMapCounter = 0;
	private static boolean started = false;

	public static void start() {
		if (started)
			return;
		Bukkit.getScheduler().runTaskTimer(Nexus.getPlugin(), () -> {

			if (startGameCounter > -1) {

				if (startGameCounter == 0) {
					startGame();
				}
				startGameCounter--;
			}

			if (changeMapCounter > -1) {
				if (changeMapCounter == 0) {
					changeMap();
				}
			}
		}, 20, 20);
		started = true;
	}

	/**
	 * Gets triggered by the scheduler when counter hits 0
	 */
	private static void startGame() {

	}

	/**
	 * Gets triggered by the scheduler when counter hits 0
	 */
	private static void changeMap() {

	}

	public static void startChangeMapTimer(short seconds) {

	}

	public static void startBeginGameTimer(short seconds) {

	}
}
