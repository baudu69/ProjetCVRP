package fr.polytech.projet.model.settings;

public class SettingsRecuit {
	private double t0;
	private double mu;
	private int n1;
	private int n2;

	private SettingsVoisinage poids_voisinage;

	SettingsRecuit() {
	}

	public SettingsRecuit(double t0, double mu, int n1, int n2, SettingsVoisinage poids_voisinage) {
		this.t0 = t0;
		this.mu = mu;
		this.n1 = n1;
		this.n2 = n2;
		this.poids_voisinage = poids_voisinage;
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
	public SettingsVoisinage poids_voisinage() {
		return poids_voisinage;
	}

	@Override
	public String toString() {
		return Settings.GSON.toJson(this);
	}

	public static class SettingsVoisinage {
		private double swap;
		private double two_opt;
		private double transfert_client;
		private double swap_path;

		private Double sum = null;

		SettingsVoisinage() {
		}

		public SettingsVoisinage(double swap, double two_opt, double transfert_client, double swap_path) {
			this.swap = swap;
			this.two_opt = two_opt;
			this.transfert_client = transfert_client;
			this.swap_path = swap_path;
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

		public double sum() {
			if (sum == null)
				sum = swap + two_opt + transfert_client + swap_path;
			return sum;
		}
	}
}
