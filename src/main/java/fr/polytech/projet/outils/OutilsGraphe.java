package fr.polytech.projet.outils;

import fr.polytech.projet.model.Chemin;
import fr.polytech.projet.model.Point;
import fr.polytech.projet.model.Solution;

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
    
    public static Solution generateRandomSolution(Map<Integer, Point> points) {
        final Solution ret = new Solution(points);
        
        final int CAPACITY = 100;
        
        final Random random = new Random();
        final int nbCamions = points.values().stream().mapToInt(Point::q).sum() / CAPACITY + 1;

	    for (int i = 0; i < nbCamions; i++) {
		    final Chemin chemin = new Chemin();
			chemin.add(points.get(0));
			ret.add(chemin);
	    }
		
		for (final Integer id : points.keySet()) {
			if (id != 0) {
				ret.get(random.nextInt(nbCamions)).add(points.get(id));
			}
		}

        return ret;
    }

}
