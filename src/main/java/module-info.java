module fr.polytech.projet {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
	
	requires com.google.gson;

	exports fr.polytech.projet.controller;
	opens fr.polytech.projet.controller to javafx.fxml;
	exports fr.polytech.projet.application;
	opens fr.polytech.projet.application to javafx.fxml;
	exports fr.polytech.projet;
	opens fr.polytech.projet to javafx.fxml;

	exports fr.polytech.projet.model;
	exports fr.polytech.projet.model.operation;
	exports fr.polytech.projet.model.algorithmes;
}
