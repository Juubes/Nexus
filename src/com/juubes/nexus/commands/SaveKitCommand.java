package com.juubes.nexus.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.juubes.nexus.Nexus;

public class SaveKitCommand implements CommandExecutor {
	private final Nexus nexus;

	public SaveKitCommand(Nexus nexus) {
		this.nexus = nexus;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("§ePikkasen hankala kuule setuppaa kitit tollee.");
			return true;
		}

		if (!sender.isOp()) {
			sender.sendMessage("§eSulla ei ole permejä setupata kittejä.");
			return true;
		}

		Player p = (Player) sender;
		if (args.length == 0) {
			String editMode = nexus.getEditModeHandler().getEditWorld(p);
			nexus.getDatabaseManager().saveKitForGame(editMode, p.getInventory());
			sender.sendMessage("§eKitti tallennettu mapille " + editMode);
		} else {
			String mapID = args[0];
			nexus.getDatabaseManager().saveKitForGame(mapID, p.getInventory());
			sender.sendMessage("§eKitti tallennettu mapille " + mapID);
		}

		return true;
	}
}
