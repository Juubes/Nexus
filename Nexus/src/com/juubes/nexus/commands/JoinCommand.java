package com.juubes.nexus.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.juubes.nexus.Nexus;
import com.juubes.nexus.logic.GameLogic;
import com.juubes.nexus.logic.GameState;
import com.juubes.nexus.playerdata.AbstractPlayerData;
import com.juubes.nexus.playerdata.PlayerDataHandler;

public class JoinCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("§eYou wot mate?");
			return true;
		}
		Player p = (Player) sender;
		if (!Nexus.ready()) {
			sender.sendMessage("§eNexus ei ole valmis pelattavaksi.");
			return true;
		}

		if (GameLogic.getGameState() == GameState.PAUSED) {
			sender.sendMessage("§eDTM on pysäytetty.");
			return true;
		}

		if (args.length == 0) {
			// Remove from old team and join to new team
			AbstractPlayerData data = PlayerDataHandler.get(p);
			if (data.getTeam() == null)
				data.setTeam(GameLogic.getCurrentGame().getSmallestTeam());
			else
				p.sendMessage("§eOlet jo tiimissä " + data.getTeam().getDisplayName());
		} else if (args.length == 1) {
			// Test for valid team
			if (GameLogic.getCurrentGame().getTeam(args[0]) == null) {
				sender.sendMessage("§ePelissä ei ole tiimiä " + args[0].toLowerCase() + ".");
				return true;
			}

		} else if (args.length > 1) {
			// TODO: support for admin joining
		}
		return true;
	}
}
