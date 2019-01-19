package com.juubes.nexus.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.juubes.nexus.playerdata.AbstractPlayerData;
import com.juubes.nexus.playerdata.PlayerDataHandler;

public class SpectateCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("§eAatteliks sie alkaa leijjumaa tai jotai?");
			return true;
		}

		AbstractPlayerData pd = PlayerDataHandler.get((Player) sender);
		pd.setTeam(null);
		return true;
	}
}
