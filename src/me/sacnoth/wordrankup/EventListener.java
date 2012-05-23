package me.sacnoth.wordrankup;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

public class EventListener implements Listener {
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerChat(PlayerChatEvent event) {
		Player player = event.getPlayer();
		String message = event.getMessage().toLowerCase();
		if (WordRankUp.codeword.contains(message)) {
			if (WordRankUp.checkPermission("wordrankup." + message, player)) {
				String group = WordRankUp.config.getGroup(message);
				String name = WordRankUp.config.getName(message);
				WordRankUp.setGroup(player, group);
				WordRankUp.log.info("Promoting: " + player.getDisplayName()
						+ " to " + group);
				player.sendMessage(ChatColor.GREEN + WordRankUp.successText
						+ " " + name);
				event.setMessage(WordRankUp.successChat + " " + name);
			}
		}
	}
}
