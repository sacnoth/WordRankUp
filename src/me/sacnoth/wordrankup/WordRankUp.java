package me.sacnoth.wordrankup;

import java.util.Set;
import java.util.logging.Logger;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class WordRankUp extends JavaPlugin {
	static Logger log;
	static PermissionManager pexPermissions;
	static Permission vaultPermissions;
	static Config config;
	static boolean pexActivated = false;
	static boolean vaultActivated = false;
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
			pexPermissions = PermissionsEx.getPermissionManager();
			pexActivated = true;
			log.info("Using PermissionsEx!");
		} else if (Bukkit.getServer().getPluginManager()
				.isPluginEnabled("Vault")) {
			setupPermissions();
			vaultActivated = true;
			log.info("Using " + vaultPermissions.getName() + " via Vault.");
		} else {
			log.warning("Neither PEX nor Vault found, WordRankUp will not work properly!");
		}
	}

	public void onDisable() {
		log.info("Players are no longer able to RankUp via codewords");
	}

	private boolean setupPermissions() {
		RegisteredServiceProvider<Permission> permissionProvider = getServer()
				.getServicesManager().getRegistration(
						net.milkbowl.vault.permission.Permission.class);
		if (permissionProvider != null) {
			vaultPermissions = permissionProvider.getProvider();
		}
		return (vaultPermissions != null);
	}

	public static boolean checkPermission(String node, Player player) {
		if (pexActivated) {
			if (pexPermissions.has(player, node)) {
				return true;
			}
			return false;
		} else if (vaultActivated && vaultPermissions.isEnabled()) {
			if (vaultPermissions.playerHas(player.getWorld(), player.getName(),
					node)) {
				log.info("Triggered");
				return true;
			}
			return false;
		}
		player.sendMessage(ChatColor.RED
				+ "Neither PEX nor Vault found, WordRankUp will not work properly!");
		return false;
	}

	public static void setGroup(Player player, String group) {
		if (pexActivated) {
			PermissionUser user = PermissionsEx.getUser(player);
			String[] groups = { group };
			user.setGroups(groups);
		} else if (vaultActivated && vaultPermissions.isEnabled()) {
			vaultPermissions.playerRemoveGroup(player,
					vaultPermissions.getPrimaryGroup(player));
			vaultPermissions.playerAddGroup(player, group);
		}
	}
}
