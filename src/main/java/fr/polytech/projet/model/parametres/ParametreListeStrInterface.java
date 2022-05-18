package fr.polytech.projet.model.parametres;

import javafx.scene.control.ComboBox;

public class ParametreListeStrInterface extends ParametreInterface {

	private final ComboBox<String> comboBox;

	public ParametreListeStrInterface(ParametreListeStr parametreListeStr) {
		super(parametreListeStr);
		this.comboBox = new ComboBox<>();
		init();
	}

	private void init() {
		if (!this.getParametreListe().getStringList().isEmpty()) {
			this.comboBox.getItems().addAll(this.getParametreListe().getStringList());
			this.comboBox.getSelectionModel().select(0);
		}
		this.gethBox().getChildren().add(comboBox);
		this.comboBox.valueProperty().addListener((observable, oldValue, newValue) -> majParametre());
	}

	private void majParametre() {
		this.getParametreListe().setValue(this.comboBox.getValue());
	}

	private ParametreListeStr getParametreListe() {
		return (ParametreListeStr) getParametre();
	}
}
