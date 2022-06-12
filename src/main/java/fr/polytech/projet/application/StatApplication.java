package fr.polytech.projet.application;

import fr.polytech.projet.model.Solution;
import fr.polytech.projet.model.algorithmes.Algorithme;
import fr.polytech.projet.model.algorithmes.Tabou;
import fr.polytech.projet.outils.Lecture;
import fr.polytech.projet.outils.OutilsGraphe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class StatApplication {
	public static void main(String[] args) {
		List<String> fichiersAFaire = List.of("A3205.txt", "A3405.txt", "A4406.txt", "A6208.txt", "A6208.txt", "A8010.txt", "c101.txt", "c201.txt", "r101.txt");
//		List<String> fichiersAFaire = List.of("A3205.txt", "A3405.txt");
		int nbrTest = 1;
		int nbrIteration = 3000;
		Lecture lecture = new Lecture();
		List<String> fichiers = lecture.listerLesFichiersStr();
		fichiers.parallelStream()
				.filter(fichiersAFaire::contains)
				.forEach(fichier -> {
					List<Double> moyenne = new ArrayList<>();
					AtomicInteger nbThread = new AtomicInteger(nbrTest);
					List<Thread> threads = new ArrayList<>();
					for (int i = 0; i < nbrTest; i++) {
						threads.add(new Thread(() -> {
							Solution solution = OutilsGraphe.generateRandomSolution(lecture.lireFichier2(fichier));
							Algorithme tabou = new Tabou(solution);
							for (int j = 0; j < nbrIteration; j++) {
								tabou.update();
							}
							moyenne.add(tabou.getSolution().longueur());
							nbThread.getAndDecrement();
						}));
					}
					threads.forEach(Thread::start);
					while (nbThread.get() != 0) {
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					System.out.printf("Fichier : %s, Distance mini : %s \n%n", fichier, moyenne.stream().mapToDouble(Double::doubleValue).summaryStatistics().getAverage());

				});

	}

}
