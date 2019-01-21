package com.juubes.nexus.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.juubes.nexus.Nexus;
import com.juubes.nexus.logic.GameLogic;
import com.juubes.nexus.logic.GameWorldManager;

public class NextmapCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		if (!sender.isOp()) {
			sender.sendMessage("§eSinulla ei ole permejä.");
			return true;
		}

		if (args.length == 0)
			GameLogic.loadNextGame();
		else {
			if (args[0].equalsIgnoreCase(GameWorldManager.getCurrentMapID())) {
				sender.sendMessage("§eTämä mappi on jo ladattu.");
				return true;
			}
			for (String mapName : Nexus.getDatabaseManager().getMaps())
				if (mapName.equalsIgnoreCase(args[0])) {
					GameLogic.loadNextGame(mapName);
					return true;
				}
			sender.sendMessage("§eEi löydetty mappia " + args[0]);
		}
		return true;
	}
}
