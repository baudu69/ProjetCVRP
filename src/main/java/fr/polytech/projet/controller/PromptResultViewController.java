package fr.polytech.projet.controller;

import fr.polytech.projet.model.Chemin;
import fr.polytech.projet.model.Point;
import fr.polytech.projet.model.Solution;
import fr.polytech.projet.model.operation.Operation;
import fr.polytech.projet.model.operation.Swap;
import fr.polytech.projet.model.operation.TwoOpt;
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

import java.util.Arrays;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

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
//        Chemin chemin = OutilsGraphe.genererSolutionAleatoire(lecture.lireFichier(this.fichier));
//        solution = genererSolution(chemin);
        solution = OutilsGraphe.generateRandomSolution(lecture.lireFichier2(this.fichier));
        dessinerSolution(solution);
    }

    private Solution genererSolution(Chemin chemin) {
        Random random = new Random();
        Solution solution = new Solution(null);
        
        final int nbCamions = chemin.nbCamionMinimum(100) * 2;

        for (int i = 0; i < nbCamions; i++) {
            Chemin points = new Chemin();
            points.add(chemin.get(0));
            solution.add(points);
        }
        
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
        final int size = chemin.size();
        for (int i = 0; i < size; i++) {
            Line line = new Line();
            line.setStartX(chemin.get(i).x() * coefMulti);
            line.setStartY(chemin.get(i).y() * coefMulti);
            line.setEndX(chemin.get((i + 1) % size).x() * coefMulti);
            line.setEndY(chemin.get((i + 1) % size).y() * coefMulti);
            line.setFill(chemin.getCouleur());
            line.setStroke(chemin.getCouleur());
            line.setStrokeWidth(3);
            this.group.getChildren().add(line);
        }
    }
    
    
    private void dessinerSolution(Solution solution) {
        solution.forEach(this::dessinerChemin);
        solution.forEach(chemin1 -> chemin1.forEach(this::dessinerPoint));
    }


    @FXML
    protected void btnLancerOnClick(ActionEvent event) {
        solution.forEach(System.out::println);
        
		group.getChildren().clear();
        
        test();
        
        System.out.println(solution.longueur());
        
        dessinerSolution(solution);
    }
    
    private void test() {
        Operation operation = null;

        double longueurMin = solution.longueur();

        for (int i = 1; i < solution.getPoints().size(); i++) {
            final Point point = solution.getPoints().get(i);
            final Set<Point> voisins = solution.getPointsInRadius(point, 4000);
            final Chemin chemin = solution.getCheminContaining(point);

            // System.out.println(voisins.size());

            for (Point voisin : voisins) {
				final Chemin cheminVoisin = solution.getCheminContaining(voisin);
                
                final boolean sameChemin = chemin == cheminVoisin;
                Operation _2opt = new TwoOpt(point, voisin);
                Operation _swap = new Swap(point, voisin);
                
                if (sameChemin) {
                    _2opt.apply(solution);
                    if (solution.longueur() < longueurMin) {
                        operation = _2opt;
                        longueurMin = solution.longueur();
                    }
                    _2opt.inverse().apply(solution);
                }
                
                _swap.apply(solution);
                if (solution.longueur() < longueurMin) {
                    operation = _swap;
                    longueurMin = solution.longueur();
                }
                _swap.inverse().apply(solution);
            }
        }

        if (operation != null) {
            operation.apply(solution);
        }
    }
    
	/*
    private void test2opt() {
        Operation operation = null;

        double longueurMin = solution.longueur();

        for (int i = 1; i < solution.getPoints().size(); i++) {
            final Point point = solution.getPoints().get(i);
            final Set<Point> voisins = solution.getPointsInRadius(point, 40);

            System.out.println(voisins.size());

            for (Point voisin : voisins) {
                Operation _2opt = new TwoOpt(point, voisin);
                _2opt.apply(solution);
                if (solution.longueur() < longueurMin) {
                    operation = _2opt;
                    longueurMin = solution.longueur();
                }
                _2opt.inverse().apply(solution);
            }
        }

        if (operation != null) {
            operation.apply(solution);
        }
    }
	//*/

    private void testSwap() {
        Operation operation = null;

        double longueurMin = solution.longueur();

        for (int i = 1; i < solution.getPoints().size(); i++) {
            final Point point = solution.getPoints().get(i);
            final Set<Point> voisins = solution.getPointsInRadius(point, 40);

            System.out.println(voisins.size());

            for (Point voisin : voisins) {
                Operation swap = new Swap(point, voisin);
                swap.apply(solution);
                if (solution.longueur() < longueurMin) {
                    operation = swap;
                    longueurMin = solution.longueur();
                }
                swap.inverse().apply(solution);
            }
        }

        if (operation != null) {
            operation.apply(solution);
        }
    }

}
