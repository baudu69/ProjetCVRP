package fr.polytech.projet.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

	public Set<Point> getPointsInRadius(Point point, double radius) {
		final Set<Point> ret = new HashSet<>();

		for (Point other : points.values()) {
			if (other != point && point.distance(other) <= radius && other.id() > 0) {
				ret.add(other);
			}
		}

		return ret;
	}

	public Map<Integer, Point> getPoints() {
		return points;
	}

	public double longueur() {
		return this.stream()
				.mapToDouble(Chemin::longueur)
				.sum();
	}

	@Override
	public Object clone() {
		Solution solution = new Solution(points);
		solution.addAll(this.stream().map(chemin -> (Chemin) chemin.clone()).toList());
		return solution;
	}
}
