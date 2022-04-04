package fr.polytech.projet.model;

import javafx.scene.paint.Paint;

import java.util.ArrayList;

public class Chemin extends ArrayList<Point> {

    private final Paint couleur = ChoixCouleur.instance.getRandomColor();

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

    /**
     * Renvoi le nombre de camions minimums necessaires pour tout transporter
     *
     * @param C Poids maximum que peut prendre un camion
     * @return nombre de camions
     */
    public int nbCamionMinimum(int C) {
        return this.quantity() % C == 0 ? this.quantity() / C : this.quantity() / C + 1;
    }

}
