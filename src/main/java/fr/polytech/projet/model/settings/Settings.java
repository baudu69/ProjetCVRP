package fr.polytech.projet.model.settings;

import java.io.FileReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

public class Settings {
	static final Gson GSON = new GsonBuilder().create();
	private static Settings settings = null;

	public static Settings getSettings() {
		return settings;
	}

	public static void reloadSettings() throws Exception {
		settings = GSON.fromJson(new JsonReader(new FileReader("settings.json")), Settings.class);

		if (settings.tabou.taille_liste_tabou() < 1) throw new IllegalArgumentException("taille_liste_tabou doit être strictement positive");
		// TODO: autres restrictions sur les paramètres
	}


	private SettingsTabou tabou;
	private SettingsRecuit recuit;

	Settings() {
	}

	public SettingsRecuit recuit() {
		return recuit;
	}
	public SettingsTabou tabou() {
		return tabou;
	}
}
