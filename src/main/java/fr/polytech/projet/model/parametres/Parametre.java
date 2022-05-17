package fr.polytech.projet.model.parametres;

public abstract class Parametre {
	private final String nom;

	protected Parametre(String nom) {
		this.nom = nom;
	}


	public String getNom() {
		return nom;
	}
}
