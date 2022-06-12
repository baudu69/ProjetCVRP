package fr.polytech.projet.model.algorithmes;

import fr.polytech.projet.model.Solution;
import fr.polytech.projet.model.algorithmes.voisinage.Voisinage;
import fr.polytech.projet.model.algorithmes.voisinage.VoisinageSwapProche;
import fr.polytech.projet.model.operation.Operation;
import fr.polytech.projet.model.operation.OperationConstantes;

import java.util.ArrayList;
import java.util.List;


public class Tabou implements Algorithme {

	private Solution solution;
	private final List<Operation> opmMoins1 = new ArrayList<>();
	private final Voisinage voisinage;
	private double fitnessMini = Double.POSITIVE_INFINITY;
	private Solution bestSolution;

	@Override
	public String getName() {
		return "Tabou";
	}

	public Tabou(Solution solution) {
		this.solution = solution;
		voisinage = new VoisinageSwapProche();
	}

	@Override
	public boolean update() {
		//On choisis C
		List<Operation> C = this.voisinage.getVoisinage(solution)
				.stream()
				.filter(op -> !operationPresentInList(op))
				.toList();


		//On garde celui avec la plus petite fitness
		double fitnessMiniXPlus1 = Double.POSITIVE_INFINITY;
		Operation operationXPlus1 = null;
		for (Operation operation : C) {
			double fitnessxPlus1 = this.getLongueurOfOperatation(operation);
			if (fitnessxPlus1 < fitnessMiniXPlus1) {
				fitnessMiniXPlus1 = fitnessxPlus1;
				operationXPlus1 = operation;
			}
		}

		if (operationXPlus1 != null) {
			//On calcule les deltas
			double deltaF = fitnessMiniXPlus1 - solution.longueur();
			if (deltaF > 0) {
				ajoutInverseOperationToListMMoins1(operationXPlus1);
			}
			if (fitnessMiniXPlus1 < fitnessMini) {
				fitnessMini = fitnessMiniXPlus1;
				bestSolution = (Solution) solution.clone();
			}
			operationXPlus1.apply(solution);
		}
		
		return true;
	}

	@Override
	public Solution getSolution() {
		return bestSolution;
	}

	@Override
	public Solution stop() {
		this.solution = bestSolution;
		return solution;
	}

	/**
	 * @param operation Operation a verifier
	 * @return true si l'operation existe dans la liste
	 */
	private boolean operationPresentInList(Operation operation) {
		return this.opmMoins1.contains(operation);
	}

	/**
	 * @param operation Operation dont l'inverse est a ajouter
	 */
	private void ajoutInverseOperationToListMMoins1(Operation operation) {
		if (opmMoins1.size() == OperationConstantes.TAILLE_LISTE_TABOU) {
			opmMoins1.remove(0);
		}
		this.opmMoins1.add(operation.inverse());
	}

	/**
	 * @param operation operation a tester
	 * @return fitness de la solution si l'on applique l'operation
	 */
	private double getLongueurOfOperatation(Operation operation) {
		operation.apply(solution);
		double fitnessOperation = solution.longueur();
		operation.inverse().apply(solution);
		return fitnessOperation;
	}
}
