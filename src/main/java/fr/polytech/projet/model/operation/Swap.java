package fr.polytech.projet.model.operation;

import fr.polytech.projet.model.Chemin;
import fr.polytech.projet.model.Point;
import fr.polytech.projet.model.Solution;

public class Swap implements Operation {
	private final Point a;
	private final Point b;

	public Swap(Point a, Point b) {
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

		final int iA = cA.indexOf(a);
		final int iB = cB.indexOf(b);
		
		cA.set(iA, b);
		cB.set(iB, a);
	}

	@Override
	public Operation inverse() {
		return this;
	}
}
