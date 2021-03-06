package fr.polytech.projet.controller;

import fr.polytech.projet.HelloApplication;
import fr.polytech.projet.model.Chemin;
import fr.polytech.projet.model.Point;
import fr.polytech.projet.model.Solution;
import fr.polytech.projet.model.algorithmes.Algorithme;
import fr.polytech.projet.model.algorithmes.Recuit;
import fr.polytech.projet.model.algorithmes.Tabou;
import fr.polytech.projet.model.settings.Settings;
import fr.polytech.projet.outils.Lecture;
import fr.polytech.projet.outils.OutilsGraphe;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

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

	private final List<Double> solutionFitnessHistory = new ArrayList<>();

	private Stage newWindow;

	private final List<Class<?>> algos = List.of(Tabou.class, Recuit.class);
	private String fichier;
	private Solution solution;

	private Algorithme algorithme;
	private final AtomicBoolean stopRequested = new AtomicBoolean(false);
	@FXML
	protected Button btnValiderAlgo;

	public void setFichier(String fichier) {
		this.fichier = fichier;
		this.lblJeuChoisi.setText(String.format("Jeu choisi : %s", this.fichier));
	}

	private void initComboBoxAlgo() {
		cbChoixAlgo.setItems(FXCollections.observableArrayList(algos.stream().map(Class::getSimpleName).toList()));
		cbChoixAlgo.getSelectionModel().select(0);
	}

	private double tempsAttente;

	private void initBtn() {
		this.btnArret.setDisable(true);
		this.btnLancer.setDisable(true);
		this.btnPasAPas.setDisable(true);
	}

	/**
	 * Charge la liste des points, les dessine puis g??n??re les chemins initiaux
	 */
	private void chargerPoints() {
		Lecture lecture = new Lecture();
		solution = OutilsGraphe.generateRandomSolution(lecture.lireFichier2(this.fichier));
		solution.forEach(System.out::println);
		dessinerSolution(solution);
	}

	public void init() {
		initComboBoxAlgo();
		initBtn();
		chargerPoints();
		this.tempsAttente = this.sldSpeed.getValue();
		this.sldSpeed.valueProperty().addListener((observable, oldValue, newValue) -> sldSpeedOnDrag());
	}


	protected void sldSpeedOnDrag() {
		this.tempsAttente = this.sldSpeed.getValue();
		System.out.println(tempsAttente);
	}

	/**
	 * Dessine un point sur le graphique
	 * Si le point est un entrepot, il sera en rouge
	 *
	 * @param point Point ?? dessiner
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
	 * @param chemin chemin ?? dessiner
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

//		System.out.println("Capacit??s :");
//		for (int i = 0, size = solution.size(); i < size; ++i) {
//			System.out.println(i + " : " + solution.get(i).quantity());
//		}
	}

	@FXML
	protected void btnValiderAlgoOnClick() {
		this.btnValiderAlgo.setDisable(true);
		this.btnLancer.setDisable(false);
		this.btnPasAPas.setDisable(false);
		try {
			Settings.reloadSettings();
			this.initAlgo();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	protected void btnPasAPasOnClick() {
		solution.forEach(System.out::println);

		group.getChildren().clear();

		try {
			algorithme.update();
		} catch (Exception e) {
			e.printStackTrace();
		}

		lblDistance.setText(String.format("Longueur : %.3f", solution.longueur()));

		dessinerSolution(solution);
	}

	@FXML
	protected void btnLancerOnClick() {
		this.btnArret.setDisable(false);
		this.btnPasAPas.setDisable(true);
		this.btnLancer.setDisable(true);

		Thread algo_thread = new Thread(() -> {
			long attentemili = 0;
			int attenteNano = 0;
			stopRequested.set(false);
			long start = System.currentTimeMillis();
			while (!stopRequested.get()) {
				try {
					Thread.sleep(attentemili, attenteNano);
					synchronized (this) {
						if (!algorithme.update()) stopRequested.set(true);
						attentemili = (long) tempsAttente;
						attenteNano = (int) (tempsAttente - attentemili);
						this.solutionFitnessHistory.add(algorithme.getSolution().longueur());
					}
				} catch (Exception e) {
					e.printStackTrace();
					stopRequested.set(true);
				}
				Platform.runLater(() -> {
					synchronized (this) {

						group.getChildren().clear();
						lblDistance.setText(String.format("Longueur : %.3f", solution.longueur()));
						dessinerSolution(solution);

					}
				});
			}
			Platform.runLater(() -> {
				synchronized (this) {
					long end = System.currentTimeMillis();
					System.out.println("Temps : " + (end - start));
					System.out.println("Stop");
					group.getChildren().clear();
					lblDistance.setText(String.format("Longueur : %.3f", algorithme.getBestSolution().longueur()));
					dessinerSolution(algorithme.getBestSolution());
					afficherHistorique();
				}
			});
		});
		algo_thread.start();
	}

	@FXML
	protected void btnArretOnClick() {
		this.btnArret.setDisable(true);
		this.btnPasAPas.setDisable(false);
		this.btnLancer.setDisable(false);
		stopRequested.set(true);
		affichageHistorique();
	}

	@FXML
	protected void btnRetourOnClick() throws IOException {
		stopRequested.set(true);
		if (this.newWindow != null) {
			this.newWindow.close();
		}
		this.promptHelloApplication.start((Stage) btnRetour.getScene().getWindow());
	}

	private void initAlgo() {
		this.cbChoixAlgo.setDisable(true);
		if (algorithme == null) {
			Class<?> classeAlgoChoisi = this.algos.stream().filter(algo -> algo.getSimpleName().equals(this.cbChoixAlgo.getValue())).findFirst().orElseThrow();
			if (Recuit.class.equals(classeAlgoChoisi)) {
				this.algorithme = new Recuit(solution);
			} else if (Tabou.class.equals(classeAlgoChoisi)) {
				this.algorithme = new Tabou(solution);
			}
		}
	}


	/**
	 * Affiche l'historique de la fitness
	 */
	private void afficherHistorique() {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		StringSelection stringSelection = new StringSelection(this.solutionFitnessHistory
				.stream()
				.map(aDouble -> String.format("%.3f", aDouble))
				.collect(Collectors.joining("\n"))
				.replace('.', ','));
		clipboard.setContents(stringSelection, null);
		System.out.println("Donn??es copi??es sur le presse-papier");
	}

	private void affichageHistorique() {
		FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("scene/prompt-graph-view.fxml"));
		Scene scene = null;
		try {
			scene = new Scene(fxmlLoader.load(), 800, 800);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		PrompGraphController controller = fxmlLoader.getController();
		controller.setValue(new ArrayList<>(this.solutionFitnessHistory));
		newWindow = new Stage();
		newWindow.setTitle("Parametres");
		newWindow.setScene(scene);
		newWindow.show();
	}
}
