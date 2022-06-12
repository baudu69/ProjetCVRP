package fr.polytech.projet.application;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import fr.polytech.projet.model.Point;
import fr.polytech.projet.model.Solution;
import fr.polytech.projet.model.algorithmes.Algorithme;
import fr.polytech.projet.model.algorithmes.Recuit;
import fr.polytech.projet.model.settings.SettingsRecuit;
import fr.polytech.projet.outils.Lecture;
import fr.polytech.projet.outils.OutilsGraphe;

public class StatsRecuit {

	public static void main(String[] args) {
		final List<String> fichiersAFaire = List.of("A3205.txt", "A3405.txt", "A4406.txt", "A6208.txt", "A6208.txt", "A8010.txt", "c101.txt", "c201.txt", "r101.txt");
		final int nbTest = 1000;

		final Lecture lecture = new Lecture();

		final List<String> fichiers = lecture.listerLesFichiersStr();
		final int nbThreads = Runtime.getRuntime().availableProcessors();
		final AtomicInteger nbStartedThreads = new AtomicInteger(0);
		final List<Thread> threads = new ArrayList<>();

		final SettingsRecuit settings = new SettingsRecuit(20, .90, 100, 300,
				new SettingsRecuit.SettingsVoisinage(2, 4, 4, 1));
		
		System.out.println();
		System.out.println("Recuit settings: " + settings);
		System.out.println("Starting Recuit stats with " + nbThreads + " threads");
		System.out.println("\n");

		for (String fichier : fichiers.stream().filter(fichiersAFaire::contains).toList()) {
			System.out.println("File: " + fichier);

			final List<Double> moyenne = new ArrayList<>();

			final Map<Integer, Point> fichierLu = lecture.lireFichier2(fichier);

			for (int i = 0; i < nbTest; i++) {
				threads.add(new Thread(() -> {
					final Solution solution = OutilsGraphe.generateRandomSolution(fichierLu);
					final Algorithme recuit = new Recuit(solution, settings);
					while (recuit.update()) ;
					moyenne.add(recuit.getBestSolution().longueur());
					nbStartedThreads.decrementAndGet();
				}));
			}

			while (!threads.isEmpty()) {
				final Thread t = threads.remove(0);
				while (nbStartedThreads.get() == nbThreads) ;
				nbStartedThreads.incrementAndGet();
				t.start();
			}

			while (nbStartedThreads.get() > 0) ;

			final DoubleSummaryStatistics stats = moyenne.stream().mapToDouble(v -> v).summaryStatistics();

			System.out.printf("\taverage minimum: %.0f\n", stats.getAverage());
			System.out.printf("\tminimum: %.0f\n", stats.getMin());
			System.out.println("\n");
		}

		System.out.println("END");
	}

}
