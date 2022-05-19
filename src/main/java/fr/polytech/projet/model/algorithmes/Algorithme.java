package fr.polytech.projet.model.algorithmes;

import fr.polytech.projet.model.Solution;
import fr.polytech.projet.model.parametres.ListeParametre;

public interface Algorithme {

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

}
