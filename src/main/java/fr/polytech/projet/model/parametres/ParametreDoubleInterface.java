package fr.polytech.projet.model.parametres;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;

/**
 * Interface entre un parametreDouble et un slider
 */
public class ParametreDoubleInterface extends ParametreInterface {
	private final Slider slider;
	private final Label lblValue;

	public ParametreDoubleInterface(ParametreDouble parametreDouble) {
		super(parametreDouble);
		this.slider = new Slider();
		this.lblValue = new Label();
		init();
		slider.valueProperty().addListener((observable, oldValue, newValue) -> majParametre());
	}

	private void init() {
		this.slider.setMax(this.getParametreDouble().getMaxValue());
		this.slider.setMin(this.getParametreDouble().getMinValue());
		this.slider.setValue(this.getParametreDouble().getValue());
		this.slider.setBlockIncrement(1.0);
		this.slider.setShowTickLabels(true);
		this.slider.setShowTickMarks(true);
		this.lblValue.setText(String.valueOf(this.slider.getValue()));
		this.gethBox().getChildren().add(slider);
		this.gethBox().getChildren().add(lblValue);
	}

	private void majParametre() {
		Double value = this.slider.getValue();
		this.getParametreDouble().setValue(value);
		this.lblValue.setText(String.format("%.2f", this.getParametreDouble().getValue()));
	}

	private ParametreDouble getParametreDouble() {
		return (ParametreDouble) getParametre();
	}
}
