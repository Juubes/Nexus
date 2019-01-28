package com.juubes.nexus.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.juubes.nexus.Nexus;

public class CreateMapCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		if (!sender.isOp()) {
			sender.sendMessage("§cEt voi luoda mappia. Op häx häx.");
			return true;
		}

		if (args.length == 0) {
			sender.sendMessage("§c/createmap <mapID> <display name> <time ticks>");
			return true;
		}

		String mapID = args[0];
		if (args.length == 1) {
			sender.sendMessage("§c/createmap " + mapID + " <display name> <time ticks>");
			return true;
		}

		String displayName = args[1].replace("_", " ");
		if (args.length == 2) {
			sender.sendMessage("§c/createmap " + mapID + " " + ChatColor
					.translateAlternateColorCodes('&', displayName) + " <time ticks>");
			return true;
		}

		try {
			Integer.parseInt(args[2]);
		} catch (Exception e) {
			sender.sendMessage("§cValitse numero 0-100 maailman ajaksi.");
			return true;
		}

		int ticks = Integer.parseInt(args[2]);
		Nexus.getDatabaseManager().createMap(mapID, displayName, ticks * 240);

		sender.sendMessage("§eMappi " + mapID + " luotu.");

		if (sender instanceof Player) {
			Nexus.getDatabaseManager().saveLobbyForMap(mapID, ((Player) sender).getLocation());
			sender.sendMessage("§eLobby tallennettu mappiin " + mapID + ".");
		}



		EditModeHandler.pendingList.add(sender);
		return true;
	}
}
