package fr.polytech.projet.model.algorithmes;

import fr.polytech.projet.model.Solution;
import fr.polytech.projet.model.parametres.Parametre;

import java.util.List;

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
	 * @return liste des parametres d'un algorithme
	 */
	List<Parametre> getParametres();

}
