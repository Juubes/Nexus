package com.juubes.nexus.logic;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PauseCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		if (!sender.isOp()) {
			sender.sendMessage("§eEt ole operaattori. Hushus.");
			return true;
		}

		GameState gameState = GameLogic.getGameState();
		if (gameState != GameState.RUNNING && gameState != GameState.COUNTDOWN)
		{
			sender.sendMessage("§e");
			return true;
		}

		GameLogic.togglePause();
		return true;
	}

}
