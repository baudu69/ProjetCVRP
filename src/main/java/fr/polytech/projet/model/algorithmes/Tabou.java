package fr.polytech.projet.model.algorithmes;

import fr.polytech.projet.model.Solution;
import fr.polytech.projet.model.parametres.ListeParametre;
import fr.polytech.projet.model.parametres.ParametreDouble;

;

public class Tabou implements Algorithme {

	ListeParametre parametres = ListeParametre.of(new ParametreDouble("Taille du voisinage", 1.0, 10.0, 5.0),
			new ParametreDouble("Taille de la liste", 1.0, 10.0, 5.0));

	@Override
	public String getName() {
		return "Tabou";
	}

	@Override
	public void update() {
		throw new IllegalStateException("Not Implemented Yet");
		// TODO: implement
	}

	@Override
	public Solution getSolution() {
		return null;
	}

	@Override
	public ListeParametre getParametres() {
		return parametres;
	}

}
