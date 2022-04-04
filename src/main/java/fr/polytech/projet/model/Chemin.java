package fr.polytech.projet.model;

import java.util.*;

public class Chemin implements List<Point> {

	private final List<Point> points = new ArrayList<>();
	private boolean changed = false;

	/**
	 * Renvoie la longueur totale du chemin
	 */
	public double longueur() {
		double ret = 0;

		Point prev = null;
		for (Point p : points) {
			ret += p.distance(prev);

			prev = p;
		}

		return ret;
	}

	/**
	 * Renvoie le poids total des colis transportés
	 */
	public int quantity() {
		return points.stream()
				.mapToInt(Point::q)
				.sum();
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		
		if (o == null || getClass() != o.getClass()) return false;
		
		Chemin that = (Chemin) o;
		
		return Objects.equals(points, that.points);
	}
	@Override
	public int hashCode() {
		return Objects.hash(points);
	}
	
	
	
	
	@Override
	public Point get(int index) {
		return points.get(index);
	}
	@Override
	public Point set(int index, Point element) {
		changed = true;
		return points.set(index, element);
	}
	@Override
	public void add(int index, Point element) {
		changed = true;
		points.add(index, element);
	}
	@Override
	public Point remove(int index) {
		changed = true;
		return points.remove(index);
	}
	@Override
	public int indexOf(Object o) {
		return points.indexOf(o);
	}
	@Override
	public int lastIndexOf(Object o) {
		return points.lastIndexOf(o);
	}
	@Override
	public ListIterator<Point> listIterator() {
		return points.listIterator();
	}
	@Override
	public ListIterator<Point> listIterator(int index) {
		return points.listIterator(index);
	}
	@Override
	public List<Point> subList(int fromIndex, int toIndex) {
		return points.subList(fromIndex, toIndex);
	}

	@Override
	public int size() {
		return points.size();
	}
	@Override
	public boolean isEmpty() {
		return points.isEmpty();
	}
	@Override
	public boolean contains(Object o) {
		return points.contains(o);
	}
	@Override
	public Iterator<Point> iterator() {
		return points.iterator();
	}
	@Override
	public Object[] toArray() {
		return points.toArray();
	}
	@Override
	public <T> T[] toArray(T[] a) {
		return points.toArray(a);
	}
	@Override
	public boolean add(Point point) {
		changed = true;
		return points.add(point);
	}
	@Override
	public boolean remove(Object o) {
		changed = true;
		return points.remove(o);
	}
	@Override
	public boolean containsAll(Collection<?> c) {
		return points.containsAll(c);
	}
	@Override
	public boolean addAll(Collection<? extends Point> c) {
		changed = true;
		return points.addAll(c);
	}
	@Override
	public boolean addAll(int index, Collection<? extends Point> c) {
		changed = true;
		return points.addAll(index, c);
	}
	@Override
	public boolean removeAll(Collection<?> c) {
		changed = true;
		return points.removeAll(c);
	}
	@Override
	public boolean retainAll(Collection<?> c) {
		changed = true;
		return points.retainAll(c);
	}
	@Override
	public void clear() {
		changed = true;
		points.clear();
	}
}
