package com.juubes.nexus.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.juubes.nexus.Nexus;
import com.juubes.nexus.data.AbstractPlayerData;

public class SetPrefixCommand implements CommandExecutor {
	private final Nexus nexus;

	public SetPrefixCommand(Nexus nexus) {
		this.nexus = nexus;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		if (!sender.isOp()) {
			sender.sendMessage("�bVain operaattorit voivat asettaa prefixej�.");
			return true;
		}

		if (args.length < 2) {
			sender.sendMessage("�bJotai meni pielee... /setprefix <player> <prefix>");
			return true;
		} else {
			String userInput = args[1];
			for (int i = 2; i < args.length; i++) {
				userInput += " " + args[i];
			}

			if (userInput.equals("\"\""))
				userInput = null;
			else if (userInput.equals("jonne"))
				userInput = "&eDTM-Jonne";
			else if (userInput.equals("admin"))
				userInput = "&4Adminaattori";
			else if (userInput.equals("mode"))
				userInput = "&cModenaattori";
			else if (userInput.equals("mode"))
				userInput = "&6Rakennusnaattori";

			Player target = Bukkit.getPlayerExact(args[0]);
			String correctName;

			if (target == null) {
				sender.sendMessage("�cPelaaja ei ole servulla.");
				return true;
			} else {
				// Player is on the server
				AbstractPlayerData data = nexus.getDatabaseManager().getPlayerData(target);
				data.setPrefix(userInput);
				correctName = target.getName();

				nexus.getGameLogic().updateNameTag(target);
			}

			if (userInput == null)
				sender.sendMessage("�bPelaajalla " + correctName + " �bei ole en�� erityist� prefixi�.");
			else
				sender.sendMessage("�bPelaajan " + correctName + " �buusi prefix on "
						+ ChatColor.translateAlternateColorCodes('&', userInput));
		}
		return true;
	}
}
