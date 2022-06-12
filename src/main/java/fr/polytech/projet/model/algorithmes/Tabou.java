package fr.polytech.projet.model.algorithmes;

import fr.polytech.projet.model.Solution;
import fr.polytech.projet.model.operation.Operation;
import fr.polytech.projet.model.operation.VoisinageFactoryTabou;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Set;

public class Tabou implements Algorithme {

	private final Solution solution;
//	private final SettingsTabou settings;
	private final Deque<Operation> listeTabou;
	private final VoisinageFactoryTabou voisinageFactory = new VoisinageFactoryTabou();
	private Solution bestSolution = null;
	private final int taille_liste_tabou = 1000;

	@Override
	public String getName() {
		return "Tabou";
	}

	public Tabou(Solution solution) {
		this.solution = solution;
		//this.settings = Settings.getSettings().tabou();
//		System.out.println(settings.toString());
		this.listeTabou = new ArrayDeque<>(taille_liste_tabou);
	}

	@Override
	public boolean update() {
		final Set<Operation> voisinage = voisinageFactory.getFullVoisinage(solution);
//		System.out.println("Taille voisinage : " + voisinage.size());
		Operation bestOp = null;
		double bestFitness = 0;

		for (final Operation op : voisinage) {
			if (!listeTabou.contains(op) && op.isValid(solution)) {
				op.apply(solution);
				if (bestOp == null || solution.longueur() < bestFitness) {
					bestOp = op;
					bestFitness = solution.longueur();
				}
				op.inverse().apply(solution);
			}
		}

		if (bestOp == null) return false;

//		System.out.println("Operation: " + bestOp);

		double prevFitness = solution.longueur();

		bestOp.apply(solution);

		if (solution.longueur() >= prevFitness) {
//			System.out.println("+LISTE: " + bestOp.inverse());
			listeTabou.addLast(bestOp);
			listeTabou.addLast(bestOp.inverse());
			while (listeTabou.size() > taille_liste_tabou) listeTabou.pop();
		} else if (bestSolution == null || solution.longueur() < bestSolution.longueur()) {
			bestSolution = solution.copy();
		}

		return true;
	}

	@Override
	public Solution getSolution() {
		return solution;
	}

	@Override
	public Solution getBestSolution() {
		return bestSolution;
	}

}
