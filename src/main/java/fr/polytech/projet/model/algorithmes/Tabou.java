package fr.polytech.projet.model.algorithmes;

import fr.polytech.projet.model.Solution;
import fr.polytech.projet.model.algorithmes.voisinage.Voisinage;
import fr.polytech.projet.model.algorithmes.voisinage.VoisinageSwapProche;
import fr.polytech.projet.model.operation.Operation;
import fr.polytech.projet.model.parametres.ListeParametre;
import fr.polytech.projet.model.parametres.ParametreInt;

import java.util.ArrayList;
import java.util.List;


public class Tabou implements Algorithme {

	private final Solution solution;
	private final List<Operation> opmMoins1 = new ArrayList<>();
	private final ListeParametre parametres;
	private final Voisinage voisinage;
	private double fitnessMini = Double.POSITIVE_INFINITY;
	private List<Operation> opGetBackBestSolution = new ArrayList<>();
	private int tailleListe;

	@Override
	public String getName() {
		return "Tabou";
	}

	public Tabou(Solution solution) {
		this.solution = solution;
		parametres = ListeParametre.of(
				new ParametreInt("Taille du voisinage", 1.0, 50.0, 5.0),
				new ParametreInt("Taille de la liste", 1.0, 50.0, 15.0));
		parametres.forEach(parametre -> parametre.ajouterParametreImpl(this));
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
				opGetBackBestSolution.add(operationXPlus1.inverse());
			}
			if (fitnessMiniXPlus1 < fitnessMini) {
				fitnessMini = fitnessMiniXPlus1;
				opGetBackBestSolution = new ArrayList<>();
			}
			operationXPlus1.apply(solution);
		}
		
		return true;
	}

	@Override
	public ListeParametre getParametres() {
		return parametres;
	}

	@Override
	public Solution getSolution() {
		return solution;
	}

	@Override
	public Solution stop() {
		applyListeOperationToSolution();
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
		//While au lieu de if au cas ou la taille de la liste est change
		while (this.opmMoins1.size() >= tailleListe) {
			this.parametres.remove(0);
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

	/**
	 * Applique la liste des operations inverse pour recuperer la solution minimale
	 */
	private void applyListeOperationToSolution() {
		for (int i = opGetBackBestSolution.size() - 1; i >= 0; i--) {
			opGetBackBestSolution.get(i).apply(solution);
			opGetBackBestSolution.remove(i);
		}
	}

	@Override
	public void applyParametre() {
		this.tailleListe = ((ParametreInt) parametres.find("Taille de la liste")).getValue().intValue();
	}
}
