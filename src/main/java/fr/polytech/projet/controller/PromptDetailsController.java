package fr.polytech.projet.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

public class PromptDetailsController {

	@FXML
	protected LineChart<Integer, Double> lcDistance;

	private final XYChart.Series<Integer, Double> series = new XYChart.Series<>();
	private int i = 0;

	public void init() {
		lcDistance.getData().add(series);
	}

	public void addDistance(Double value) {
		series.getData().add(new XYChart.Data<>(i, value));
		i++;
	}


}
