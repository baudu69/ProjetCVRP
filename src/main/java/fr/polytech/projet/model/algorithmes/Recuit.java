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
	private double t;
	private int n2_i = 0;
	private int n1_i = 0;

	private final Random random = new Random();
	private final VoisinageFactory voisinageFactory = new VoisinageFactory();

	List<Parametre> parametres = List.of(
			new Parametre("MU", 0.0, 0.9999, 0.99),
			new Parametre("T0", 1.0, 1000.0, 900.0),
			new ParametreInt("n2", 1.0, 10.0, 5.0)
	);

	public Recuit(Solution solution) {

		this.solution = solution;

		this.t = parametres.get(1).getValue();
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
		if (n2_i == parametres.get(2).getValue()) {
			n2_i = 0;
			t *= this.parametres.get(0).getValue();
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
				"parametres = " + parametres +
				"}";
	}
}
