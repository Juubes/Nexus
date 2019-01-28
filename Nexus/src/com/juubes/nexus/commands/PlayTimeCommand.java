package com.juubes.nexus.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.juubes.nexus.logic.GameLogic;

public class PlayTimeCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		if (args.length == 0) {
			int minutes = (int) ((System.currentTimeMillis() - GameLogic.getCurrentGame()
					.getStartTime()) / 1000 / 60);
			sender.sendMessage("§ePelattu " + minutes + " minuuttia");
		} else {
			if (!sender.isOp()) {
				sender.sendMessage("§cTämä on liian oopee komento.");
				return true;
			}

			int num = 0;
			try {
				num = Integer.parseInt(args[0]);
			} catch (Exception e) {
				sender.sendMessage("Ei sopiva numero: " + args[0]);
				return true;
			}

			long newStartTime = (System.currentTimeMillis() - num * 60 * 1000);
			GameLogic.getCurrentGame().setStartTime(newStartTime);
			sender.sendMessage("§ePelin uusi aika on " + num + " minuuttia.");
		}
		return true;
	}

}
