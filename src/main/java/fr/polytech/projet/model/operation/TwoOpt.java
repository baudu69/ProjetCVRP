package fr.polytech.projet.model.operation;

import fr.polytech.projet.model.Chemin;
import fr.polytech.projet.model.Point;
import fr.polytech.projet.model.Solution;

public class TwoOpt implements Operation {

	private final Point a;
	private final Point b;

	public TwoOpt(Point a, Point b) {
		this.a = a;
		this.b = b;
	}

	@Override
	public void apply(Solution solution) {
		final Chemin cx = solution.getCheminContaining(a);
		final Chemin cy = solution.getCheminContaining(b);

		if (cx == null) throw new IllegalArgumentException("Point A not in solution");
		if (cy == null) throw new IllegalArgumentException("Point B not in solution");
		if (cx != cy) throw new IllegalArgumentException("Points A and B must be on the same path");

		final int ia = cx.indexOf(a);
		final int ib = cx.indexOf(b);

		final int min = Math.min(ia, ib) + 1;
		final int max = Math.max(ia, ib) - 1;

		// inverser le centre du chemin
		for (int i = min; i < max; i++) cx.add(i, cx.remove(max));
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
	public String toString() {
		return "TwoOpt{" + a.id() + ";" + b.id() + "}";
	}
}
