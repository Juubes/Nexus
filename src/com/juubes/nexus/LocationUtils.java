package com.juubes.nexus;

import org.bukkit.Location;

public final class LocationUtils {

	/**
	 * Returns a Location object with a null world
	 */
	public static Location toLocation(String str) {
		if (str == null)
			return null;
		str = str.replace(" ", "");
		String[] coordinates = str.split(",");

		double x = Double.parseDouble(coordinates[0]);
		double y = Double.parseDouble(coordinates[1]);
		double z = Double.parseDouble(coordinates[2]);
		float yaw = Float.parseFloat(coordinates[3]);
		float pitch = Float.parseFloat(coordinates[4]);
		return new Location(null, x, y, z, yaw, pitch);
	}

	/**
	 * Returns a string representation of a location without the world
	 */
	public static String toString(Location loc) {
		if (loc == null)
			return null;
		return loc.getX() + ", " + loc.getY() + ", " + loc.getZ() + ", " + loc.getYaw() + ", " + loc
				.getPitch();
	}

}
