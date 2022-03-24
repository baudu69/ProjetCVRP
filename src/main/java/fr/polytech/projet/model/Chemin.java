package fr.polytech.projet.model;

import fr.polytech.projet.controller.PromptResultViewController;
import javafx.scene.shape.Line;

import java.util.Objects;

public class Chemin {
    private Point debut;
    private Point fin;
    private Line line;

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

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    /**
     *
     */
    public void updateAffichage() {
        if (Objects.nonNull(line)) {
            line.setStartX(this.getDebut().getX() * PromptResultViewController.coefMulti);
            line.setStartY(this.getDebut().getY() * PromptResultViewController.coefMulti);
            line.setEndX(this.getFin().getX() * PromptResultViewController.coefMulti);
            line.setEndY(this.getFin().getY() * PromptResultViewController.coefMulti);
        }
    }
}
