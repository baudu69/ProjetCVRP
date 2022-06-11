package fr.polytech.projet.model.operation;

import java.util.Map;
import java.util.Random;

import fr.polytech.projet.model.Chemin;
import fr.polytech.projet.model.Point;
import fr.polytech.projet.model.Solution;

public class VoisinageFactory {

	private final double w_swap = 3;
	private final double w_2opt = 10;
	private final double w_pathSwap = 1;
	private final double w_tfrc = 20;

	private final double w_sum;

	public VoisinageFactory() {
		w_sum = w_swap + w_2opt + w_pathSwap + w_tfrc;
	}

	private final Random random = new Random();

	public Operation getRandomVoisinage(Solution solution) {
		double rand_type = random.nextDouble() * w_sum;

		final Map<Integer, Point> points = solution.getPoints();
		final int points$size = points.size();

		if (rand_type < w_swap) {
			return new Swap(
					points.get(random.nextInt(points$size - 1) + 1),
					points.get(random.nextInt(points$size - 1) + 1)
			);
		}
		rand_type -= w_swap;
		if (rand_type < w_2opt) {
			final Chemin chemin = getRandomCheminNonVide(solution);
			final int chemin$size = chemin.size() - 2;

			return new TwoOpt(
					chemin.get(random.nextInt(chemin$size) + 1),
					chemin.get(random.nextInt(chemin$size) + 1)
			);
		}
		rand_type -= w_2opt;
		if (rand_type < w_pathSwap) {
			return new SwapPath(
					points.get(random.nextInt(points$size - 1) + 1),
					points.get(random.nextInt(points$size - 1) + 1)
			);
		}
		rand_type -= w_pathSwap;
		if (rand_type < w_tfrc) {
			final Chemin chemin1 = getRandomCheminNonVide(solution);
			final Chemin chemin2 = getRandomCheminNonVide(solution);
			
			return new TransfertClient(
					chemin1, random.nextInt(1, chemin1.size() - 1),
					chemin2, random.nextInt(1, chemin2.size())
			);
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
