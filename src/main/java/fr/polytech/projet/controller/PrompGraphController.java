package fr.polytech.projet.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.util.List;

public class PrompGraphController {

	@FXML
	public Label lblOptimum;
	@FXML
	private LineChart<String, Double> lc_result;

	public void setValue(List<Double> value) {
		double valueMini = value.stream().mapToDouble(Double::doubleValue).min().orElseThrow();
		int iteMini = value.indexOf(valueMini);
		XYChart.Series<String, Double> series = new XYChart.Series<>();
		series.setName("résultats");
		for (int i = 0; i < value.size(); i++) {
			Double v = value.get(i);
			if (value.size() < 200) {
				series.getData().add(new XYChart.Data<>(String.valueOf(i), v));
			} else {
				if (i % (value.size() / 100) == 0 || v == valueMini) {
					series.getData().add(new XYChart.Data<>(String.valueOf(i), v));
				}
			}

		}
		lc_result.getData().add(series);
		lblOptimum.setText("Optimum : " + iteMini + " itérations");
	}
}
