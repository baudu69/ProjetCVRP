package fr.polytech.projet.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.util.List;

public class PrompGraphController {

	@FXML
	private LineChart<String, Double> lc_result;

	public void setValue(List<Double> value) {
		XYChart.Series<String, Double> series = new XYChart.Series<>();
		series.setName("résultats");
		for (int i = 0; i < value.size(); i++) {
			Double v = value.get(i);
			series.getData().add(new XYChart.Data<>(String.valueOf(i), v));
		}
//		series.getData().add(new XYChart.Data<>(String.valueOf("test"), 3.5));
		lc_result.getData().add(series);

	}
}
