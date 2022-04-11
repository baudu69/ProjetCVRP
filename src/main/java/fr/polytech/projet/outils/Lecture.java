package fr.polytech.projet.outils;


import fr.polytech.projet.model.Chemin;
import fr.polytech.projet.model.Point;

import java.io.*;
import java.util.*;
import java.util.function.Function;

public class Lecture {
    private static final String chemin = "src/main/resources/fr/polytech/projet/donnees/";

    private final Function<String, Point> mapToItem = (line) -> {
        String[] p = line.split(";");
        int id = Integer.parseInt(p[0]);
        int x = Integer.parseInt(p[1]);
        int y = Integer.parseInt(p[2]);
        int q = Integer.parseInt(p[3]);

        return new Point(x, y, id, q);
    };

    /**
     * Renvoie la liste des points d'un fichier
     * Le point avec l'ID 0 est un objet de type Entrepot
     *
     * @param fichier fichier voulu
     * @return Liste de points avec un entrepot
     */
    public Chemin lireFichier(File fichier) {
        Chemin inputList = new Chemin();
        try (InputStream inputFS = new FileInputStream(fichier);
             BufferedReader br = new BufferedReader(new InputStreamReader(inputFS))
        ) {
            inputList.addAll(br.lines().skip(1).map(mapToItem).toList());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return inputList;
    }

    /**
     * Renvoie la liste des points d'un fichier
     * Le point avec l'ID 0 est un objet de type Entrepot
     *
     * @param nomFicher nom du fichier voulu
     * @return Liste de points avec un entrepot
     */
    public Chemin lireFichier(String nomFicher) {
        File file = new File(chemin + nomFicher);
        return lireFichier(file);
    }

    public Map<Integer, Point> lireFichier2(String nomFichier) {
        final File file = new File(chemin + nomFichier);
		
	    try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
		    final Map<Integer, Point> ret = new HashMap<>();

		    reader.lines()
		            .map(mapToItem)
		            .forEach(point -> ret.put(point.id(), point));
			
			return ret;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Collections.emptyMap();
    }

    /**
     * Liste les fichiers de données présent dans le dossier ressource
     *
     * @return Tableau de Fichier
     */
    public File[] listerLesFichiers() {
        File file = new File(chemin);
        return file.listFiles();
    }

    /**
     * Liste les noms des fichiers de données présent dans le dossier ressource
     *
     * @return Tableau de noms de fichiers
     */
    public List<String> listerLesFichiersStr() {
        return Arrays.stream(this.listerLesFichiers()).map(File::getName).toList();
    }
}
