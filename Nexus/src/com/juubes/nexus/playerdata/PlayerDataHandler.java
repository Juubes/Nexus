package com.juubes.nexus.playerdata;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.Player;

public class PlayerDataHandler {
	private static HashSet<AbstractPlayerData> loadedData = new HashSet<>();

	public static AbstractPlayerData get(Player p) {
		if (p == null)
			throw new NullPointerException("Player can't be null.");
		for (AbstractPlayerData pd : loadedData) {
			if (pd.getPlayer().equals(p))
				return pd;
		}
		throw new RuntimeException("Playerdata for player " + p.getName() + " isn't loaded.");
	}

	public static void setLoaded(AbstractPlayerData data) {
		loadedData.add(data);
	}

	public static void unload(AbstractPlayerData data) {
		data.save();
		loadedData.remove(data);
	}

	public static Set<AbstractPlayerData> getLoadedData() {
		return loadedData;
	}
}
