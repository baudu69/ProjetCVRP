package fr.polytech.projet.model.algorithmes.voisinage;

import fr.polytech.projet.model.Chemin;
import fr.polytech.projet.model.Point;
import fr.polytech.projet.model.Solution;
import fr.polytech.projet.model.operation.MoveFromCheminToAnother;
import fr.polytech.projet.model.operation.Operation;
import fr.polytech.projet.model.operation.Swap;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomVoisinage extends Voisinage {
	private final Random random = new Random();
	private int taileVoisinage;

	public List<Operation> getVoisinage(Solution solution) {
		double r = random.nextDouble();
		if (r < 0.999) {
			return optimiserChemin(solution);
		} else {
			return bougerPointDeChemin(solution);
		}
	}

	private List<Operation> optimiserChemin(Solution solution) {
		List<Operation> operationList = new ArrayList<>();
		for (int i = 0; i < getTailleVoisinage(); i++) {
			Chemin chemin1 = solution.get(random.nextInt(0, solution.size()));
			Chemin chemin2 = solution.get(random.nextInt(0, solution.size()));
			Point point1 = chemin1.get(random.nextInt(0, chemin1.size()));
			Point point2 = chemin2.get(random.nextInt(0, chemin2.size()));
			Operation swap = new Swap(point1, point2);
			operationList.add(swap);
		}
		return operationList;
	}

	private List<Operation> bougerPointDeChemin(Solution solution) {
		for (int i = 0; i < solution.size(); i++) {
			Chemin chemin = solution.get(i);
			if (chemin.size() == 2) {
				Point seul = chemin.get(0).isDepot() ? chemin.get(1) : chemin.get(0);
				solution.remove(chemin);
				solution.get(0).add(seul);

			}
		}
		List<Operation> operationList = new ArrayList<>();
		for (int i = 0; i < getTailleVoisinage(); i++) {
			Chemin chemin1 = solution.get(random.nextInt(0, solution.size()));
			Chemin chemin2 = solution.get(random.nextInt(0, solution.size()));
			Point point = chemin1.get(random.nextInt(0, chemin1.size()));
			Operation move = new MoveFromCheminToAnother(chemin1, chemin2, point);
			operationList.add(move);
		}
		return operationList;


	}


	public int getTailleVoisinage() {
		return taileVoisinage;
	}
}
