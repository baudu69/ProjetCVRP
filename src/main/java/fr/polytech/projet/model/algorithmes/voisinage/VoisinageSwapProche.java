package fr.polytech.projet.model.algorithmes.voisinage;

import fr.polytech.projet.model.Chemin;
import fr.polytech.projet.model.Point;
import fr.polytech.projet.model.Solution;
import fr.polytech.projet.model.operation.MoveFromCheminToAnother;
import fr.polytech.projet.model.operation.Operation;
import fr.polytech.projet.model.operation.Swap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class VoisinageSwapProche extends Voisinage {

	private final static Random random = new Random();
	private int tailleVoisinage = 30;

	@Override
	public List<Operation> getVoisinage(Solution solution) {
		Point point1 = getRandomPoint(solution);
		List<Operation> op = getPointsPlusProche(solution, point1, tailleVoisinage - 2)
				.stream()
				.map(point2 -> (Operation) new Swap(point1, point2))
				.collect(Collectors.toList());
		op.addAll(bougerPointDeChemin(solution));
		return op;
	}

	/**
	 * Move proche (long a l'execution)
	 *
	 * @param solution solution
	 * @return liste d'op qui bougent un point d'un chemin a un autre
	 */
	private List<Operation> bougerPointDeChemin(Solution solution) {
		List<Operation> operationList = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			Point point1 = getRandomPoint(solution);
			Point point2 = getPointPlusProcheNotInNotChemin(solution, point1, solution.getCheminContaining(point1));
			operationList.add(new MoveFromCheminToAnother(solution.getCheminContaining(point1), solution.getCheminContaining(point2), point1));
		}
		return operationList;
	}

	/**
	 * Move random
	 *
	 * @param solution solution
	 * @return liste d'op qui bougent un point d'un chemin a un autre
	 */
	private List<Operation> bougerPointDeCheminRandom(Solution solution) {
		List<Operation> operationList = new ArrayList<>();
		for (int i = 0; i < 2; i++) {
			Point point1 = getRandomPoint(solution);
			Point point2 = getRandomPoint(solution);
			operationList.add(new MoveFromCheminToAnother(solution.getCheminContaining(point1), solution.getCheminContaining(point2), point1));
		}
		return operationList;
	}


	@Override
	public void applyParametre() {

	}

	/**
	 * @param point point
	 * @param nbr   nombre de points a renvoyer
	 * @return liste des points les plus proche de point
	 */
	private List<Point> getPointsPlusProche(Solution solution, Point point, int nbr) {
		List<Point> pointsPlusProches = new ArrayList<>();
		List<Point> points = solution.stream().map((chemin -> chemin.stream().toList()))
				.flatMap(Collection::parallelStream)
				.filter(point1 -> !point1.isDepot())
				.collect(Collectors.toList());
		for (int i = 0; i < nbr; i++) {
			pointsPlusProches.add(
					points.stream()
							.filter(point1 ->
									point1.distance(point) == points.stream()
											.mapToDouble(point2 -> point2.distance(point))
											.min()
											.orElseThrow()
							).findFirst()
							.orElseThrow()
			);
			points.remove(pointsPlusProches.get(i));
		}
		return pointsPlusProches;
	}

	/**
	 * @param solution solution
	 * @param point    point initial
	 * @param chemin   chemin dans lequel le point suivant ne dois pas se trouver
	 * @return point le plus proche qui ne se trouve pas dans le chemin
	 */
	private Point getPointPlusProcheNotInNotChemin(Solution solution, Point point, Chemin chemin) {
		//ALED LE TEMPS
//		List<Point> allPoints = getAllPointsWithoutDepot(solution);
//		return allPoints
//				.stream()
//				.filter(point1 -> !chemin.contains(point1))
//				.filter(point1 ->
//						point1.distance(point) == allPoints.stream()
//								.mapToDouble(point2 -> point2.distance(point))
//								.min()
//								.orElseThrow()
//				).findFirst()
//				.orElseThrow();

		return getPointsPlusProche(solution, point, 10).stream().filter(point1 -> !chemin.contains(point1)).findFirst().orElseThrow();

	}

	/**
	 * @param solution solution
	 * @return point aleatoire (non depot)
	 */
	private Point getRandomPoint(Solution solution) {
		List<Point> points = getAllPointsWithoutDepot(solution);
		return points.get(random.nextInt(points.size()));
	}

	/**
	 * @param solution solution
	 * @return tous les points de la solution (non depot)
	 */
	private List<Point> getAllPointsWithoutDepot(Solution solution) {
		return solution.stream().map((chemin -> chemin.stream().toList()))
				.flatMap(Collection::parallelStream)
				.filter(point1 -> !point1.isDepot())
				.toList();
	}
}
