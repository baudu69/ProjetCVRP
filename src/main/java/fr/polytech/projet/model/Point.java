package fr.polytech.projet.model;


public record Point(int x, int y, int id, int q) {

	/**
	 * @return {@code true} si depot
	 */
	public boolean isDepot() {
		return this.id == 0;
	}

	/**
	 * 
	 * @param that
	 * @return La distance avec le point passé en paramètre
	 */
	public double distance(final Point that) {
		if (that == null) return 0;
		
		final int dx = x - that.x;
		final int dy = y - that.y;
		
		return Math.sqrt(dx * dx + dy * dy);
	}

}
