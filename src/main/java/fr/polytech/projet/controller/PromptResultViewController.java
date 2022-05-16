package fr.polytech.projet.controller;

import fr.polytech.projet.HelloApplication;
import fr.polytech.projet.model.Chemin;
import fr.polytech.projet.model.Point;
import fr.polytech.projet.model.Solution;
import fr.polytech.projet.model.algorithmes.Algorithme;
import fr.polytech.projet.model.algorithmes.Recuit;
import fr.polytech.projet.model.algorithmes.Tabou;
import fr.polytech.projet.model.operation.Operation;
import fr.polytech.projet.model.operation.Swap;
import fr.polytech.projet.model.operation.TwoOpt;
import fr.polytech.projet.outils.Lecture;
import fr.polytech.projet.outils.OutilsGraphe;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class PromptResultViewController {

	public static final int coefMulti = 5;
	private final HelloApplication promptHelloApplication = new HelloApplication();
	@FXML
	protected Group group;
	@FXML
	protected Label lblDistance;
	@FXML
	protected Button btnLancer;
	@FXML
	protected Button btnArret;
	@FXML
	protected Button btnPasAPas;
	@FXML
	protected Label lblJeuChoisi;
	@FXML
	protected ComboBox<String> cbChoixAlgo;
	@FXML
	protected Slider sldSpeed;
	@FXML
	protected Button btnRetour;
	private String fichier;
	private Solution solution;

	private Algorithme algorithme;

	private List<Class<?>> algos = List.of(Tabou.class, Recuit.class);

	public void setFichier(String fichier) {
		this.fichier = fichier;
		this.lblJeuChoisi.setText(String.format("Jeu choisi : %s", this.fichier));
	}

	private void initComboBoxAlgo() {
		cbChoixAlgo.setItems(FXCollections.observableArrayList(algos.stream().map(Class::getSimpleName).toList()));
		cbChoixAlgo.getSelectionModel().select(0);
	}

	private void initBtn() {
		this.btnArret.setDisable(true);
	}

	public void init() {
		initComboBoxAlgo();
		initBtn();
		chargerPoints();
	}

	/**
	 * Charge la liste des points, les dessine puis genère les chemins initiaux
	 */
	private void chargerPoints() {
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
	protected void btnPasAPasOnClick(ActionEvent event) {
		initAlgo();
		solution.forEach(System.out::println);

		group.getChildren().clear();

		algorithme.update();

		lblDistance.setText(String.format("Longueur : %.3f", solution.longueur()));
		// System.out.println(solution.longueur());

		dessinerSolution(solution);
	}

	@FXML
	protected void btnLancerOnClick(ActionEvent event) {
		initAlgo();
		this.btnArret.setDisable(false);
		this.btnPasAPas.setDisable(true);
		this.btnLancer.setDisable(true);
	}

	@FXML
	protected void btnArretOnClick(ActionEvent event) {
		this.btnArret.setDisable(true);
		this.btnPasAPas.setDisable(false);
		this.btnLancer.setDisable(false);
	}

	@FXML
	protected void btnRetourOnClick(ActionEvent event) throws IOException {
		this.promptHelloApplication.start((Stage) btnRetour.getScene().getWindow());
	}

	private void initAlgo() {
		this.cbChoixAlgo.setDisable(true);
		if (algorithme == null) {
			Class<?> classeAlgoChoisi = this.algos.stream().filter(algo -> algo.getSimpleName().equals(this.cbChoixAlgo.getValue())).findFirst().orElseThrow();
			if (Recuit.class.equals(classeAlgoChoisi)) {
				this.algorithme = new Recuit(solution, 0.99, 900, 5);
			} else if (Tabou.class.equals(classeAlgoChoisi)) {
				this.algorithme = new Tabou();
			}
		}
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
