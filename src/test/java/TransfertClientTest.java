import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import fr.polytech.projet.model.Chemin;
import fr.polytech.projet.model.Point;
import fr.polytech.projet.model.Solution;
import fr.polytech.projet.model.operation.TransfertClient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TransfertClientTest {

	@Test
	public void testTransfertClient() {
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

		final TransfertClient op1 = new TransfertClient(0, 1, 0, 3);
		final TransfertClient op2 = new TransfertClient(0, 1, 1, 3);
		final TransfertClient op3 = new TransfertClient(0, 1, 1, 5);
		final TransfertClient op4 = new TransfertClient(0, 1, 0, 5);
		final TransfertClient op5 = new TransfertClient(0, 3, 0, 3);
		final TransfertClient op6 = new TransfertClient(0, 4, 0, 1);

		assertEquals("TFRC{0,1;0,3}", op1.toString());
		assertEquals("TFRC{0,2;0,1}", op1.inverse().toString());

		assertEquals("TFRC{0,1;1,3}", op2.toString());
		assertEquals("TFRC{1,3;0,1}", op2.inverse().toString());

		assertEquals("TFRC{0,1;1,5}", op3.toString());
		assertEquals("TFRC{1,5;0,1}", op3.inverse().toString());

		assertEquals("TFRC{0,1;0,5}", op4.toString());
		assertEquals("TFRC{0,4;0,1}", op4.inverse().toString());

		assertEquals("TFRC{0,3;0,3}", op5.toString());
		assertEquals("TFRC{0,3;0,3}", op5.inverse().toString());

		assertEquals("TFRC{0,4;0,1}", op6.toString());
		assertEquals("TFRC{0,1;0,5}", op6.inverse().toString());


		assertEquals("Chemin{0-1-2-3-4-0}", chemin1.toString());
		assertEquals("Chemin{0-5-6-7-8-0}", chemin2.toString());


		op1.apply(solution);
		assertEquals("Chemin{0-2-1-3-4-0}", chemin1.toString());
		assertEquals("Chemin{0-5-6-7-8-0}", chemin2.toString());
		op1.inverse().apply(solution);

		assertEquals("Chemin{0-1-2-3-4-0}", chemin1.toString());
		assertEquals("Chemin{0-5-6-7-8-0}", chemin2.toString());


		op2.apply(solution);
		assertEquals("Chemin{0-2-3-4-0}", chemin1.toString());
		assertEquals("Chemin{0-5-6-1-7-8-0}", chemin2.toString());
		op2.inverse().apply(solution);

		assertEquals("Chemin{0-1-2-3-4-0}", chemin1.toString());
		assertEquals("Chemin{0-5-6-7-8-0}", chemin2.toString());


		op3.apply(solution);
		assertEquals("Chemin{0-2-3-4-0}", chemin1.toString());
		assertEquals("Chemin{0-5-6-7-8-1-0}", chemin2.toString());
		op3.inverse().apply(solution);

		assertEquals("Chemin{0-1-2-3-4-0}", chemin1.toString());
		assertEquals("Chemin{0-5-6-7-8-0}", chemin2.toString());


		op4.apply(solution);
		assertEquals("Chemin{0-2-3-4-1-0}", chemin1.toString());
		assertEquals("Chemin{0-5-6-7-8-0}", chemin2.toString());
		op4.inverse().apply(solution);

		assertEquals("Chemin{0-1-2-3-4-0}", chemin1.toString());
		assertEquals("Chemin{0-5-6-7-8-0}", chemin2.toString());


		op5.apply(solution);
		assertEquals("Chemin{0-1-2-3-4-0}", chemin1.toString());
		assertEquals("Chemin{0-5-6-7-8-0}", chemin2.toString());
		op5.inverse().apply(solution);

		assertEquals("Chemin{0-1-2-3-4-0}", chemin1.toString());
		assertEquals("Chemin{0-5-6-7-8-0}", chemin2.toString());
		

		op6.apply(solution);
		assertEquals("Chemin{0-4-1-2-3-0}", chemin1.toString());
		assertEquals("Chemin{0-5-6-7-8-0}", chemin2.toString());
		op6.inverse().apply(solution);

		assertEquals("Chemin{0-1-2-3-4-0}", chemin1.toString());
		assertEquals("Chemin{0-5-6-7-8-0}", chemin2.toString());

		assertThrows(IllegalArgumentException.class, () -> new TransfertClient(0, 0, 1, 2).apply(solution));
		assertThrows(IllegalArgumentException.class, () -> new TransfertClient(0, 5, 1, 2).apply(solution));
		assertThrows(IllegalArgumentException.class, () -> new TransfertClient(0, 2, 1, 0).apply(solution));
		assertThrows(IllegalArgumentException.class, () -> new TransfertClient(0, 2, 1, 6).apply(solution));
	}

}
