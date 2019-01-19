package com.juubes.nexus.logic;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class GameHandlingCommands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		{
			Bukkit.broadcastMessage("§eKomentoa ei käsitelty: /" + cmd.getName());
		}
		return false;
	}

}
