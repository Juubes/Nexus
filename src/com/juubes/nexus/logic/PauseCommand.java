package com.juubes.nexus.logic;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.juubes.nexus.Nexus;

public class PauseCommand implements CommandExecutor {

	private final Nexus nexus;
	
	public PauseCommand(Nexus nexus) {
		this.nexus = nexus;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		if (!sender.isOp()) {
			sender.sendMessage("�eEt ole operaattori. Hushus.");
			return true;
		}

		GameState gameState = nexus.getGameLogic().getGameState();
		if (gameState == GameState.PAUSED) {
			sender.sendMessage("�ePeli on jo pys�ytetty. Voit aloittaa pelin komennolla /start");
			return true;
		}

		nexus.getGameLogic().togglePause();
		return true;
	}

}
