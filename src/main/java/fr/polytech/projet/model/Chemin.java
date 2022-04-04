package fr.polytech.projet.model;

import java.util.ArrayList;

public class Chemin extends ArrayList<Point> {

    public int distance() {
        for (Point p : this) {

        }

        return -1;
    }

    public int quantity() {
        return this.stream()
                .mapToInt(Point::q)
                .sum();
    }

}
