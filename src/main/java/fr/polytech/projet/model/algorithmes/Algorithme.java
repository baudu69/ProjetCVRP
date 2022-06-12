package fr.polytech.projet.model.algorithmes;

import fr.polytech.projet.model.Solution;

public interface Algorithme {

	/**
	 * @return The name of the algorithm.
	 */
	String getName();

	/**
	 * Computes one iteration of the algorithm.
	 *
	 * @return false if the algorithm has to stop, true otherwise
	 */
	boolean update();

	/**
	 * @return The current solution.
	 */
	Solution getSolution();

	/**
	 * @return the best solution if saved, the current one otherwise
	 */
	Solution stop();

}
