package fr.polytech.projet.model.parametres;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;

/**
 * Interface entre un parametre et un slider
 */
public class ParametreInterface {
	private final Parametre parametre;
	private final Slider slider;
	private final Label lblName;
	private final Label lblValue;

	private final HBox hBox;

	public ParametreInterface(Parametre parametre) {
		this.parametre = parametre;
		this.slider = new Slider();
		this.lblName = new Label();
		this.lblValue = new Label();
		this.hBox = new HBox();
		init();
		slider.valueProperty().addListener((observable, oldValue, newValue) -> majParametre());
	}

	private void init() {
		this.slider.setMax(this.parametre.getMaxValue());
		this.slider.setMin(this.parametre.getMinValue());
		this.slider.setValue(this.parametre.getValue());
		this.slider.setBlockIncrement(1.0);
		this.slider.setShowTickLabels(true);
		this.slider.setShowTickMarks(true);
		this.lblName.setText(this.parametre.getNom());
		this.lblValue.setText(String.valueOf(this.slider.getValue()));
		this.hBox.getChildren().add(lblName);
		this.hBox.getChildren().add(slider);
		this.hBox.getChildren().add(lblValue);
	}

	private void majParametre() {
		Double value = this.slider.getValue();
		this.getParametre().setValue(value);
		this.lblValue.setText(String.format("%.2f", this.parametre.getValue()));
	}

	public Parametre getParametre() {
		return parametre;
	}

	public HBox gethBox() {
		return hBox;
	}
}
