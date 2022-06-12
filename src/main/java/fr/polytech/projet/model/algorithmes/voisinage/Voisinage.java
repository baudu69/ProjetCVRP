package fr.polytech.projet.model.algorithmes.voisinage;

import fr.polytech.projet.model.Chemin;
import fr.polytech.projet.model.Solution;
import fr.polytech.projet.model.operation.Operation;

import java.util.List;
import java.util.Random;

public abstract class Voisinage {

	private static final Random random = new Random();

	/**
	 * @param solution solution
	 * @return liste des operations pour chaque solution du voisinage
	 */
	public abstract List<Operation> getVoisinage(Solution solution);


	/**
	 * @param solution solution
	 * @return random chemin
	 */
	protected Chemin getRandomChemin(Solution solution) {
		return solution.get(random.nextInt(0, solution.size()));
	}

	/**
	 * @param solution solution
	 * @param already  chemin a ne pas prendre en compte
	 * @return random point
	 */
	protected Chemin getRandomChemin(Solution solution, Chemin already) {
		Chemin chemin = getRandomChemin(solution);
		while (chemin.equals(already)) {
			chemin = getRandomChemin(solution);
		}
		return chemin;
	}
}
