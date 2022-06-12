package fr.polytech.projet.model.operation;

import java.util.Map;
import java.util.Random;

import fr.polytech.projet.model.Point;
import fr.polytech.projet.model.Solution;

import static fr.polytech.projet.model.settings.SettingsRecuit.SettingsVoisinage;

public class VoisinageFactoryRecuit {

	private final SettingsVoisinage settings;

	public VoisinageFactoryRecuit(SettingsVoisinage settings) {
		this.settings = settings;
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
			final int chemin = getRandomCheminNonVide(solution);
			final int chemin$size = solution.get(chemin).size() - 1;

			final int ia = random.nextInt(chemin$size);
			int ib;
			do {
				ib = random.nextInt(chemin$size);
			} while (ib == ia);

			return new TwoOpt(chemin, Math.min(ia, ib), Math.max(ia, ib));
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
			final int chemin1 = getRandomCheminNonVide(solution);
			final int chemin2 = getRandomCheminNonVide(solution);

			return new TransfertClient(
					chemin1, random.nextInt(1, solution.get(chemin1).size() - 1),
					chemin2, random.nextInt(1, solution.get(chemin2).size() - 1)
			);
		}

		return Operation.NOP;
	}

	private int getRandomCheminNonVide(Solution solution) {
		int c;
		final int size = solution.size();
		do {
			c = random.nextInt(size);
		} while (solution.get(c).size() == 2);
		return c;
	}

}
