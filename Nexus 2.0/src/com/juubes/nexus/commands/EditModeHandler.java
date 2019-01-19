package com.juubes.nexus.commands;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EditModeHandler implements CommandExecutor {
	public static Set<CommandSender> pendingList = new HashSet<>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		if (!sender.isOp()) {
			sender.sendMessage("§eSulla ei ole permejä muokkaustilaan senkin pelle.");
			return true;
		}
		if (!(sender instanceof Player)) {
			sender.sendMessage("§eEt voi tehdä tätä komentoa.");
			return true;
		}

		Player p = (Player) sender;
		if (args.length > 0) {
			setEditModeWorld(p, args[0]);
		}
		sender.sendMessage("§eMuokkaustilasi on maailma " + getEditWorld(p));
		sender.sendMessage("§eVoit vaihtaa muokkaustilaa komennolla: /editmode <Map ID>");
		return true;
	}

	private final static HashMap<Player, String> EDITMODE = new HashMap<>();

	public static String getEditWorld(Player p) {
		if (EDITMODE.get(p) == null)
			return p.getWorld().getName();
		else
			return EDITMODE.get(p);
	}

	public static void setEditModeWorld(Player p, String mapID) {
		EDITMODE.put(p, mapID);
	}
}
