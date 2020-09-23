package com.juubes.nexus.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.juubes.nexus.Nexus;
import com.juubes.nexus.data.AbstractPlayerData;
import com.juubes.nexus.logic.GameState;
import com.juubes.nexus.logic.Team;

public class JoinCommand implements CommandExecutor {
	private final Nexus nexus;

	public JoinCommand(Nexus nexus) {
		this.nexus = nexus;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("§eYou wot mate?");
			return true;
		}
		Player p = (Player) sender;
		if (!nexus.isReady()) {
			sender.sendMessage("§eNexus ei ole valmis pelattavaksi.");
			return true;
		}

		if (nexus.getGameLogic().getGameState() == GameState.PAUSED) {
			sender.sendMessage("§eDTM on pysäytetty.");
			return true;
		}

		if (args.length == 0) {
			AbstractPlayerData data = nexus.getDatabaseManager().getPlayerData(p.getUniqueId());
			if (data.getTeam() == null)
				data.setTeam(nexus.getGameLogic().getCurrentGame().getSmallestTeam());
			else
				p.sendMessage("§eOlet jo tiimissä " + data.getTeam().getDisplayName());
		} else if (args.length == 1) {
			if (!p.hasPermission("DTM.teamselect")) {
				p.sendMessage("§eSinulla ei ole permejä valita tiimiä.");
				return true;
			}

			Team team = getTeamWithName(args[0]);
			if (team == null) {
				sender.sendMessage("§ePelissä ei ole tiimiä " + args[0].toLowerCase() + ".");
				return true;
			}

			AbstractPlayerData pd = nexus.getDatabaseManager().getPlayerData(p.getUniqueId());
			if (pd.getTeam() == null)
				pd.setTeam(team);
			else
				p.sendMessage("§eOlet jo tiimissä " + pd.getTeam().getDisplayName());
		} else if (args.length > 1) {
			if (!p.isOp()) {
				p.sendMessage("§eEt ole operaattori.");
				return true;
			}

			Player target = Bukkit.getPlayer(args[0]);
			if (target == null) {
				p.sendMessage("§eTämä pelaaja ei ole online.");
				return true;
			}

			Team team = getTeamWithName(args[1]);
			if (team == null) {
				sender.sendMessage("§ePelissä ei ole tiimiä " + args[1].toLowerCase() + ".");
				return true;
			}

			AbstractPlayerData targetPlayerData = nexus.getDatabaseManager().getPlayerData(p.getUniqueId());
			targetPlayerData.setTeam(team);

			p.sendMessage("§ePelaaja " + target.getDisplayName() + " §elähetetty tiimiin " + team.getDisplayName()
					+ "§e.");
		}
		return true;
	}

	private Team getTeamWithName(String name) {
		Team team = nexus.getGameLogic().getCurrentGame().getTeam(name);
		if (team != null)
			return team;
		for (Team t : nexus.getGameLogic().getCurrentGame().getTeams()) {
			if (ChatColor.stripColor(t.getDisplayName()).equalsIgnoreCase(name))
				return t;
		}
		return null;
	}
}
