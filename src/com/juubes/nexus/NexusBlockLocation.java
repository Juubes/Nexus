package com.juubes.nexus;

import org.bukkit.World;
import org.bukkit.block.Block;

public class NexusBlockLocation implements Cloneable {
	private NexusLocation loc;

	public NexusBlockLocation(int x, int y, int z) {
		this.loc = new NexusLocation(x, y, z, 0, 0);
	}

	public NexusBlockLocation(NexusLocation loc) {
		this.loc = loc;
	}

	public NexusBlockLocation(Block block) {
		this.loc = new NexusLocation(block.getLocation());
	}

	/**
	 * Returns a bukkit block object at the located at this world.
	 */
	public Block getBlock(World world) {
		return world.getBlockAt(loc.toLocation(world));
	}

	public int getX() {
		return (int) loc.getX();
	}

	public int getY() {
		return (int) loc.getY();
	}

	public int getZ() {
		return (int) loc.getZ();
	}

	public NexusLocation getLocation() {
		return loc;
	}

	@Override
	public NexusBlockLocation clone() throws CloneNotSupportedException {
		return new NexusBlockLocation(getX(), getY(), getZ());
	}
}
