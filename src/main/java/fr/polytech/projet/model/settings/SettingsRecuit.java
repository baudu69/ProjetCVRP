package fr.polytech.projet.model.settings;

public class SettingsRecuit {
	private double t0;
	private double mu;
	private int n1;
	private int n2;

	private SettingsVoisinage poids_voisinage;

	SettingsRecuit() {
	}

	public double t0() {
		return t0;
	}
	public double mu() {
		return mu;
	}
	public int n1() {
		return n1;
	}
	public int n2() {
		return n2;
	}

	@Override
	public String toString() {
		return "SettingsRecuit{t0=" + t0 + ", mu=" + mu + ", n1=" + n1 + ", n2=" + n2 + "}";
	}

	public static class SettingsVoisinage {
		private double swap;
		private double two_opt;
		private double transfert_client;
		private double swap_path;

		SettingsVoisinage() {
		}

		public double swap() {
			return swap;
		}
		public double two_opt() {
			return two_opt;
		}
		public double transfert_client() {
			return transfert_client;
		}
		public double swap_path() {
			return swap_path;
		}
	}
}
