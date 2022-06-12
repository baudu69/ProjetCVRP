package fr.polytech.projet.model.algorithmes;

import fr.polytech.projet.model.Solution;
import fr.polytech.projet.model.operation.Operation;
import fr.polytech.projet.model.operation.VoisinageFactory;
import fr.polytech.projet.model.settings.Settings;
import fr.polytech.projet.model.settings.SettingsRecuit;

import java.util.Random;

public class Recuit implements Algorithme {

	private final Solution solution;
	private final SettingsRecuit settings;
	private double t;
	private int n2_i = 0;
	private int n1_i = 0;

	private final Random random = new Random();
	private final VoisinageFactory voisinageFactory = new VoisinageFactory();


	public Recuit(Solution solution) {
		this.solution = solution;

		this.settings = Settings.getSettings().recuit();
		System.out.println(settings.toString());

		this.t = settings.t0();
	}

	@Override
	public String getName() {
		return "Recuit";
	}

	@Override
	public boolean update() {
		final double f = solution.longueur();

		Operation operation;
		do {
			operation = voisinageFactory.getRandomVoisinage(solution);
		} while (!operation.isValid(solution));

		System.out.println("OPERATION: " + operation);
		System.out.printf("t: %.4e\n", this.t);

		operation.apply(solution);
		final double new_f = solution.longueur();
		final double delta_f = new_f - f;

		if (delta_f >= 0) {
			double p = random.nextDouble();
			if (p > Math.exp(-delta_f / t)) {
				// rollback
				operation.inverse().apply(solution);
			}
		}

		n2_i++;
		if (n2_i == settings.n2()) {
			n2_i = 0;
			t *= settings.mu();
			n1_i++;
		}

		System.out.println("n1: " + n1_i);
		System.out.println("n2: " + n2_i);

		return n1_i != settings.n1();
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
				", settings=" + settings +
				'}';
	}
}
