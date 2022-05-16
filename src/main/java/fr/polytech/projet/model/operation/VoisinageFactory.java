package fr.polytech.projet.model.operation;

import java.util.Random;

import fr.polytech.projet.model.Solution;

public class VoisinageFactory {

	private final double w_swap = 1;
	private final double w_2opt = 1;
	private final double w_sum;
	
	public VoisinageFactory() {
		w_sum = w_swap + w_2opt;
	}

	private final Random random = new Random();

	public static Operation getRandomVoisinage(Solution solution) {
		// TODO
		return Operation.NOP;
	}

}
