package com.juubes.nexus;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.bukkit.configuration.file.YamlConfiguration;

import net.md_5.bungee.api.ChatColor;

public class Lang {
    private final HashMap<String, String> translations = new HashMap<>();

    public static String get(String key) {
        return Nexus.getAPI().getLang().getTranslation(key);
    }

    public String getTranslation(String key) {
        return ChatColor.translateAlternateColorCodes('&', translations.getOrDefault(key, "{no-translation: " + key
                + "}"));
    }

    public void loadTranslations(InputStream is) {
        try (InputStreamReader reader = new InputStreamReader(is)) {
            YamlConfiguration conf = YamlConfiguration.loadConfiguration(reader);
            for (String key : conf.getKeys(false)) {
                this.translations.put(key, conf.getString(key));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
