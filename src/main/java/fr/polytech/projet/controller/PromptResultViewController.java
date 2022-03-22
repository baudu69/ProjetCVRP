package fr.polytech.projet.controller;

import fr.polytech.projet.model.Entrepot;
import fr.polytech.projet.model.Point;
import fr.polytech.projet.outils.Lecture;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class PromptResultViewController {

    @FXML
    protected Group group;
    private String fichier;

    public String getFichier() {
        return fichier;
    }

    public void setFichier(String fichier) {
        this.fichier = fichier;
    }

    public void chargerPoints() {
        Lecture lecture = new Lecture();
        lecture.lireFichier(this.fichier).forEach(this::dessinerPoint);
    }

    private void dessinerPoint(Point point) {
        Circle circle = new Circle();
        circle.setCenterX(point.getX() * 8);
        circle.setCenterY(point.getY() * 8);
        circle.setRadius(2.0f);
        if (point instanceof Entrepot)
            circle.setFill(Paint.valueOf("RED"));
        this.group.getChildren().add(circle);
    }
}
