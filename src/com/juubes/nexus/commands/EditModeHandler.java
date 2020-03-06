package com.juubes.nexus.commands;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.juubes.nexus.Lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.juubes.nexus.Nexus;

public class EditModeHandler implements CommandExecutor {
	private Set<CommandSender> pendingList = new HashSet<>();
	private final Nexus nexus;

	public EditModeHandler(Nexus nexus) {
		this.nexus = nexus;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		if (!sender.isOp()) {
			sender.sendMessage("§eSulla ei ole permejä muokkaustilaan senkin pelle.");
			return true;
		}
		if (!(sender instanceof Player)) {
			sender.sendMessage("§eEt voi tehd§ tätä komentoa.");
			return true;
		}

		Player p = (Player) sender;
		if (args.length > 0) {

			boolean foundMapID = false;
			for (String map : nexus.getDatabaseManager().getMaps()) {
				if (map.equalsIgnoreCase(args[0])) {
					foundMapID = true;
					setEditModeWorld(p, map);
					break;
				}
			}
			if (!foundMapID) {
				sender.sendMessage(Lang.get("map-not-found"));
			}

		}
		sender.sendMessage("§eMuokkaustilasi on maailma " + getEditWorld(p));
		sender.sendMessage("§eVoit vaihtaa muokkaustilaa komennolla: /editmode <Map ID>");
		return true;
	}

	private final HashMap<Player, String> EDITMODE = new HashMap<>();

	public String getEditWorld(Player p) {
		if (EDITMODE.get(p) == null)
			return p.getWorld().getName();
		else
			return EDITMODE.get(p);
	}

	public void setEditModeWorld(Player p, String mapID) {
		EDITMODE.put(p, mapID);
	}
	
	public Set<CommandSender> getPendingList() {
		return pendingList;
	}
}
