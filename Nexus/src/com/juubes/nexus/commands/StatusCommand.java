package com.juubes.nexus.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.juubes.nexus.Nexus;

public class StatusCommand implements CommandExecutor {
	private final Nexus nexus;

	public StatusCommand(Nexus nexus) {
		this.nexus = nexus;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		if (!sender.isOp()) {
			sender.sendMessage("�eTsot tsot... t�� on yll�pitokomento.");
			return true;
		}

		sender.sendMessage("�eMapin nimi: �b" + nexus.getGameLogic().getCurrentGame().getMapDisplayName());
		sender.sendMessage("�eTiimej�: �b" + nexus.getGameLogic().getCurrentGame().getTeams().length);
		return true;
	}

}
