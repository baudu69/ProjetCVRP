package fr.polytech.projet.model;

import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChoixCouleur {
    public static final ChoixCouleur instance = new ChoixCouleur();
    private static final Random random = new Random();
    private List<Paint> couleurs = new ArrayList<>();

    private ChoixCouleur() {
        couleurs.add(Paint.valueOf("BLACK"));
        couleurs.add(Paint.valueOf("BLUE"));
        couleurs.add(Paint.valueOf("CYAN"));
        couleurs.add(Paint.valueOf("DARK_GRAY"));
        couleurs.add(Paint.valueOf("GRAY"));
        couleurs.add(Paint.valueOf("GREEN"));
        couleurs.add(Paint.valueOf("LIGHT_GRAY"));
        couleurs.add(Paint.valueOf("MAGENTA"));
        couleurs.add(Paint.valueOf("ORANGE"));
        couleurs.add(Paint.valueOf("PINK"));
        couleurs.add(Paint.valueOf("RED"));
        couleurs.add(Paint.valueOf("WHITE"));
        couleurs.add(Paint.valueOf("YELLOW"));
    }

    public Paint getRandomColor() {
        int index = random.nextInt(couleurs.size());
        Paint couleur = couleurs.get(index);
        couleurs.remove(couleur);
        return couleur;
    }
}
