package com.juubes.nexus.commands;

import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.juubes.nexus.ColorCodes;
import com.juubes.nexus.Nexus;

public class AddTeamCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("§eTämän voi suorittaa vain pelaajana.");
			return true;
		}
		if (!sender.isOp()) {
			sender.sendMessage("§cSinulla ei ole permejä.");
			return true;
		}

		Player p = (Player) sender;
		String mapID = EditModeHandler.getEditWorld(p);

		if (args.length == 0) {
			sender.sendMessage("§c/addteam <team ID> <color> <display name...>");
			return true;
		}

		String teamID = args[0].toLowerCase();
		if (args.length == 1) {
			sender.sendMessage("§c/addteam " + teamID + " <color> <display name...>");
			return true;
		}

		String color = args[1].toUpperCase();
		if (args.length == 2) {
			sender.sendMessage("§c/addteam " + teamID + " " + color + " <display name...>");
			return true;
		}

		for (String dbTeamID : Nexus.getDatabaseManager().getTeamList(mapID)) {
			if (dbTeamID.equals(teamID)) {
				sender.sendMessage("§eTiimi " + teamID + " on jo luotu.");
				return true;
			}
		}
		ChatColor parsedColor = getChatColor(color);
		if (parsedColor == null) {
			sender.sendMessage("§eTiimin väri ei kelpaa: " + color + ".");
			return true;
		}

		String displayName = args[2];

		Set<String> teamIDs = Nexus.getDatabaseManager().getTeamList(mapID);
		teamIDs.add(teamID);
		Nexus.getDatabaseManager().setTeamList(mapID, teamIDs);
		Nexus.getDatabaseManager().setTeamDisplayName(mapID, teamID, displayName);
		Nexus.getDatabaseManager().setTeamColor(mapID, teamID, parsedColor);

		sender.sendMessage("§eTiimi " + displayName + " tallennettu mappiin " + mapID
				+ ". Voit asettaa spawnin /setteamspawn " + teamID + "");
		return true;
	}

	private ChatColor getChatColor(String color) {
		for (ChatColor color2 : ChatColor.values()) {
			if (color.equalsIgnoreCase(color2.name()))
				return color2;
		}
		try {
			return ColorCodes.fromColorCode(color);
		} catch (Exception e) {
			return null;
		}
	}
}
