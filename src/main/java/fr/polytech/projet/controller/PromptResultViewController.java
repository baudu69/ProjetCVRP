package fr.polytech.projet.controller;

import fr.polytech.projet.model.Chemin;
import fr.polytech.projet.model.Point;
import fr.polytech.projet.model.Solution;
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

import java.util.Random;
import java.util.stream.IntStream;

public class PromptResultViewController {

    public static final int coefMulti = 8;

    @FXML
    protected Group group;

    @FXML
    protected Label lblDistance;

    @FXML
    protected Button btnLancer;

    private String fichier;

    private Solution solution;

    public void setFichier(String fichier) {
        this.fichier = fichier;
    }

    /**
     * Charge la liste des points, les dessine puis genère les chemins initiaux
     */
    public void chargerPoints() {
        Lecture lecture = new Lecture();
        Chemin chemin = OutilsGraphe.genererSolutionAleatoire(lecture.lireFichier(this.fichier));
        solution = genererSolution(chemin);
        solution.forEach(this::dessinerChemin);
        solution.forEach(chemin1 -> chemin1.forEach(this::dessinerPoint));

    }

    private Solution genererSolution(Chemin chemin) {
        Random random = new Random();
        Solution solution = new Solution();
        IntStream
                .range(0, chemin.nbCamionMinimum(100) * 2)
                .mapToObj(i -> new Chemin())
                .forEach(chemin1 -> {
                    chemin1.add(chemin.get(0));
                    solution.add(chemin1);
                });
        for (Point point : chemin) {
            boolean passe = false;
            do {
                Chemin camionChoisi = solution.get(random.nextInt(solution.size()));
                if (camionChoisi.quantity() + point.q() <= 100) {
                    passe = true;
                    camionChoisi.add(point);
                }
            } while (!passe);
        }

        //Ajout de l'entrepot a la fin de chaque chemin
        solution.forEach(chemin1 -> chemin1.add(chemin.get(0)));
        return solution;
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
            line.setFill(chemin.getCouleur());
            line.setStroke(chemin.getCouleur());
            this.group.getChildren().add(line);
        }
        Line line = new Line();
        line.setStartX(chemin.get(0).x() * coefMulti);
        line.setStartY(chemin.get(0).y() * coefMulti);
        line.setEndX(chemin.get(chemin.size() - 1).x() * coefMulti);
        line.setEndY(chemin.get(chemin.size() - 1).y() * coefMulti);
        line.setStroke(chemin.getCouleur());
        line.setFill(chemin.getCouleur());
        this.group.getChildren().add(line);


    }


    @FXML
    protected void btnLancerOnClick(ActionEvent event) {

    }
}
