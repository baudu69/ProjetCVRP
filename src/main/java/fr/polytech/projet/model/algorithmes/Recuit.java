package fr.polytech.projet.model.algorithmes;

import java.util.List;
import java.util.Random;

import fr.polytech.projet.model.Solution;
import fr.polytech.projet.model.operation.Operation;
import fr.polytech.projet.model.operation.VoisinageFactory;
import fr.polytech.projet.model.parametres.ListeParametre;
import fr.polytech.projet.model.parametres.ParametreDouble;
import fr.polytech.projet.model.parametres.ParametreInt;
import fr.polytech.projet.model.parametres.ParametreListeStr;
import fr.polytech.projet.model.settings.Settings;
import fr.polytech.projet.model.settings.SettingsRecuit;

public class Recuit implements Algorithme {

	private final Solution solution;
	private final SettingsRecuit settings;
	private double t;
	private int n2_i = 0;
	private int n1_i = 0;

	private final Random random = new Random();
	private final VoisinageFactory voisinageFactory = new VoisinageFactory();

	ListeParametre parametres = ListeParametre.of(
			new ParametreDouble("MU", 0.0, 0.9999, 0.99),
			new ParametreDouble("T0", 1.0, 1000.0, 900.0),
			new ParametreInt("n2", 1.0, 10.0, 5.0),
			new ParametreListeStr("testCB", List.of("test1", "test2"), "test1")
	);

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
	public ListeParametre getParametres() {
		return parametres;
	}

	@Override
	public Solution stop() {
		return solution;
		//Not to implement
	}

	@Override
	public String toString() {
		return "Recuit{" +
				"parametreDoubles = " + parametres +
				"}";
	}

	@Override
	public void applyParametre() {

	}
}
