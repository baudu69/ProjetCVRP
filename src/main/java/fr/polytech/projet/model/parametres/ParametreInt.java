package fr.polytech.projet.model.parametres;

public class ParametreInt extends ParametreDouble {
	public ParametreInt(String nom, Double minValue, Double maxValue, Double value) {
		super(nom, minValue, maxValue, value);
	}

	@Override
	public void setValue(Double value) {
		this.value = 0.0 + Math.round(value);
	}
}
