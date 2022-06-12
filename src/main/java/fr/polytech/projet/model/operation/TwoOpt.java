package fr.polytech.projet.model.operation;

import java.util.Objects;

import fr.polytech.projet.model.Chemin;
import fr.polytech.projet.model.Solution;

public class TwoOpt implements Operation {

	private final int chemin_index;
	private final int ia;
	private final int ib;

	/**
	 * On enlève les arêtes qui suivent les points passés en paramètre.
	 */
	public TwoOpt(int chemin_index, int ia, int ib) {
		if (ia >= ib) throw new IllegalArgumentException("Condition non vérifiée : ia < ib");
		this.chemin_index = chemin_index;
		this.ia = ia;
		this.ib = ib;
	}


	@Override
	public void apply(Solution solution) {
		final Chemin chemin = solution.get(chemin_index);
		if (ib >= chemin.size() - 1) throw new IllegalArgumentException("Condition non vérifiée : ib < chemin.size() - 1");

		// inverser le centre du chemin
		for (int i = ia + 1; i < ib; i++) chemin.add(i, chemin.remove(ib));
	}

	@Override
	public Operation inverse() {
		return this;
	}

	@Override
	public boolean isValid(Solution solution) {
		boolean valid = true;
		apply(solution);
		if (solution.get(chemin_index).quantity() > OperationConstantes.CAPACITY_MAX) valid = false;
		inverse().apply(solution);
		return valid;
	}

	@Override
	public String toString() {
		return "TwoOpt{" + chemin_index + ";" + ia + ";" + ib + "}";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TwoOpt that = (TwoOpt) o;
		return this.chemin_index == that.chemin_index
				&& this.ia == that.ia
				&& this.ib == that.ib;
	}

	@Override
	public int hashCode() {
		return Objects.hash(chemin_index, ia, ib);
	}
}
