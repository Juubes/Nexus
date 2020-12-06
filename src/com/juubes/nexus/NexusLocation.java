package com.juubes.nexus;

import org.bukkit.Location;
import org.bukkit.World;

/**
 * A class to replace org.bukkit.Location in some cases, where a reference to a
 * World is not necessary.
 */
public class NexusLocation {
	private double x;
	private double y;
	private double z;
	private float pitch;
	private float yaw;

	public NexusLocation(double x, double y, double z, float yaw, float pitch) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
	}

	public NexusLocation(Location location) {
		this.x = location.getX();
		this.y = location.getY();
		this.z = location.getZ();
		this.yaw = location.getYaw();
		this.pitch = location.getPitch();
	}

	public NexusLocation(int x, int y, int z) {
		this(x, y, z, 0, 0);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public float getYaw() {
		return yaw;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public Location toLocation(World world) {
		if (world == null)
			throw new NullPointerException();
		return new Location(world, x, y, z, yaw, pitch);
	}

	public NexusBlockLocation getBlock() {
		return new NexusBlockLocation((int) x, (int) y, (int) z);
	}
}
