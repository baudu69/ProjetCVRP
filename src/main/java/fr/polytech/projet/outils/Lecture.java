package fr.polytech.projet.outils;


import fr.polytech.projet.model.Entrepot;
import fr.polytech.projet.model.Point;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Lecture {
    private static final String chemin = "src/main/resources/fr/polytech/projet/donnees/";
    private final Function<String, Point> mapToItem = (line) -> {
        String[] p = line.split(";");
        int id = Integer.parseInt(p[0]);
        int x = Integer.parseInt(p[1]);
        int y = Integer.parseInt(p[2]);
        int q = Integer.parseInt(p[3]);
        if (id == 0) return new Entrepot(x, y, id, q);

        return new Point(x, y, id, q);
    };

    public List<Point> lireFichier(File fichier) {
        List<Point> inputList = new ArrayList<>();
        try {
            InputStream inputFS = new FileInputStream(fichier);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));
            inputList = br.lines().skip(1).map(mapToItem).collect(Collectors.toList());
            br.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return inputList;
    }

    public List<Point> lireFichier(String nomFicher) {
        File file = new File(chemin + nomFicher);
        return lireFichier(file);
    }

    public File[] listerLesFichiers() {
        File file = new File(chemin);
        return file.listFiles();
    }

    public List<String> listerLesFichiersStr() {
        return Arrays.stream(this.listerLesFichiers()).map(File::getName).toList();
    }
}
