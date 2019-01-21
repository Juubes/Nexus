package com.juubes.nexus.commands;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.juubes.nexus.Nexus;

public class SaveMapCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		if (!sender.isOp()) {
			sender.sendMessage("§cMapin voi luoda vain operaattori.");
			return true;
		}
		if (!(sender instanceof Player)) {
			sender.sendMessage("§cTän voi suorittaa vain pelaajana.");
			return true;
		}

		if (args.length == 0) {
			sender.sendMessage("§c/savemap <Map ID>");

		} else {
			// Make sure correct capitalization
			String mapID = args[0];
			for (String map : Nexus.getDatabaseManager().getMaps()) {
				if (mapID.equalsIgnoreCase(map))
					mapID = map;
			}

			Player p = (Player) sender;
			// Save the player's map to Nexus maps folder
			World w = p.getWorld();
			File from = w.getWorldFolder();
			File to = new File(Nexus.getConfigFolder(), "maps/" + mapID);

			// Remove already existing files and useless playerdata
			try {
				FileUtils.deleteDirectory(new File(from, "playerdata"));
				FileUtils.deleteDirectory(new File(from, "stats"));
				new File(from, "uid.dat").delete();
				// Remove and copy
				FileUtils.deleteDirectory(to);
				FileUtils.copyDirectory(from, to);
				new File(to, "session.lock").delete();
			} catch (IOException e) {
				e.printStackTrace();
				sender.sendMessage("§cJotain meni pieleen: " + e.getMessage());
				return true;
			}
			sender.sendMessage("§eMappi tallennettu!");
		}
		return true;
	}

}
