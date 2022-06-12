package fr.polytech.projet.model.settings;

import java.io.FileReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

public class Settings {
	private static final Gson GSON = new GsonBuilder().create();
	private static Settings settings = null;

	public static Settings getSettings() {
		return settings;
	}

	public static void reloadSettings() throws Exception {
		settings = GSON.fromJson(new JsonReader(new FileReader("settings.json")), Settings.class);
	}



	private SettingsRecuit recuit;

	Settings() {
	}

	public SettingsRecuit recuit() {
		return recuit;
	}
}
