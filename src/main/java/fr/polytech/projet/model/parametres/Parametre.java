package fr.polytech.projet.model.parametres;

import java.util.ArrayList;
import java.util.List;

public abstract class Parametre {
	private final String nom;
	private List<ParametreImpl> parametresImpls = new ArrayList<>();

	protected Parametre(String nom) {
		this.nom = nom;
	}


	public String getNom() {
		return nom;
	}

	protected void notifyParametresImpl() {
		System.out.println(parametresImpls.size());
		parametresImpls.forEach(ParametreImpl::applyParametre);
	}

	public void ajouterParametreImpl(ParametreImpl p) {
		System.out.println("Ajout");
		parametresImpls.add(p);
	}
}
