import fr.polytech.projet.model.Chemin;
import fr.polytech.projet.model.Point;
import fr.polytech.projet.model.Solution;
import fr.polytech.projet.model.operation.Operation;
import fr.polytech.projet.model.operation.Swap;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MoveFromCheminToAnotherTest {


    @Test
    void testClone() {
        Point point1 = new Point(0, 0, 0, 0);
        Point point2 = new Point(1, 1, 1, 1);
        Point point3 = new Point(2, 2, 2, 2);
        Point point4 = new Point(3, 3, 3, 3);
        Point point5 = new Point(4, 4, 4, 4);
        Point point6 = new Point(5, 5, 5, 5);
        Point point7 = new Point(6, 6, 6, 6);
        Point point8 = new Point(7, 7, 7, 7);
        Point point9 = new Point(8, 8, 8, 8);
        Point point10 = new Point(9, 9, 9, 9);
        Chemin chemin1 = new Chemin();
        chemin1.add(point1);
        chemin1.add(point2);
        chemin1.add(point3);
        chemin1.add(point4);
        chemin1.add(point5);
        Chemin chemin2 = new Chemin();
        chemin2.add(point6);
        chemin2.add(point7);
        chemin2.add(point8);
        chemin2.add(point9);
        chemin2.add(point10);
        Solution solution = new Solution(new HashMap<>());
        solution.add(chemin1);
        solution.add(chemin2);
        Solution solution1 = (Solution) solution.clone();


        Operation swap = new Swap(point2, point6);
        swap.apply(solution);
        assertTrue(solution.get(1).contains(point2));
        assertFalse(solution1.get(1).contains(point2));

    }

}