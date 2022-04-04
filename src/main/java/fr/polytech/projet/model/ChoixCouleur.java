package fr.polytech.projet.model;

import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChoixCouleur {
	private static final Random random = new Random();

	private ChoixCouleur() {
	}

	public static Paint getRandomColor() {
		return javafx.scene.paint.Color.color(random.nextFloat(), random.nextFloat(), random.nextFloat());
	}
}
