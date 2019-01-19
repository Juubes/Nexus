package com.juubes.nexus.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.juubes.nexus.logic.GameLogic;

public class NextmapCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
		if (!sender.isOp()) {
			sender.sendMessage("§eSinulla ei ole permejä.");
			return true;
		}

		GameLogic.loadNextGame();
		return true;
	}
}
