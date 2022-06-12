package fr.polytech.projet.model.operation;

import fr.polytech.projet.model.Chemin;
import fr.polytech.projet.model.Point;
import fr.polytech.projet.model.Solution;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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

	@Override
	public boolean isValid(Solution solution) {
		boolean valid = true;
		apply(solution);
		if (solution.getCheminContaining(a).quantity() > OperationConstantes.CAPACITY_MAX) valid = false;
		if (solution.getCheminContaining(b).quantity() > OperationConstantes.CAPACITY_MAX) valid = false;
		inverse().apply(solution);
		return valid;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SwapPath that = (SwapPath) o;
		return Objects.equals(Set.of(this.a, this.b), Set.of(that.a, that.b));
	}
	@Override
	public int hashCode() {
		return Objects.hash(Set.of(a, b));
	}
}
