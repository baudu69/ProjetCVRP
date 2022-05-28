package fr.polytech.projet.model.operation;

import fr.polytech.projet.model.Solution;

public interface Operation {
	void apply(Solution solution);
	Operation inverse();

	Operation NOP = new Operation() {
		@Override
		public void apply(Solution solution) {
		}

		@Override
		public Operation inverse() {
			return this;
		}

		@Override
		public boolean isValid(Solution solution) {
			return true;
		}
	};

	boolean isValid(Solution solution);
}
