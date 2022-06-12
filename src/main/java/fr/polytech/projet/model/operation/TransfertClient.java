package fr.polytech.projet.model.operation;

import java.util.Objects;

import fr.polytech.projet.model.Chemin;
import fr.polytech.projet.model.Solution;

public class TransfertClient implements Operation {

	private final int indexCheminSrc;
	private final int indexSrc;
	private final int indexCheminDst;
	private final int indexDst;

	public TransfertClient(int indexCheminSrc, int indexSrc, int indexCheminDst, int indexDst) {
		this.indexCheminSrc = indexCheminSrc;
		this.indexSrc = indexSrc;
		this.indexCheminDst = indexCheminDst;
		this.indexDst = indexDst;
	}

	@Override
	public void apply(Solution solution) {
		final Chemin cheminSrc = solution.get(indexCheminSrc);
		final Chemin cheminDst = solution.get(indexCheminDst);

		if (indexSrc == 0) throw new IllegalArgumentException("Condition non vérifiée : indexSrc != 0");
		if (indexSrc >= cheminSrc.size() - 1) throw new IllegalArgumentException("Condition non vérifiée : indexSrc < cheminSrc.size() - 1");
		if (indexDst == 0) throw new IllegalArgumentException("Condition non vérifiée : indexDst != 0");
		if (indexDst >= cheminDst.size()) throw new IllegalArgumentException("Condition non vérifiée : indexDst < cheminDst.size() - 1");

		if (cheminDst == cheminSrc && indexDst > indexSrc) {
			cheminDst.add(indexDst - 1, cheminSrc.remove(indexSrc));
		} else {
			cheminDst.add(indexDst, cheminSrc.remove(indexSrc));
		}
	}

	@Override
	public Operation inverse() {
		if (indexCheminDst == indexCheminSrc) {
			if (indexDst > indexSrc) return new TransfertClient(indexCheminDst, indexDst - 1, indexCheminSrc, indexSrc);
			else if (indexDst < indexSrc) return new TransfertClient(indexCheminDst, indexDst, indexCheminSrc, indexSrc + 1);
		}

		return new TransfertClient(indexCheminDst, indexDst, indexCheminSrc, indexSrc);
	}

	@Override
	public boolean isValid(Solution solution) {
		boolean valid = true;

		this.apply(solution);

		if (solution.get(indexCheminDst).quantity() > OperationConstantes.CAPACITY_MAX) valid = false;

		this.inverse().apply(solution);

		return valid;
	}

	@Override
	public String toString() {
		return "TFRC{" + indexCheminSrc + "," + indexSrc + ";" + indexCheminDst + "," + indexDst + "}";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TransfertClient that = (TransfertClient) o;
		return indexCheminSrc == that.indexCheminSrc
				&& indexSrc == that.indexSrc
				&& indexCheminDst == that.indexCheminDst
				&& indexDst == that.indexDst;
	}

	@Override
	public int hashCode() {
		return Objects.hash(indexCheminSrc, indexSrc, indexCheminDst, indexDst);
	}

}
