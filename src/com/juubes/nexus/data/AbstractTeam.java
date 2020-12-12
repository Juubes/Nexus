package com.juubes.nexus.data;

import org.bukkit.ChatColor;

import com.juubes.nexus.NexusLocation;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class AbstractTeam {
	public final String ID;

	public String displayName;

	public ChatColor teamColor;

	public NexusLocation spawn;
}
