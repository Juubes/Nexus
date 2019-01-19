package com.juubes.nexus.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.juubes.nexus.logic.GameLogic;

public class StatusCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		if (!sender.isOp()) {
			sender.sendMessage("�eTsot tsot... t�� on yll�pitokomento.");
			return true;
		}

		sender.sendMessage("�eMapin nimi: �b" + GameLogic.getCurrentGame().getMapDisplayName());
		sender.sendMessage("�eTiimej�: �b" + GameLogic.getCurrentGame().getTeams().length);
		return true;
	}

}
