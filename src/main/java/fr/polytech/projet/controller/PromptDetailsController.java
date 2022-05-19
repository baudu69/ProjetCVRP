package fr.polytech.projet.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.util.List;

public class PromptDetailsController {

	@FXML
	protected LineChart<Integer, Double> lcDistance;
	List<Double> listDistance;

	public void init() {
		XYChart.Series<Integer, Double> series = new XYChart.Series<>();
		for (int i = 0; i < listDistance.size(); i++) {
			series.getData().add(new XYChart.Data<>(i, listDistance.get(i)));
		}
		lcDistance.getData().add(series);
	}


}
