package com.juubes.nexus.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.juubes.nexus.Nexus;
import com.juubes.nexus.data.AbstractPlayerData;
import com.juubes.nexus.data.PlayerDataHandler;
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
			sender.sendMessage("�eYou wot mate?");
			return true;
		}
		Player p = (Player) sender;
		if (!nexus.isReady()) {
			sender.sendMessage("�eNexus ei ole valmis pelattavaksi.");
			return true;
		}

		if (nexus.getGameLogic().getGameState() == GameState.PAUSED) {
			sender.sendMessage("�eDTM on pys�ytetty.");
			return true;
		}

		if (args.length == 0) {
			AbstractPlayerData data = PlayerDataHandler.get(p);
			if (data.getTeam() == null)
				data.setTeam(nexus.getGameLogic().getCurrentGame().getSmallestTeam());
			else
				p.sendMessage("�eOlet jo tiimiss� " + data.getTeam().getDisplayName());
		} else if (args.length == 1) {
			if (!p.hasPermission("DTM.teamselect")) {
				p.sendMessage("�eSinulla ei ole permej� valita tiimi�.");
				return true;
			}

			Team team = getTeamWithName(args[0]);
			if (team == null) {
				sender.sendMessage("�ePeliss� ei ole tiimi� " + args[0].toLowerCase() + ".");
				return true;
			}

			AbstractPlayerData pd = AbstractPlayerData.get(p);
			if (pd.getTeam() == null)
				pd.setTeam(team);
			else
				p.sendMessage("�eOlet jo tiimiss� " + pd.getTeam().getDisplayName());
		} else if (args.length > 1) {
			if (!p.isOp()) {
				p.sendMessage("�eEt ole operaattori.");
				return true;
			}

			Player target = Bukkit.getPlayer(args[0]);
			if (target == null) {
				p.sendMessage("�eT�m� pelaaja ei ole online.");
				return true;
			}

			Team team = getTeamWithName(args[1]);
			if (team == null) {
				sender.sendMessage("�ePeliss� ei ole tiimi� " + args[1].toLowerCase() + ".");
				return true;
			}

			AbstractPlayerData targetPlayerData = AbstractPlayerData.get(target);
			targetPlayerData.setTeam(team);

			p.sendMessage(
					"�ePelaaja " + target.getDisplayName() + " �el�hetetty tiimiin " + team.getDisplayName() + "�e.");
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
