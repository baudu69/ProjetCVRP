package fr.polytech.projet.model.operation;

import java.util.Map;
import java.util.Random;

import fr.polytech.projet.model.Chemin;
import fr.polytech.projet.model.Point;
import fr.polytech.projet.model.Solution;
import fr.polytech.projet.model.settings.Settings;
import fr.polytech.projet.model.settings.SettingsRecuit;

public class VoisinageFactoryRecuit {

	private final SettingsRecuit.SettingsVoisinage settings;

	public VoisinageFactoryRecuit() {
		settings = Settings.getSettings().recuit().poids_voisinage();
	}

	private final Random random = new Random();

	public Operation getRandomVoisinage(Solution solution) {
		double rand_type = random.nextDouble() * settings.sum();

		final Map<Integer, Point> points = solution.getPoints();
		final int points$size = points.size();

		rand_type -= settings.swap();
		if (rand_type < 0) {
			return new Swap(
					points.get(random.nextInt(points$size - 1) + 1),
					points.get(random.nextInt(points$size - 1) + 1)
			);
		}
		rand_type -= settings.two_opt();
		if (rand_type < 0) {
//			final Chemin chemin = getRandomCheminNonVide(solution);
//			final int chemin$size = chemin.size() - 2;
//
//			return new TwoOpt(
//					chemin.get(random.nextInt(chemin$size) + 1),
//					chemin.get(random.nextInt(chemin$size) + 1)
//			);
			// TODO: use new 2-opt version
			return Operation.NOP;
		}
		rand_type -= settings.swap_path();
		if (rand_type < 0) {
			return new SwapPath(
					points.get(random.nextInt(points$size - 1) + 1),
					points.get(random.nextInt(points$size - 1) + 1)
			);
		}
		rand_type -= settings.transfert_client();
		if (rand_type < 0) {
//			final Chemin chemin1 = getRandomCheminNonVide(solution);
//			final Chemin chemin2 = getRandomCheminNonVide(solution);
//
//			return new TransfertClient(
//					chemin1, random.nextInt(1, chemin1.size() - 1),
//					chemin2, random.nextInt(1, chemin2.size())
//			);
			// TODO: use new TFRC version
			return Operation.NOP;
		}

		return Operation.NOP;
	}

	private Chemin getRandomCheminNonVide(Solution solution) {
		Chemin c;
		final int size = solution.size();
		do {
			c = solution.get(random.nextInt(size));
		} while (c.size() == 2);
		return c;
	}

}
