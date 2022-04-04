package fr.polytech.projet.controller;

import fr.polytech.projet.model.Chemin;
import fr.polytech.projet.model.Point;
import fr.polytech.projet.outils.Lecture;
import fr.polytech.projet.outils.OutilsGraphe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class PromptResultViewController {

    public static final int coefMulti = 8;

    @FXML
    protected Group group;

    @FXML
    protected Label lblDistance;

    @FXML
    protected Button btnLancer;

    private String fichier;

    private Chemin chemin;

    public void setFichier(String fichier) {
        this.fichier = fichier;
    }

    /**
     * Charge la liste des points, les dessine puis genère les chemins initiaux
     */
    public void chargerPoints() {
        Lecture lecture = new Lecture();
        chemin = OutilsGraphe.genererSolutionAleatoire(lecture.lireFichier(this.fichier));
        this.dessinerChemin(chemin);
        chemin.forEach(this::dessinerPoint);

    }

    /**
     * Dessine un point sur le graphique
     * Si le point est un entrepot, il sera en rouge
     *
     * @param point Point à dessiner
     */
    private void dessinerPoint(Point point) {
        Circle circle = new Circle();
        circle.setCenterX(point.x() * coefMulti);
        circle.setCenterY(point.y() * coefMulti);
        circle.setRadius(4.0f);
        if (point.isDepot())
            circle.setFill(Paint.valueOf("RED"));
        this.group.getChildren().add(circle);
    }

    /**
     * Dessine un chemin sur le graphique
     *
     * @param chemin chemin à dessiner
     */
    private void dessinerChemin(Chemin chemin) {
        for (int i = 0; i < chemin.size() - 1; i++) {
            Line line = new Line();
            line.setStartX(chemin.get(i).x() * coefMulti);
            line.setStartY(chemin.get(i).y() * coefMulti);
            line.setEndX(chemin.get(i + 1).x() * coefMulti);
            line.setEndY(chemin.get(i + 1).y() * coefMulti);
            this.group.getChildren().add(line);
        }
        Line line = new Line();
        line.setStartX(chemin.get(0).x() * coefMulti);
        line.setStartY(chemin.get(0).y() * coefMulti);
        line.setEndX(chemin.get(chemin.size() - 1).x() * coefMulti);
        line.setEndY(chemin.get(chemin.size() - 1).y() * coefMulti);
        this.group.getChildren().add(line);


    }


    @FXML
    protected void btnLancerOnClick(ActionEvent event) {

    }
}
