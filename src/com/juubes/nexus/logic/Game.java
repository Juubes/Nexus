package com.juubes.nexus.logic;

import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import com.juubes.nexus.Nexus;
import com.juubes.nexus.NexusLocation;

public class Game {
	private ItemStack[] kit;
	private Team[] teams;
	private NexusLocation lobby;
	private long startTime;
	private World world;
	private String mapDisplayName;
	private String mapID;
	private boolean ended;

	public Game(Nexus nexus, String mapRequest) {
		this.ended = false;
		this.world = nexus.getGameWorldManager().loadNextWorld(mapRequest);
		this.world.setTime(0);

		this.mapID = nexus.getGameWorldManager().getCurrentMapID();

		this.mapDisplayName = nexus.getDatabaseManager().getMapDisplayName(mapID);
		this.lobby = nexus.getDatabaseManager().getLobbyForMap(mapID);
		this.teams = nexus.getDatabaseManager().getTeams(mapID);
		this.kit = nexus.getDatabaseManager().getKitForGame(mapID).getContents();

		this.startTime = System.currentTimeMillis();
	}

	public Team[] getTeams() {
		return this.teams;
	}

	public World getWorld() {
		return world;
	}

	public long getStartTime() {
		return startTime;
	}

	public NexusLocation getLobby() {
		if (lobby == null)
			return new NexusLocation(0, 100, 0);
		return lobby;
	}

	public Team getTeam(String teamID) {
		for (int i = 0; i < teams.length; i++) {
			if (teams[i].getID().equalsIgnoreCase(teamID))
				return teams[i];
		}
		return null;
	}

	public String getMapDisplayName() {
		return mapDisplayName;
	}

	public Team getSmallestTeam() {
		Team smallest = teams[(int) (Math.random() * teams.length)];
		for (int i = 0; i < teams.length; i++) {
			if (teams[i].getPlayers().size() < smallest.getPlayers().size())
				smallest = teams[i];
		}
		return smallest;
	}

	public void setKit(ItemStack[] kit) {
		this.kit = kit;
	}

	public ItemStack[] getKit() {
		return kit;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public boolean hasEnded() {
		return ended;
	}

	public void setEnded(boolean ended) {
		this.ended = ended;
	}

}
