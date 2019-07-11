package com.juubes.nexus.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.juubes.nexus.Nexus;
import com.juubes.nexus.data.AbstractPlayerData;
import com.juubes.nexus.data.AbstractSeasonStats;

public class StatsCommand implements CommandExecutor {
	private final Nexus nexus;

	public StatsCommand(Nexus nexus) {
		this.nexus = nexus;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		if (args.length == 0) {
			// Print own stats
			if (!(sender instanceof Player)) {
				sender.sendMessage("�eDude... s� oot konsoli. Ei sul oo statsei.");
				sender.sendMessage("�b/stats <nimi>");
				return true;
			}
			Player p = (Player) sender;
			AbstractPlayerData pd = nexus.getDatabaseManager().getPlayerData(p);
			sender.sendMessage("�e�l    Kausi");
			sender.sendMessage(pd.getSeasonStats().toString());
			sender.sendMessage("�e�l    Yhteens�");
			sender.sendMessage(pd.getTotalStats().toString());
		} else {
			// Print target's stats
			AbstractSeasonStats stats = nexus.getDatabaseManager().getSeasonStats(args[0], nexus.getCurrentSeason());
			if (stats == null) {
				sender.sendMessage("�eEi l�ydetty statseja pelaajalle " + args[0]);
				return true;
			}
		}
		return true;
	}
}
