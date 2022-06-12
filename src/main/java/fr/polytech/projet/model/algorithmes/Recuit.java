package fr.polytech.projet.model.algorithmes;

import fr.polytech.projet.model.Solution;
import fr.polytech.projet.model.operation.Operation;
import fr.polytech.projet.model.operation.OperationConstantes;
import fr.polytech.projet.model.operation.VoisinageFactory;

import java.util.Random;

public class Recuit implements Algorithme {

	private final Solution solution;
	private double t = 0;
	private int n2_i = 0;
	private int n1_i = 0;

	private final Random random = new Random();
	private final VoisinageFactory voisinageFactory = new VoisinageFactory();


	public Recuit(Solution solution) {

		this.solution = solution;
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
		if (n2_i == OperationConstantes.N_2) {
			n2_i = 0;
			t *= OperationConstantes.MU;
			n1_i++;
		}
	}

	@Override
	public Solution getSolution() {
		return solution;
	}

	@Override
	public Solution stop() {
		return solution;
		//Not to implement
	}

	@Override
	public String toString() {
		return "Recuit{" +
				"solution=" + solution +
				", t=" + t +
				", n2_i=" + n2_i +
				", n1_i=" + n1_i +
				", random=" + random +
				", voisinageFactory=" + voisinageFactory +
				'}';
	}
}
