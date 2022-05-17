package fr.polytech.projet.model.algorithmes;

import fr.polytech.projet.model.Solution;
import fr.polytech.projet.model.operation.Operation;
import fr.polytech.projet.model.operation.VoisinageFactory;
import fr.polytech.projet.model.parametres.Parametre;
import fr.polytech.projet.model.parametres.ParametreInt;

import java.util.List;
import java.util.Random;

public class Recuit implements Algorithme {

	private final Solution solution;
	private final double mu;
	private final double t0;
	private final int n2;

	private double t;
	private int n2_i = 0;
	private int n1_i = 0;

	private final Random random = new Random();
	private final VoisinageFactory voisinageFactory = new VoisinageFactory();

	List<Parametre> parametres = List.of(
			new Parametre("Taille du voisinage", 1.0, 10.0, 5.0),
			new Parametre("ALED du voisinage", 1.0, 10.0, 5.0),
			new ParametreInt("ALED du voisinage Entier", 1.0, 10.0, 5.0)
	);

	public Recuit(Solution solution, double mu, double t0, int n2) {
		if (mu < 0 || mu >= 1) throw new IllegalArgumentException("mu must be between 0 and 1");

		this.solution = solution;
		this.mu = mu;
		this.t0 = t0;
		this.n2 = n2;

		this.t = t0;
	}

	@Override
	public String getName() {
		return "Recuit";
	}

	@Override
	public void update() {
		final double f = solution.longueur();
		
		final Operation operation = voisinageFactory.getRandomVoisinage(solution);
		
		operation.apply(solution);
		final double new_f = solution.longueur();
		final double delta_f = new_f - f;
		
		if (delta_f > 0) {
			double p = random.nextDouble();
			if (p > Math.exp(-delta_f / t)) {
				// rollback
				operation.inverse().apply(solution);
			}
		}

		n2_i++;
		if (n2_i == n2) {
			n2_i = 0;
			t *= mu;
			n1_i++;
		}
	}

	@Override
	public Solution getSolution() {
		return solution;
	}

	@Override
	public List<Parametre> getParametres() {
		return parametres;
	}

	@Override
	public String toString() {
		return "Recuit{" +
				"mu=" + mu + ", " +
				"t0=" + t0 + ", " +
				"n2=" + n2 +
				"}";
	}
}
