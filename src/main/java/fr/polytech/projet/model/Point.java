package fr.polytech.projet.model;


public record Point(int x, int y, int id, int q) {

    /**
     * @return true si depot
     */
    public boolean isDepot() {
        return this.id == 0;
    }
}
