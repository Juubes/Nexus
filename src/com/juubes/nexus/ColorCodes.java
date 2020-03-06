package com.juubes.nexus;

import org.bukkit.ChatColor;

public enum ColorCodes {

    AQUA, BLACK, BLUE, DARKAQUA, DARKBLUE, DARKGRAY, DARKGREEN, DARKRED, DARK_PURPLE, GOLD, GRAY, GREEN, LIGHTPURPLE, RED, WHITE, YELLOW;

    public static ChatColor fromColorCode(String str) {
        int index = Integer.valueOf(str, 16);
        return ChatColor.valueOf(values()[index].name());
    }
}