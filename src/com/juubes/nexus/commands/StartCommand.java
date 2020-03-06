package com.juubes.nexus.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.juubes.nexus.Nexus;
import com.juubes.nexus.logic.GameState;

public class StartCommand implements CommandExecutor {
	private final Nexus nexus;

	public StartCommand(Nexus nexus) {
		this.nexus = nexus;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		if (!sender.isOp()) {
			sender.sendMessage("§cEi permejä.");
			return true;
		}

		if (nexus.getGameLogic().getGameState() == GameState.RUNNING) {
			sender.sendMessage("§ePeli on jo käynnissä.");
			return true;
		}

		if (nexus.getGameLogic().getGameState() == GameState.PAUSED) {
			nexus.getGameLogic().togglePause();
			return true;
		}

		if (nexus.getGameLogic().getGameState() == GameState.COUNTDOWN) {
			if (nexus.getGameLogic().getCurrentGame().hasEnded())
				nexus.getGameLogic().loadNextGame();
			nexus.getGameLogic().startGame();
			return true;
		}

		sender.sendMessage("§ePeli on pysäytetty kokonaan. /dtm status");
		return true;
	}
}
