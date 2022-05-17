package fr.polytech.projet.model.parametres;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public abstract class ParametreInterface {

	private final Parametre parametre;

	private final Label lblName;

	private final HBox hBox;

	protected ParametreInterface(Parametre parametre) {
		this.parametre = parametre;
		this.lblName = new Label();
		this.hBox = new HBox();
		this.lblName.setText(parametre.getNom());
		this.hBox.getChildren().add(this.lblName);
	}

	public static ParametreInterface getInstance(Parametre p) {
		if (p instanceof ParametreDouble) {
			return new ParametreDoubleInterface((ParametreDouble) p);
		} else {
			throw new IllegalStateException("Type de parametre non gere");
		}
	}

	protected Parametre getParametre() {
		return parametre;
	}

	public HBox gethBox() {
		return hBox;
	}

	public Label getLblName() {
		return lblName;
	}
}
