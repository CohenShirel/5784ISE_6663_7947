package primitives;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {

    Ray ray = new Ray(new Point(0, 0, 0), new Vector(1, 1, 1));
    List <Point> list;
    @Test
    void testFindClosestPoint() {
        // ================== Equivalence Partitions Tests =======
        // TC01: closest point in the middle of the list
        list = List.of(
                new Point(2,2,2),
                new Point(3,3,3),
                new Point(1,1,1),
                new Point(4,4,4),
                new Point(5,5,5));
        assertEquals(new Point(1,1,1), ray.findClosestPoint(list));

        // ====================== Boundary Values =====================
        // TC11: empty list
        list = null;
        assertNull(ray.findClosestPoint(list));

        // TC12: closest point in the start of the list
        list = List.of(
                new Point(1,1,1),
                new Point(2,2,2),
                new Point(3,3,3));
        assertEquals(new Point(1,1,1), ray.findClosestPoint(list));

        // TC13: closest point in the end of the list
        list = List.of(
                new Point(2,2,2),
                new Point(3,3,3),
                new Point(1,1,1));
        assertEquals(new Point(1,1,1), ray.findClosestPoint(list));

    }
}