package com.juubes.nexus.logic;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.google.common.base.Preconditions;
import com.juubes.nexus.Nexus;

public class Team {
	private final Nexus nexus;

	private ChatColor teamColor;
	private String displayName;
	private Location spawn;
	private String ID;

	/**
	 * @param ID
	 *            is the 'short name' for the team as identified in the config
	 *            files.
	 * @param displayName
	 *            is the display name for the team - without color
	 * @param teamColor
	 *            give the color
	 *
	 */
	public Team(Nexus nexus, String ID, ChatColor teamColor, String displayName, Location spawn) {
		this.nexus = nexus;
		this.ID = Preconditions.checkNotNull(ID);
		this.teamColor = Preconditions.checkNotNull(teamColor);
		this.displayName = Preconditions.checkNotNull(displayName);
		this.spawn = spawn;
	}

	public String getDisplayName() {
		return teamColor.toString() + ChatColor.BOLD + displayName;
	}

	public ChatColor getChatColor() {
		return teamColor;
	}

	public List<Player> getPlayers() {
		List<Player> players = new ArrayList<>();
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (nexus.getDatabaseManager().getPlayerData(player).getTeam() == this)
				players.add(player);
		}
		return players;
	}

	public Location getSpawn() {
		return this.spawn;
	}

	public String getID() {
		return this.ID;
	}

	@Override
	public String toString() {
		return this.getDisplayName() + "�r";
	}

	public Color getLeatherColor() {
		switch (this.teamColor) {
		case AQUA:
			return Color.AQUA;
		case BLACK:
			return Color.BLACK;
		case BLUE:
			return Color.BLUE;
		case DARK_AQUA:
			return Color.AQUA;
		case DARK_BLUE:
			return Color.NAVY;
		case DARK_GRAY:
			return Color.GRAY;
		case DARK_GREEN:
			return Color.OLIVE;
		case DARK_PURPLE:
			return Color.PURPLE;
		case DARK_RED:
			return Color.MAROON;
		case GOLD:
			return Color.YELLOW;
		case GRAY:
			return Color.GRAY;
		case GREEN:
			return Color.LIME;
		case LIGHT_PURPLE:
			return Color.PURPLE;
		case RED:
			return Color.RED;
		case WHITE:
			return Color.WHITE;
		case YELLOW:
			return Color.YELLOW;
		default:
			break;
		}
		return null;
	}
}
