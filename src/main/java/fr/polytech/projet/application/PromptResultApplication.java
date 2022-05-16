package fr.polytech.projet.application;

import fr.polytech.projet.HelloApplication;
import fr.polytech.projet.controller.PromptResultViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PromptResultApplication {

    public void start(Stage stage, String nomFichier) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("scene/prompt-result-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 800);
        PromptResultViewController controller = fxmlLoader.getController();
        controller.setFichier(nomFichier);
        controller.init();
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
}
