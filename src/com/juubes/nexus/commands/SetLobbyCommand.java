package com.juubes.nexus.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.juubes.nexus.Nexus;
import com.juubes.nexus.NexusLocation;

public class SetLobbyCommand implements CommandExecutor {

	private final Nexus nexus;

	public SetLobbyCommand(Nexus nexus) {
		this.nexus = nexus;
	}

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
		String editWorld = nexus.getEditModeHandler().getEditWorld(p);
		if (!nexus.getDatabaseManager().isMapCreated(editWorld)) {
			p.sendMessage("§e" + editWorld + " ei ole vielä luotu. /createmap");
			return true;
		}
		nexus.getDatabaseManager().saveLobbyForMap(editWorld, new NexusLocation(p.getLocation()));
		sender.sendMessage("§eLobby tallennettu mappiin " + editWorld + ".");
		nexus.getEditModeHandler().getPendingList().add(sender);
		return true;
	}
}
