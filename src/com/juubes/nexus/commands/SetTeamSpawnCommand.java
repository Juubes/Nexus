package com.juubes.nexus.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.juubes.nexus.Nexus;
import com.juubes.nexus.NexusLocation;

public class SetTeamSpawnCommand implements CommandExecutor {
	private final Nexus nexus;

	public SetTeamSpawnCommand(Nexus nexus) {
		this.nexus = nexus;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		if (!sender.isOp()) {
			sender.sendMessage("§eT§m§ on supersalainen adminkomento. Hushus.");
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
		String mapID = nexus.getEditModeHandler().getEditWorld(p);

		if (!nexus.getDatabaseManager().isMapCreated(mapID)) {
			p.sendMessage("§e" + mapID + " ei ole vielä luotu. /createmap");
			return true;
		}

		if (!nexus.getDatabaseManager().getTeamList(mapID).contains(teamID)) {
			sender.sendMessage("§eTiimiä " + teamID + " ei ole luotu.");
			return true;
		}

		nexus.getDatabaseManager().saveTeamSpawn(mapID, teamID, new NexusLocation(p.getLocation()));
		sender.sendMessage("§eTiimin spawni asetettu mappiin " + mapID + ".");

		nexus.getEditModeHandler().getPendingList().add(sender);

		return true;
	}
}
