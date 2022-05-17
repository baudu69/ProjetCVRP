package fr.polytech.projet.model.parametres;

/**
 * Classe contenant les donnees des d'un parametre
 */
public class ParametreDouble extends Parametre {
	private final Double minValue;
	private final Double maxValue;
	protected Double value;

	public ParametreDouble(String nom, Double minValue, Double maxValue, Double value) {
		super(nom);
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

	@Override
	public String toString() {
		return "ParametreDouble{" +
				"nom='" + getNom() + '\'' +
				", min=" + minValue +
				", max=" + maxValue +
				", value=" + value +
				'}';
	}
}
