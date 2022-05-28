package fr.polytech.projet.model.operation;

import fr.polytech.projet.model.Chemin;
import fr.polytech.projet.model.Point;
import fr.polytech.projet.model.Solution;

import java.util.Objects;

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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Swap swap = (Swap) o;
		return Objects.equals(a, swap.a) && Objects.equals(b, swap.b);
	}

	@Override
	public int hashCode() {
		return Objects.hash(a, b);
	}

	@Override
	public String toString() {
		return "Swap{" +
				"a=" + a +
				", b=" + b +
				'}';
	}
}
