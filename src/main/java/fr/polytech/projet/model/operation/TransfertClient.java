package fr.polytech.projet.model.operation;

import fr.polytech.projet.model.Chemin;
import fr.polytech.projet.model.Solution;

public class TransfertClient implements Operation {

	private final Chemin cheminSrc;
	private final int indexSrc;
	private final Chemin cheminDst;
	private final int indexDst;

	public TransfertClient(Chemin cheminSrc, int indexSrc, Chemin cheminDst, int indexDst) {
		this.cheminSrc = cheminSrc;
		this.indexSrc = indexSrc;
		this.cheminDst = cheminDst;
		this.indexDst = indexDst;
	}

	@Override
	public void apply(Solution solution) {
		if (indexSrc == 0) throw new IllegalArgumentException("indexSrc cannot be 0");
		if (indexSrc >= cheminSrc.size() - 1) throw new IllegalArgumentException("indexSrc cannot be >= cheminSrc.size() - 1");
		if (indexDst == 0) throw new IllegalArgumentException("indexDst cannot be 0");
		if (indexDst >= cheminDst.size()) throw new IllegalArgumentException("indexDst cannot be >= cheminDst.size()");

		if (cheminDst == cheminSrc && indexDst > indexSrc) {
			cheminDst.add(indexDst - 1, cheminSrc.remove(indexSrc));
		} else {
			cheminDst.add(indexDst, cheminSrc.remove(indexSrc));
		}
	}

	@Override
	public Operation inverse() {
		if (cheminDst == cheminSrc) {
			if (indexDst > indexSrc) return new TransfertClient(cheminDst, indexDst - 1, cheminSrc, indexSrc);
			else if (indexDst < indexSrc) return new TransfertClient(cheminDst, indexDst, cheminSrc, indexSrc + 1);
		}

		return new TransfertClient(cheminDst, indexDst, cheminSrc, indexSrc);
	}

	@Override
	public boolean isValid(Solution solution) {
		boolean valid = true;

		this.apply(solution);

		if (cheminDst.quantity() > OperationConstantes.CAPACITY_MAX) valid = false;

		this.inverse().apply(solution);

		return valid;
	}

	@Override
	public String toString() {
		return "TFRC{" + indexSrc + "," + indexDst + "}";
	}
}
