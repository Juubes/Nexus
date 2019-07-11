package com.juubes.nexus.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.juubes.nexus.Nexus;
import com.juubes.nexus.data.AbstractPlayerData;

public class SpectateCommand implements CommandExecutor {
	private final Nexus nexus;

	public SpectateCommand(Nexus nexus) {
		this.nexus = nexus;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("�eAatteliks sie alkaa leijjumaa tai jotai?");
			return true;
		}

		AbstractPlayerData pd = nexus.getDatabaseManager().getPlayerData((Player) sender);
		pd.setTeam(null);
		return true;
	}
}
