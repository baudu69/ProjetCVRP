package fr.polytech.projet.outils;

import fr.polytech.projet.model.Chemin;
import fr.polytech.projet.model.Point;
import fr.polytech.projet.model.Solution;
import fr.polytech.projet.model.operation.OperationConstantes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class OutilsGraphe {


    /**
     * Genere un nouveau chemin aleatoire
     *
     * @param chemin chemin entree
     * @return nouveau chemin aleatoire
     */
    public static Chemin genererSolutionAleatoire(Chemin chemin) {
        Chemin chemin1 = new Chemin();
        chemin1.add(chemin.get(0));
	    chemin.remove(0);
	    Random random = new Random();
	    for (int i = 0; i < chemin.size(); i++) {
		    int index = random.nextInt(chemin.size());
		    chemin1.add(chemin.get(index));
		    chemin.remove(index);
	    }
	    return chemin1;
    }

	public static Solution generateRandomSolution(Map<Integer, Point> pointMap) {
		final Solution ret = new Solution(pointMap);
		final List<Point> points = new ArrayList<>(pointMap.values());
		final int CAPACITY = OperationConstantes.CAPACITY_MAX;
		final Random random = new Random();
		Chemin chemin = new Chemin();
		final Point depot = pointMap.get(0);
		pointMap.remove(0);
		while (!points.isEmpty()) {
			Point point = points.get(random.nextInt(points.size()));
			if (chemin.quantity() + point.q() > CAPACITY) {
				chemin.add(depot);
				ret.add(chemin);
				chemin = new Chemin();
				chemin.add(depot);
			}
			chemin.add(point);
			points.remove(point);
		}
		chemin.add(depot);
		ret.add(chemin);

		return ret;
	}

}
