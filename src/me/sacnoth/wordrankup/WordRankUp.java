package me.sacnoth.wordrankup;

import java.util.Set;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class WordRankUp extends JavaPlugin {
	static Logger log;
	static PermissionManager permissions;
	static Config config;
	static boolean activatedPermissions = false;
	static String successText;
	static String successChat;
	static Set<String> codeword;

	public void onEnable() {
		log = this.getLogger();

		getServer().getPluginManager()
				.registerEvents(new EventListener(), this);

		config = new Config(this);
		config.load();

		log.info("Players are now able to RankUp via codewords");

		if (Bukkit.getServer().getPluginManager()
				.isPluginEnabled("PermissionsEx")) {
			permissions = PermissionsEx.getPermissionManager();
			activatedPermissions = true;
			log.info("Using Permissions!");
		}
	}

	public void onDisable() {
		log.info("Players are no longer able to RankUp via codewords");
	}

	public static boolean checkPermission(String node, Player player) {
		if (activatedPermissions) {
			if (permissions.has(player, node)) {
				return true;
			}
			return false;
		}
		player.sendMessage(ChatColor.RED
				+ "PEX was not found, please contact your admin!");
		return false;
	}
}
