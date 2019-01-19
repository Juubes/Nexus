package com.juubes.nexus.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.juubes.nexus.logic.GameLogic;
import com.juubes.nexus.logic.GameState;

public class StartCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		if (!sender.isOp()) {
			sender.sendMessage("§cEi permejä.");
			return true;
		}

		if (GameLogic.getGameState() == GameState.RUNNING) {
			sender.sendMessage("§ePeli on jo käynnissä.");
			return true;
		}

		if (GameLogic.getGameState() == GameState.PAUSED) {
			GameLogic.togglePause();
			return true;
		}

		if (GameLogic.getGameState() == GameState.COUNTDOWN) {
			if (GameLogic.getCurrentGame().hasEnded())
				GameLogic.loadNextGame();
			GameLogic.startGame();
			return true;
		}

		sender.sendMessage("§ePeli on pysäytetty kokonaan. /dtm status");
		return true;
	}
}
