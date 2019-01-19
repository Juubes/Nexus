package com.juubes.nexus.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.juubes.nexus.Nexus;

public class SetLobbyCommand implements CommandExecutor {
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
		String editWorld = EditModeHandler.getEditWorld(p);
		Nexus.getDatabaseManager().saveLobbyForMap(editWorld, p.getLocation());
		sender.sendMessage("§eLobby tallennettu mappiin " + editWorld + ".");
		EditModeHandler.pendingList.add(sender);
		return true;
	}
}
