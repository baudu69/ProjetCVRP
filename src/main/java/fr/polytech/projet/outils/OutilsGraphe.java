package fr.polytech.projet.outils;

import fr.polytech.projet.model.Chemin;

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

}
