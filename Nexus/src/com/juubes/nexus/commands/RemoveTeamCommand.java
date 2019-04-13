package com.juubes.nexus.commands;

import java.util.Set;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.juubes.nexus.Nexus;

public class RemoveTeamCommand implements CommandExecutor {
	private final Nexus nexus;

	public RemoveTeamCommand(Nexus nexus) {
		this.nexus = nexus;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("�eT�m�n voi suorittaa vain pelaajana.");
			return true;
		}
		if (!sender.isOp()) {
			sender.sendMessage("�cSinulla ei ole permej�.");
			return true;
		}

		Player p = (Player) sender;
		String mapID = nexus.getEditModeHandler().getEditWorld(p);

		if (args.length == 0) {
			sender.sendMessage("�c/removeteam <team ID>");
			return true;
		}

		String teamID = args[0].toLowerCase();
		Set<String> teams = nexus.getDatabaseManager().getTeamList(mapID);
		boolean teamExists = false;
		for (String id2 : teams) {
			if (id2.equals(teamID)) {
				teamExists = true;
			}
		}
		if (!teamExists) {
			sender.sendMessage("�eTiimi� " + teamID + " ei ole ladattu.");
			sender.sendMessage("�eTiimit: ");
			for (String id : nexus.getDatabaseManager().getTeamList(mapID)) {
				sender.sendMessage("�e  " + id);
			}
			return true;
		}

		teams.remove(teamID);

		nexus.getDatabaseManager().setTeamList(mapID, teams);
		sender.sendMessage("�e" + teamID + " tiimi poistettu.");
		return true;
	}
}
