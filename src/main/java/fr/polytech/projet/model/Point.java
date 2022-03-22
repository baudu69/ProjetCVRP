package fr.polytech.projet.model;

import java.util.Objects;

public class Point {
    private final int id;
    private final int q;
    private final int x;
    private final int y;
    private Chemin depart;
    private Chemin arrive;

    public Point(int x, int y, int id, int q) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.q = q;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getId() {
        return id;
    }

    public int getQ() {
        return q;
    }

    public Chemin getDepart() {
        return depart;
    }

    public void setDepart(Chemin depart) {
        this.depart = depart;
    }

    public Chemin getArrive() {
        return arrive;
    }

    public void setArrive(Chemin arrive) {
        this.arrive = arrive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
