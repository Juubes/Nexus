package com.juubes.nexus.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.juubes.nexus.Nexus;
import com.juubes.nexus.data.AbstractPlayerData;
import com.juubes.nexus.logic.GameState;

public class JoinCommand implements CommandExecutor {
	private final Nexus pl;

	public JoinCommand(Nexus pl) {
		this.pl = pl;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// TODO implement

		if (!(sender instanceof Player))
			return true;

		if (pl.getLogicHandler().getGameState() == GameState.PAUSED) {
			sender.sendMessage("§ePeli on pysäytetty.");
			return true;
		}

		AbstractPlayerData pd = pl.getDataHandler().getPlayerData(((Player) sender).getUniqueId());
		if (args.length == 0) {
			if (pd.team == null)
				pl.getLogicHandler().setPlayerToSmallestTeam((Player) sender);
			else
				p.sendMessage("§eOlet jo tiimissä " + pd.team.displayName);
		}

		return true;
	}
}
