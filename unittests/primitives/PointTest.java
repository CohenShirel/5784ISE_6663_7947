package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;

class PointTest {

    Point  p1         = new Point(1, 2, 3);
    Point  p2         = new Point(2, 4, 6);
    Point  p3         = new Point(2, 4, 5);
    Vector v1         = new Vector(1, 2, 3);
    Vector v1Opposite = new Vector(-1, -2, -3);
    // Test point operations ================================================
    @Test
    void subtract() {
        // =============== Equivalence Partitions Tests ==============
        //TC01: test that two point are subtracted
        assertEquals(p2.subtract(p1), v1, "ERROR: (point2 - point1) does not work correctly");
        // ================ Boundary Values Tests ==================
        //TC11: test that point is subtracted from itself
        assertThrows(IllegalArgumentException.class, () -> p1.subtract(p1), //
                "ERROR: (point - itself) throws wrong exception");
    }

    @Test
    void add() {
        //============== Equivalence Partitions Tests ==============
        //TC01: test that two point are added
        assertEquals(p1.add(v1), p2, "ERROR: (point + vector) = other point does not work correctly");
        // ================ Boundary Values Tests ==================
        //TC11: test that point is added to itself
        assertEquals(Point.ZERO, p1.add(v1Opposite), "ERROR: (point + vector) = center of coordinates does not work correctly");
    }

    @Test
    void distanceSquared() {
        // =============== Equivalence Partitions Tests ==============
        //TC01: test that two point are subtracted
        assertEquals(9, p1.distanceSquared(p3), "ERROR: squared distance between points is wrong");
        //TC02: test that two point are subtracted
        assertEquals(9, p3.distanceSquared(p1), "ERROR: squared distance between points is wrong");
        // ================ Boundary Values Tests ==================
        //TC11: test that point squared distance from itself is zero
        assertEquals(0, p1.distanceSquared(p1), "ERROR: point squared distance to itself is not zero");

    }

    @Test
    void distance() {
        // =============== Equivalence Partitions Tests ==============
        //TC01: test that two point are subtracted
        assertEquals(3, p1.distance(p3), "ERROR: squared distance between points to itself is wrong");
        //TC02: test that two point are subtracted
        assertEquals(3, p3.distance(p1), "ERROR: squared distance between points to itself is wrong");
        // ================ Boundary Values Tests ==================
        //TC11: test that point distance from itself is zero
        assertEquals(0, p1.distance(p1), "ERROR: point distance to itself is not zero");

    }
}