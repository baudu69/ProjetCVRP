package fr.polytech.projet.model;

import java.util.ArrayList;
import java.util.Map;

public class Solution extends ArrayList<Chemin> {

	private final Map<Integer, Point> points;

	public Solution(Map<Integer, Point> points) {
		this.points = points;
	}

	public Chemin getCheminContaining(Point point) {
		for (final Chemin chemin : this) {
			if (chemin.contains(point)) return chemin;
		}

		return null;
	}

	public Map<Integer, Point> getPoints() {
		return points;
	}

	public double longueur() {
		return this.stream()
				.mapToDouble(Chemin::longueur)
				.sum();
	}

}
