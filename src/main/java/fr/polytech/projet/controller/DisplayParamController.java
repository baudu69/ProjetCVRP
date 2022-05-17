package fr.polytech.projet.controller;

import fr.polytech.projet.model.algorithmes.Algorithme;
import fr.polytech.projet.model.parametres.ParametreInterface;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class DisplayParamController {
	@FXML
	protected VBox vBoxMain;

	private Algorithme algorithme;

	public void init() {
		this.algorithme.getParametres()
				.forEach(parametre -> this.vBoxMain.getChildren().add(new ParametreInterface(parametre).gethBox()));
	}

	public void setAlgorithme(Algorithme algorithme) {
		this.algorithme = algorithme;
	}
}
