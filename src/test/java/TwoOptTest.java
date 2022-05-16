import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import fr.polytech.projet.model.Chemin;
import fr.polytech.projet.model.Point;
import fr.polytech.projet.model.Solution;
import fr.polytech.projet.model.operation.TwoOpt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TwoOptTest {

	private static String cheminToString(Chemin chemin) {
		return chemin.stream()
				.map(Point::id)
				.map(String::valueOf)
				.collect(Collectors.joining("-"));
	}

	@Test
	public void twoOptTest() {
		final Map<Integer, Point> pointMap = new HashMap<>();
		final Consumer<Point> createPoint = (Point p) -> pointMap.put(p.id(), p);
		for (int i = 0; i < 10; i++) createPoint.accept(new Point(0, 0, i, 0));

		final Chemin chemin1 = new Chemin();
		final Solution solution1 = new Solution(pointMap);
		solution1.add(chemin1);

		chemin1.add(pointMap.get(0));
		for (int i = 1; i < 10; i++) chemin1.add(pointMap.get(i));
		chemin1.add(pointMap.get(0));

		final TwoOpt op1 = new TwoOpt(pointMap.get(0), pointMap.get(4));
		final TwoOpt op2 = new TwoOpt(pointMap.get(1), pointMap.get(3));
		final TwoOpt op3 = new TwoOpt(pointMap.get(3), pointMap.get(1));
		final TwoOpt op4 = new TwoOpt(pointMap.get(2), pointMap.get(2));

		Assertions.assertEquals("TwoOpt{0;4}", op1.toString());
		Assertions.assertEquals("TwoOpt{0;4}", op1.inverse().toString());
		Assertions.assertEquals("TwoOpt{1;3}", op2.toString());
		Assertions.assertEquals("TwoOpt{1;3}", op2.inverse().toString());
		Assertions.assertEquals("TwoOpt{3;1}", op3.toString());
		Assertions.assertEquals("TwoOpt{3;1}", op3.inverse().toString());
		Assertions.assertEquals("TwoOpt{2;2}", op4.toString());
		Assertions.assertEquals("TwoOpt{2;2}", op4.inverse().toString());

		Assertions.assertEquals("0-1-2-3-4-5-6-7-8-9-0", cheminToString(chemin1));
		op1.apply(solution1);
		Assertions.assertEquals("0-3-2-1-4-5-6-7-8-9-0", cheminToString(chemin1));
		op1.inverse().apply(solution1);
		Assertions.assertEquals("0-1-2-3-4-5-6-7-8-9-0", cheminToString(chemin1));
		op2.apply(solution1);
		Assertions.assertEquals("0-1-2-3-4-5-6-7-8-9-0", cheminToString(chemin1));
		op2.inverse().apply(solution1);
		Assertions.assertEquals("0-1-2-3-4-5-6-7-8-9-0", cheminToString(chemin1));
		op3.apply(solution1);
		Assertions.assertEquals("0-1-2-3-4-5-6-7-8-9-0", cheminToString(chemin1));
		op3.inverse().apply(solution1);
		Assertions.assertEquals("0-1-2-3-4-5-6-7-8-9-0", cheminToString(chemin1));
		op4.apply(solution1);
		Assertions.assertEquals("0-1-2-3-4-5-6-7-8-9-0", cheminToString(chemin1));
		op4.inverse().apply(solution1);
		Assertions.assertEquals("0-1-2-3-4-5-6-7-8-9-0", cheminToString(chemin1));
	}

}
