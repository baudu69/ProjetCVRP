package fr.polytech.projet.model.operation;

import java.util.Map;
import java.util.Random;

import fr.polytech.projet.model.Chemin;
import fr.polytech.projet.model.Point;
import fr.polytech.projet.model.Solution;

public class VoisinageFactory {

	private final double w_swap = 1;
	private final double w_2opt = 1;
	private final double w_sum;

	public VoisinageFactory() {
		w_sum = w_swap + w_2opt;
	}

	private final Random random = new Random();

	public Operation getRandomVoisinage(Solution solution) {
		double rand_type = random.nextDouble() * w_sum;

		final Map<Integer, Point> points = solution.getPoints();
		final int points$size = points.size();

		if (rand_type < w_swap) {
			return new Swap(points.get(random.nextInt(points$size)), points.get(random.nextInt(points$size)));
		}
		rand_type -= w_swap;
		if (rand_type < w_2opt) {
			final Chemin chemin = solution.get(random.nextInt(solution.size()));
			final int chemin$size = chemin.size() - 2;
			return new TwoOpt(chemin.get(random.nextInt(chemin$size) + 1), chemin.get(random.nextInt(chemin$size) + 1));
		}

		return Operation.NOP;
	}

}
