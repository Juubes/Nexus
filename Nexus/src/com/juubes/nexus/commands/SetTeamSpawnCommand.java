package com.juubes.nexus.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.juubes.nexus.Nexus;

public class SetTeamSpawnCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		if (!sender.isOp()) {
			sender.sendMessage("§eTämä on supersalainen adminkomento. Hushus.");
			return true;
		}
		if (!(sender instanceof Player)) {
			sender.sendMessage("§eEt voi tehdä tätä komentoa.");
			return true;
		}
		Player p = (Player) sender;
		if (args.length != 1) {
			sender.sendMessage("§c/setteamspawn <tiimi>");
			return true;
		}

		String teamID = args[0].toLowerCase();
		String mapID = EditModeHandler.getEditWorld(p);

		if (!Nexus.getDatabaseManager().isMapCreated(mapID)) {
			p.sendMessage("§e" + mapID + " ei ole vielä luotu. /createmap");
			return true;
		}

		if (!Nexus.getDatabaseManager().getTeamList(mapID).contains(teamID)) {
			sender.sendMessage("§eTiimiä " + teamID + " ei ole luotu.");
			return true;
		}

		Nexus.getDatabaseManager().saveTeamSpawn(mapID, teamID, p.getLocation());
		sender.sendMessage("§eTiimin spawni asetettu mappiin " + mapID + ".");

		EditModeHandler.pendingList.add(sender);

		return true;
	}
}
