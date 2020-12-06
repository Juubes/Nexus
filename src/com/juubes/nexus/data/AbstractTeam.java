package com.juubes.nexus.data;

import java.util.Set;

import org.apache.commons.lang.NotImplementedException;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.juubes.nexus.NexusLocation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public abstract class AbstractTeam {
	@Getter
	private final String ID;

	@Setter
	@Getter
	private String displayName;

	@Getter
	@Setter
	private ChatColor teamColor;

	@Setter
	@Getter
	private NexusLocation spawn;

	public Set<Player> getPlayers() {
		throw new NotImplementedException();
	}
}
