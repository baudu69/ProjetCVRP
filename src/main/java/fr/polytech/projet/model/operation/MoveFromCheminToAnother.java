package fr.polytech.projet.model.operation;

import fr.polytech.projet.model.Chemin;
import fr.polytech.projet.model.Point;
import fr.polytech.projet.model.Solution;

import java.util.Objects;
import java.util.Random;

public class MoveFromCheminToAnother implements Operation {

	private final static Random random = new Random();
	private final Chemin chemin1;
	private final Chemin chemin2;
	private final Point point;
	private final Integer indexPointDepart;
	private final Integer indexPointArrive;

	public MoveFromCheminToAnother(Chemin chemin1, Chemin chemin2, Point point) {
		this.chemin1 = chemin1;
		this.chemin2 = chemin2;
		this.point = point;
		this.indexPointDepart = chemin1.indexOf(point);
		this.indexPointArrive = random.nextInt(chemin2.size());
		if (point.isDepot()) {
			throw new IllegalArgumentException("Point cannot be depot");
		}
	}

	private MoveFromCheminToAnother(Chemin chemin1, Chemin chemin2, Point point, int indexPointDepart, int indexPointArrive) {
		this.chemin1 = chemin1;
		this.chemin2 = chemin2;
		this.point = point;
		this.indexPointDepart = indexPointDepart;
		this.indexPointArrive = indexPointArrive;
		if (point.isDepot()) {
			throw new IllegalArgumentException("Point cannot be depot");
		}
	}

	@Override
	public void apply(Solution solution) {
		this.chemin1.remove(point);
		this.chemin2.add(indexPointArrive, point);
	}

	@Override
	public Operation inverse() {
		return new MoveFromCheminToAnother(chemin2, chemin1, point, indexPointArrive, indexPointDepart);
	}

	@Override
	public boolean isValid(Solution solution) {
		boolean valid = true;
		apply(solution);
		if (chemin1.quantity() > OperationConstantes.CAPACITY_MAX) valid = false;
		if (chemin2.quantity() > OperationConstantes.CAPACITY_MAX) valid = false;
		inverse().apply(solution);
		return valid;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		MoveFromCheminToAnother that = (MoveFromCheminToAnother) o;
		return Objects.equals(chemin1, that.chemin1) && Objects.equals(chemin2, that.chemin2) && Objects.equals(point, that.point);
	}

	@Override
	public int hashCode() {
		return Objects.hash(chemin1, chemin2, point);
	}
}
