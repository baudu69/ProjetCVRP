package fr.polytech.projet.model.operation;

import java.util.Objects;
import java.util.Set;

import fr.polytech.projet.model.Chemin;
import fr.polytech.projet.model.Point;
import fr.polytech.projet.model.Solution;
import fr.polytech.projet.model.settings.Settings;

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
		return Objects.equals(Set.of(a, b), Set.of(swap.a, swap.b));
	}

	@Override
	public int hashCode() {
		return Objects.hash(Set.of(a, b));
	}

	@Override
	public String toString() {
		return "Swap{" +
				"a=" + a +
				", b=" + b +
				'}';
	}

	/**
	 * Check si le poids max n'est pas enfreint
	 *
	 * @param solution solution
	 * @return validite de l'operation
	 */
	@Override
	public boolean isValid(Solution solution) {
		boolean valid = true;
		apply(solution);
		if (solution.getCheminContaining(a).quantity() > OperationConstantes.CAPACITY_MAX) valid = false;
		if (solution.getCheminContaining(b).quantity() > OperationConstantes.CAPACITY_MAX) valid = false;
		inverse().apply(solution);
		return valid;
	}
}
