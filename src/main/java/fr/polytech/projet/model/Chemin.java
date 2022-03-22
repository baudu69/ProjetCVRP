package fr.polytech.projet.model;

public class Chemin {
    private Point debut;
    private Point fin;

    public Chemin(Point debut, Point fin) {
        this.debut = debut;
        this.fin = fin;
        debut.setDepart(this);
        fin.setArrive(this);
    }

    public Point getDebut() {
        return debut;
    }

    public void setDebut(Point debut) {
        this.debut = debut;
        debut.setDepart(this);
    }

    public Point getFin() {
        return fin;
    }

    public void setFin(Point fin) {
        this.fin = fin;
        fin.setArrive(this);
    }

    public Double getDistance() {
        return Math.sqrt((fin.getX() - debut.getX()) * (fin.getX() - debut.getX()) + (fin.getY() - debut.getY()) * (fin.getY() - debut.getY()));
    }

}
