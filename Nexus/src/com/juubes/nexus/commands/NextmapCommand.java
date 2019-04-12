package com.juubes.nexus.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.juubes.nexus.Nexus;

public class NextmapCommand implements CommandExecutor {
	private final Nexus nexus;

	public NextmapCommand(Nexus nexus) {
		this.nexus = nexus;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		if (!sender.isOp()) {
			sender.sendMessage("�eSinulla ei ole permej�.");
			return true;
		}

		if (args.length == 0)
			nexus.getGameLogic().loadNextGame();
		else {
			if (args[0].equalsIgnoreCase(nexus.getGameWorldManager().getCurrentMapID())) {
				sender.sendMessage("�eT�m� mappi on jo ladattu.");
				return true;
			}
			for (String mapName : nexus.getDatabaseManager().getMaps())
				if (mapName.equalsIgnoreCase(args[0])) {
					nexus.getGameLogic().loadNextGame(mapName);
					return true;
				}
			sender.sendMessage("�eEi l�ydetty mappia " + args[0]);
		}
		return true;
	}
}
