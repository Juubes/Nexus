package com.juubes.nexus;

import java.util.HashMap;

public class Lang {
	private final HashMap<String, String> translations = new HashMap<>();

	public static String get(String key) {
		return Nexus.getAPI().getLang().getTranslation(key);
	}

	public String getTranslation(String key) {
		return translations.getOrDefault(key, "{no-translation: " + key + "}");
	}

}
