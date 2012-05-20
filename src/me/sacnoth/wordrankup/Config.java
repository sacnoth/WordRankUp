package me.sacnoth.wordrankup;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {
	private WordRankUp plugin;

	public Config(WordRankUp plugin) {
		this.plugin = plugin;
	}

	public void load() {
		final FileConfiguration config = plugin.getConfig();

		config.addDefault("language.success", "You are now a");
		WordRankUp.successText = config.getString("language.success");
		config.set("language.success", WordRankUp.successText);

		config.addDefault("language.successChat", "I am now a");
		WordRankUp.successChat = config.getString("language.successChat");
		config.set("language.successChat", WordRankUp.successChat);

		WordRankUp.codeword = config.getKeys(false);
		WordRankUp.codeword.remove("language");

		if (WordRankUp.codeword.isEmpty()) {
			String key = "password"
					+ (int) Math.round(Math.random() * 100000000);
			config.addDefault(key + ".group", "member");
			config.set(key + ".group", "member");
			config.addDefault(key + ".name", "registered player");
			config.set(key + ".name", "registered player");
		}

		plugin.saveConfig();
	}

	public String getGroup(String word) {
		final FileConfiguration config = plugin.getConfig();
		return config.getString(word + ".group");
	}

	public String getName(String word) {
		final FileConfiguration config = plugin.getConfig();
		return config.getString(word + ".name");
	}
}
