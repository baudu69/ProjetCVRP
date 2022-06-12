package fr.polytech.projet.model.settings;

public class SettingsTabou {
	private int taille_liste_tabou;
	private SettingsVoisinage operations_voisinage;

	SettingsTabou() {
	}

	public int taille_liste_tabou() {
		return taille_liste_tabou;
	}
	public SettingsVoisinage operations_voisinage() {
		return operations_voisinage;
	}

	@Override
	public String toString() {
		return Settings.GSON.toJson(this);
	}

	public static class SettingsVoisinage {
		private boolean swap;
		private boolean two_opt;
		private boolean transfert_client;
		private boolean swap_path;

		SettingsVoisinage() {
		}

		public boolean swap() {
			return swap;
		}
		public boolean two_opt() {
			return two_opt;
		}
		public boolean transfert_client() {
			return transfert_client;
		}
		public boolean swap_path() {
			return swap_path;
		}
	}
}
