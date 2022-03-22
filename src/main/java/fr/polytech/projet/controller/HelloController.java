package fr.polytech.projet.controller;

import fr.polytech.projet.application.PromptResultApplication;
import fr.polytech.projet.outils.Lecture;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class HelloController {

    private final PromptResultApplication promptResultApplication = new PromptResultApplication();

    @FXML
    private ComboBox<String> choixJeuDonnees;

    @FXML
    private Button btnValiderChoix;

    @FXML
    protected void btnValiderOnClick(ActionEvent event) throws Exception {
        this.btnValiderChoix.setText("Appuyé");
        promptResultApplication.start((Stage) btnValiderChoix.getScene().getWindow(), choixJeuDonnees.getValue());
    }

    @FXML
    protected void initialize() {
        initialiserCombobox();
    }

    private void initialiserCombobox() {
        Lecture lecture = new Lecture();
        this.choixJeuDonnees.getItems().addAll(lecture.listerLesFichiersStr());
        this.choixJeuDonnees.getSelectionModel().selectFirst();
    }
}