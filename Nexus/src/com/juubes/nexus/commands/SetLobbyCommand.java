package com.juubes.nexus.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.juubes.nexus.Nexus;

public class SetLobbyCommand implements CommandExecutor {

	private final Nexus nexus;

	public SetLobbyCommand(Nexus nexus) {
		this.nexus = nexus;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		if (!sender.isOp()) {
			sender.sendMessage("�eT�m� on supersalainen adminkomento. Hushus.");
			return true;
		}
		if (!(sender instanceof Player)) {
			sender.sendMessage("�eEt voi tehd� t�t� komentoa.");
			return true;
		}

		Player p = (Player) sender;
		String editWorld = nexus.getEditModeHandler().getEditWorld(p);
		if (!nexus.getDatabaseManager().isMapCreated(editWorld)) {
			p.sendMessage("�e" + editWorld + " ei ole viel� luotu. /createmap");
			return true;
		}
		nexus.getDatabaseManager().saveLobbyForMap(editWorld, p.getLocation());
		sender.sendMessage("�eLobby tallennettu mappiin " + editWorld + ".");
		nexus.getEditModeHandler().getPendingList().add(sender);
		return true;
	}
}
