package fr.polytech.projet.model.operation;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import fr.polytech.projet.model.Chemin;
import fr.polytech.projet.model.Point;
import fr.polytech.projet.model.Solution;
import fr.polytech.projet.model.settings.Settings;
import fr.polytech.projet.model.settings.SettingsTabou;

public class VoisinageFactoryTabou {

	private final SettingsTabou.SettingsVoisinage settings;

	public VoisinageFactoryTabou() {
		settings = Settings.getSettings().tabou().operations_voisinage();
	}

	public Set<Operation> getFullVoisinage(Solution solution) {
		final Set<Operation> ret = new HashSet<>();
		if (settings.swap()) ret.addAll(getFullVoisinageSwap(solution));
		if (settings.two_opt()) ret.addAll(getFullVoisinageTwoOpt(solution));
		if (settings.transfert_client()) ret.addAll(getFullVoisinageTransfertClient(solution));
		if (settings.swap_path()) ret.addAll(getFullVoisinageSwapPath(solution));
		return ret;
	}

	private Set<Swap> getFullVoisinageSwap(Solution solution) {
		final Set<Swap> ret = new HashSet<>();

		final Set<Point> points_utilises = new HashSet<>();

		for (final Point p1 : solution.getPoints().values()) {
			if (!p1.isDepot()) {
				points_utilises.add(p1);
				for (final Point p2 : solution.getPoints().values()) {
					if (!(p2.isDepot() || points_utilises.contains(p2))) {
						ret.add(new Swap(p1, p2));
					}
				}
			}
		}

		// Enlève les Swaps qui ne modifient pas la solution
		for (final Chemin c : solution) {
			if (c.size() == 4) ret.remove(new Swap(c.get(1), c.get(2)));
			if (c.size() == 5) ret.remove(new Swap(c.get(1), c.get(3)));
		}

		return ret;
	}

	private Set<TwoOpt> getFullVoisinageTwoOpt(Solution solution) {
		final Set<TwoOpt> ret = new HashSet<>();

		for (int k = 0; k < solution.size(); k++) {
			final Chemin c = solution.get(k);

			for (int i = 0; i < c.size() - 1; i++) {
				for (int j = i + 2; j < c.size() - 1; j++) {
					ret.add(new TwoOpt(k, i, j));
				}
			}

			if (c.size() > 3) ret.remove(new TwoOpt(k, 0, c.size() - 2));
		}

		return ret;
	}

	private Set<TransfertClient> getFullVoisinageTransfertClient(Solution solution) {
		final Set<TransfertClient> ret = new HashSet<>();

		final int size = solution.size();

		for (int i_c_src = 0; i_c_src < size; i_c_src++) {
			final Chemin c_src = solution.get(i_c_src);
			final int i_src_max = c_src.size() - 1;

			for (int i_src = 1; i_src < i_src_max; i_src++) {

				for (int i_c_dst = 0; i_c_dst < size; i_c_dst++) {
					final Chemin c_dst = solution.get(i_c_dst);
					final int i_dst_max = c_dst.size() - 1;

					for (int i_dst = 1; i_dst < i_dst_max; i_dst++) {

						if (!c_src.equals(c_dst) || i_dst - i_src < -1 || i_dst - i_src > 2) {
							ret.add(new TransfertClient(i_c_src, i_src, i_c_dst, i_dst));
						}

					}

				}

			}

			if (c_src.size() == 4) {
				ret.remove(new TransfertClient(i_c_src, 2, i_c_src, 1));
			}

		}

		return ret;
	}

	private Set<SwapPath> getFullVoisinageSwapPath(Solution solution) {
		return Collections.emptySet(); // TODO
	}


}