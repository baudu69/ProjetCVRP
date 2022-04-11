package fr.polytech.projet.model.operation;

import fr.polytech.projet.model.Solution;

public interface Operation {
	void apply(Solution solution);
	Operation inverse();
}
