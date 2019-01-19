package com.juubes.nexus;

import org.bukkit.ChatColor;

public enum ColorCodes {

	BLACK, DARKBLUE, DARKGREEN, DARKAQUA, DARKRED, DARKPURPLE, GOLD, GRAY, DARKGRAY, BLUE, GREEN, AQUA, RED, LIGHTPURPLE, YELLOW, WHITE;

	public static ChatColor fromColorCode(String str) {
		int index = Integer.valueOf(str, 16);
		return ChatColor.valueOf(values()[index].name());
	}
}