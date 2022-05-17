package fr.polytech.projet.model.parametres;

import java.util.ArrayList;
import java.util.Arrays;

public class ListeParametre extends ArrayList<Parametre> {
	public static ListeParametre of(Parametre... parametres) {
		ListeParametre listeParametre = new ListeParametre();
		listeParametre.addAll(Arrays.asList(parametres));
		return listeParametre;
	}

	public Parametre find(String nom) {
		return this.stream()
				.filter(parametre -> parametre.getNom().equals(nom))
				.findFirst()
				.orElseThrow();

	}
}
