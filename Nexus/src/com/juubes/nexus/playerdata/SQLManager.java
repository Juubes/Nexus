package com.juubes.nexus.playerdata;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.juubes.nexus.Nexus;

/**
 * Handles SQL connecting and executing of queries
 */
public class SQLManager {

	private static Connection conn;

	private static void checkConnection() {
		if (conn == null) {
			FileConfiguration conf = Nexus.getPlugin().getConfig();
			String pw = conf.getString("mysql.password");
			String user = conf.getString("mysql.user");
			String server = conf.getString("mysql.server");
			String db = conf.getString("mysql.database");

			System.out.println("Connecting to " + server + "/" + db + " as user " + user);
			try {
				conn = DriverManager.getConnection("jdbc:mysql://" + server + "/" + db, user, pw);
			} catch (SQLException e) {
				e.printStackTrace();
				for (Player p : Bukkit.getOnlinePlayers()) {
					p.kickPlayer(
							"§e§lDTM\n§b      Palvelin uudelleenkäynnistyy teknisistä syistä.");
				}
				Bukkit.shutdown();
			}
		} else {
			try {
				if (!conn.isValid(0)) {

					conn.close();

					conn = null;
					checkConnection();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
	}

	public static void execute(String sql) throws SQLException {
		checkConnection();

		Statement stmt = conn.createStatement();
		stmt.execute(sql);
	}

	public static ResultSet query(String sql) throws SQLException {
		checkConnection();

		Statement stmt = conn.createStatement();
		return stmt.executeQuery(sql);
	}

}
