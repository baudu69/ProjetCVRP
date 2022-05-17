package fr.polytech.projet.model.algorithmes;

import fr.polytech.projet.model.Solution;
import fr.polytech.projet.model.parametres.Parametre;

import java.util.List;

public class Tabou implements Algorithme {

	List<Parametre> parametres = List.of(new Parametre("Taille du voisinage", 1.0, 10.0, 5.0),
			new Parametre("Taille de la liste", 1.0, 10.0, 5.0));

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
	public List<Parametre> getParametres() {
		return parametres;
	}

}
