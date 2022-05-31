package fr.polytech.projet.model.operation;

import java.util.ArrayList;
import java.util.List;

import fr.polytech.projet.model.Chemin;
import fr.polytech.projet.model.Point;
import fr.polytech.projet.model.Solution;

public class SwapPath implements Operation {

	private final Point a;
	private final Point b;

	public SwapPath(Point a, Point b) {
		if (a.isDepot()) throw new IllegalArgumentException("A cannot be Depot");
		if (b.isDepot()) throw new IllegalArgumentException("B cannot be Depot");

		this.a = a;
		this.b = b;
	}

	@Override
	public void apply(Solution solution) {
		final Chemin cA = solution.getCheminContaining(a);
		final Chemin cB = solution.getCheminContaining(b);

		if (cA == null) throw new IllegalArgumentException("Point A not in solution");
		if (cB == null) throw new IllegalArgumentException("Point B not in solution");
		
		if (cA == cB) return;
		
		int iA = cA.indexOf(a);
		int iB = cB.indexOf(b);
		
		List<Point> subPathA = cA.subList(iA, cA.size());
		List<Point> subPathB = cB.subList(iB, cB.size());
		
		List<Point> tmp = new ArrayList<>(subPathA);
		subPathA.clear();
		cA.addAll(subPathB);
		subPathB.clear();
		cB.addAll(tmp);
	}

	@Override
	public Operation inverse() {
		return this;
	}
}
