import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import fr.polytech.projet.model.Chemin;
import fr.polytech.projet.model.Point;
import fr.polytech.projet.model.Solution;
import fr.polytech.projet.model.operation.TwoOpt;
import fr.polytech.projet.model.operation.VoisinageFactoryTabou;
import fr.polytech.projet.model.settings.Settings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TwoOptTest {

	@Test
	public void twoOptTest() {
		final Map<Integer, Point> pointMap = new HashMap<>();
		final Consumer<Point> createPoint = (Point p) -> pointMap.put(p.id(), p);
		for (int i = 0; i < 9; i++) createPoint.accept(new Point(0, 0, i, 0));

		final Chemin chemin1 = new Chemin();
		final Chemin chemin2 = new Chemin();
		final Solution solution = new Solution(pointMap);
		solution.add(chemin1);
		solution.add(chemin2);

		chemin1.add(pointMap.get(0));
		chemin2.add(pointMap.get(0));
		for (int i = 1; i < 5; i++) {
			chemin1.add(pointMap.get(i));
			chemin2.add(pointMap.get(i + 4));
		}
		chemin1.add(pointMap.get(0));
		chemin2.add(pointMap.get(0));

		for (int i = 0; i < 6; ++i) {
			final int a = i;
			assertThrows(IllegalArgumentException.class, () -> new TwoOpt(0, a, 0));
			assertThrows(IllegalArgumentException.class, () -> new TwoOpt(1, a, 0));
		}

		final TwoOpt op1 = new TwoOpt(0, 0, 1);
		final TwoOpt op2 = new TwoOpt(1, 0, 1);
		final TwoOpt op3 = new TwoOpt(0, 0, 2);
		final TwoOpt op4 = new TwoOpt(1, 0, 2);
		final TwoOpt op5 = new TwoOpt(0, 0, 3);
		final TwoOpt op6 = new TwoOpt(1, 0, 3);
		final TwoOpt op7 = new TwoOpt(0, 0, 4);
		final TwoOpt op8 = new TwoOpt(1, 0, 4);
		final TwoOpt op9 = new TwoOpt(0, 0, 5);
		final TwoOpt op0 = new TwoOpt(1, 0, 5);

		assertEquals("TwoOpt{0;0;1}", op1.toString());
		assertEquals("TwoOpt{0;0;1}", op1.inverse().toString());
		assertEquals("TwoOpt{1;0;1}", op2.toString());
		assertEquals("TwoOpt{1;0;1}", op2.inverse().toString());
		assertEquals("TwoOpt{0;0;2}", op3.toString());
		assertEquals("TwoOpt{0;0;2}", op3.inverse().toString());
		assertEquals("TwoOpt{1;0;2}", op4.toString());
		assertEquals("TwoOpt{1;0;2}", op4.inverse().toString());
		assertEquals("TwoOpt{0;0;3}", op5.toString());
		assertEquals("TwoOpt{0;0;3}", op5.inverse().toString());
		assertEquals("TwoOpt{1;0;3}", op6.toString());
		assertEquals("TwoOpt{1;0;3}", op6.inverse().toString());
		assertEquals("TwoOpt{0;0;4}", op7.toString());
		assertEquals("TwoOpt{0;0;4}", op7.inverse().toString());
		assertEquals("TwoOpt{1;0;4}", op8.toString());
		assertEquals("TwoOpt{1;0;4}", op8.inverse().toString());
		assertEquals("TwoOpt{0;0;5}", op9.toString());
		assertEquals("TwoOpt{0;0;5}", op9.inverse().toString());
		assertEquals("TwoOpt{1;0;5}", op0.toString());
		assertEquals("TwoOpt{1;0;5}", op0.inverse().toString());

		assertEquals("Chemin{0-1-2-3-4-0}", chemin1.toString());
		assertEquals("Chemin{0-5-6-7-8-0}", chemin2.toString());

		op1.apply(solution);

		assertEquals("Chemin{0-1-2-3-4-0}", chemin1.toString());
		assertEquals("Chemin{0-5-6-7-8-0}", chemin2.toString());

		op2.apply(solution);

		assertEquals("Chemin{0-1-2-3-4-0}", chemin1.toString());
		assertEquals("Chemin{0-5-6-7-8-0}", chemin2.toString());

		op3.apply(solution);
		assertEquals("Chemin{0-2-1-3-4-0}", chemin1.toString());
		assertEquals("Chemin{0-5-6-7-8-0}", chemin2.toString());
		op3.inverse().apply(solution);

		assertEquals("Chemin{0-1-2-3-4-0}", chemin1.toString());
		assertEquals("Chemin{0-5-6-7-8-0}", chemin2.toString());

		op4.apply(solution);
		assertEquals("Chemin{0-1-2-3-4-0}", chemin1.toString());
		assertEquals("Chemin{0-6-5-7-8-0}", chemin2.toString());
		op4.inverse().apply(solution);

		assertEquals("Chemin{0-1-2-3-4-0}", chemin1.toString());
		assertEquals("Chemin{0-5-6-7-8-0}", chemin2.toString());

		op5.apply(solution);
		assertEquals("Chemin{0-3-2-1-4-0}", chemin1.toString());
		assertEquals("Chemin{0-5-6-7-8-0}", chemin2.toString());
		op5.inverse().apply(solution);

		assertEquals("Chemin{0-1-2-3-4-0}", chemin1.toString());
		assertEquals("Chemin{0-5-6-7-8-0}", chemin2.toString());

		op6.apply(solution);
		assertEquals("Chemin{0-1-2-3-4-0}", chemin1.toString());
		assertEquals("Chemin{0-7-6-5-8-0}", chemin2.toString());
		op6.inverse().apply(solution);

		assertEquals("Chemin{0-1-2-3-4-0}", chemin1.toString());
		assertEquals("Chemin{0-5-6-7-8-0}", chemin2.toString());

		op7.apply(solution);
		assertEquals("Chemin{0-4-3-2-1-0}", chemin1.toString());
		assertEquals("Chemin{0-5-6-7-8-0}", chemin2.toString());
		op7.inverse().apply(solution);

		assertEquals("Chemin{0-1-2-3-4-0}", chemin1.toString());
		assertEquals("Chemin{0-5-6-7-8-0}", chemin2.toString());

		op8.apply(solution);
		assertEquals("Chemin{0-1-2-3-4-0}", chemin1.toString());
		assertEquals("Chemin{0-8-7-6-5-0}", chemin2.toString());
		op8.inverse().apply(solution);

		assertEquals("Chemin{0-1-2-3-4-0}", chemin1.toString());
		assertEquals("Chemin{0-5-6-7-8-0}", chemin2.toString());

		assertThrows(IllegalArgumentException.class, () -> op9.apply(solution));
		assertThrows(IllegalArgumentException.class, () -> op0.apply(solution));
	}

}
