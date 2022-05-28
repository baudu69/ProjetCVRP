package fr.polytech.projet.model.algorithmes;

import fr.polytech.projet.model.Solution;
import fr.polytech.projet.model.parametres.ListeParametre;
import fr.polytech.projet.model.parametres.ParametreImpl;

public interface Algorithme extends ParametreImpl {

	/**
	 * @return The name of the algorithm.
	 */
	String getName();

	/**
	 * Computes one iteration of the algorithm.
	 */
	void update();

	/**
	 * @return The current solution.
	 */
	Solution getSolution();

	/**
	 * @return liste des parametreDoubles d'un algorithme
	 */
	ListeParametre getParametres();

	/**
	 * Print best solution if exist
	 */
	Solution stop();

}
