package fr.polytech.projet.model.parametres;

/**
 * Classe contenant les donnees des d'un parametre
 */
public class Parametre {

	private final String nom;
	private final Double minValue;
	private final Double maxValue;
	protected Double value;

	public Parametre(String nom, Double minValue, Double maxValue, Double value) {
		this.nom = nom;
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.value = value;
	}

	public Double getMinValue() {
		return minValue;
	}

	public Double getMaxValue() {
		return maxValue;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public String getNom() {
		return nom;
	}

	@Override
	public String toString() {
		return "Parametre{" +
				"nom='" + nom + '\'' +
				", min=" + minValue +
				", max=" + maxValue +
				", value=" + value +
				'}';
	}
}
