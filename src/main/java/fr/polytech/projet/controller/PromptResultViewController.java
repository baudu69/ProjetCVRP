package fr.polytech.projet.controller;

import fr.polytech.projet.model.Chemin;
import fr.polytech.projet.model.Entrepot;
import fr.polytech.projet.model.Point;
import fr.polytech.projet.outils.Lecture;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;

public class PromptResultViewController {

    private final int coefMulti = 8;

    @FXML
    protected Group group;

    @FXML
    protected Label lblDistance;

    private String fichier;

    private List<Point> points;
    private List<Chemin> chemins;

    public void setFichier(String fichier) {
        this.fichier = fichier;
    }

    public void chargerPoints() {
        Lecture lecture = new Lecture();
        List<Point> points = lecture.lireFichier(this.fichier);
        points.forEach(this::dessinerPoint);
        genererCheminAleatoire(points);
    }

    private void dessinerPoint(Point point) {
        Circle circle = new Circle();
        circle.setCenterX(point.getX() * coefMulti);
        circle.setCenterY(point.getY() * coefMulti);
        circle.setRadius(2.0f);
        if (point instanceof Entrepot)
            circle.setFill(Paint.valueOf("RED"));
        this.group.getChildren().add(circle);
    }

    private void dessinerChemin(Chemin chemin) {
        Line line = new Line();
        line.setStartX(chemin.getDebut().getX() * coefMulti);
        line.setStartY(chemin.getDebut().getY() * coefMulti);
        line.setEndX(chemin.getFin().getX() * coefMulti);
        line.setEndY(chemin.getFin().getY() * coefMulti);
        this.group.getChildren().add(line);
    }

    private void genererCheminAleatoire(List<Point> points) {
        List<Chemin> chemins = new ArrayList<>();
        for (int i = 0; i < points.size() - 1; i++) {
            chemins.add(new Chemin(points.get(i), points.get(i + 1)));
        }
        chemins.add(new Chemin(points.get(points.size() - 1), points.get(0)));
        chemins.forEach(this::dessinerChemin);
        Double distanceTotale = 0.0;
        for (Chemin chemin : chemins) {
            distanceTotale += chemin.getDistance();
        }
        this.lblDistance.setText("Distance totale : " + distanceTotale);
    }
}
