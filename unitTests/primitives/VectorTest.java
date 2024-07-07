package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

class VectorTest {
    Vector v1         = new Vector(1, 2, 3);
    Vector v1Opposite = new Vector(-1, -2, -3);
    Vector v2         = new Vector(-2, -4, -6);
    Vector v3         = new Vector(0, 3, -2);
    Vector v4         = new Vector(1, 2, 2);

    /**
     * Test the zeroVector() method of the Vector class.
     */
    @Test
    void testZeroVector() {
        //TC01: test zero vector first constructor
        assertThrows(IllegalArgumentException.class, () -> new Vector(0, 0, 0), "ERROR: zero vector throws wrong exception");
        //TC02: test zero vector second constructor
        assertThrows(IllegalArgumentException.class, () -> new Vector(Double3.ZERO) , "ERROR: zero vector throws wrong exception");
    }

    /**
     * Test the add() method of the Vector class.
     */
    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: test that two vector are added
        assertEquals(v1Opposite,v1.add(v2),"ERROR: Vector + Vector does not work correctly");

        // ================== Boundary Values Tests ==================
        // TC11: test that vector is added to itself
        assertThrows(IllegalArgumentException.class, () -> v1.add(v1Opposite), "ERROR: Vector + itself throws wrong exception");
    }

    /**
     * Test the subtract() method of the Vector class.
     */
    @Test
    void testSubtract() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: test that two vector are subtracted
        assertEquals(new Vector(3, 6, 9),v1.subtract(v2),"ERROR: Vector - Vector does not work correctly");

        // ================== Boundary Values Tests ==================
        // TC11: test that vector is subtracted from itself
        assertThrows(IllegalArgumentException.class, () -> v1.subtract(v1), "ERROR: Vector + itself throws wrong exception");

    }

    /**
     * Test the scale() method of the Vector class.
     */
    @Test
    void scale() {

        // ============ Equivalence Partitions Tests ==============
        // TC01: test that two vector are scaled
        Vector v5 = v1.scale(2);
        assertEquals(new Vector(2, 4, 6), v5, "scale() wrong result");

        // =============== Boundary Values Tests ==================
        // TC11: test that vector is scaled to zero
        assertThrows(IllegalArgumentException.class, () -> v1.scale(0), "crossProduct() for parallel vectors does not throw an exception");
    }

    /**
     * Test the dotProduct() method of the Vector class.
     */
    @Test
    void dotProduct() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the dot product of a vector
        assertEquals(-28, v2.dotProduct(v1), "dotProduct() returned wrong result");

        // =============== Boundary Values Tests ==================
        // TC11: Test that the dot product of a vector with itself is calculated correctly
        assertEquals(0, v1.dotProduct(v3),"ERROR: dotProduct() for orthogonal vectors is not zero");
        // TC12: Test that the dot product of orthogonal vectors is zero
        assertEquals(14,v1.dotProduct(v1), "dotProduct() returned wrong result");
    }

    /**
     * Test method for
     * {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    void testCrossProduct() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the cross product of two vectors
        Vector vr = v1.crossProduct(v3);
        assertTrue(isZero(vr.length() - v1.length() * v3.length()), "crossProduct() returned wrong result");
        // TC02: Test that the cross product of two orthogonal vectors
        assertFalse(!isZero(vr.dotProduct(v1)) || !isZero(vr.dotProduct(v3)), "ERROR: crossProduct() result is not orthogonal to its operands");

        // =============== Boundary Values Tests ==================
        // TC11: Test that the cross product of two orthogonal vectors
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v2), "ERROR: crossProduct() for parallel vectors does not throw an exception");
    }

    /**
     * Test method for square length of the vector
     */
    @Test
    void lengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the square length of a vector
        assertEquals(0, v4.lengthSquared() - 9,"ERROR: lengthSquared() wrong value");
    }

    /**
     * Test method for length of the vector
     */
    @Test
    void length() {
        // =========== Equivalence Partitions Tests ==============
        // TC01: Test that the length of a vector
        assertEquals(0, (v4.length() - 3),"ERROR: length() wrong value");
    }

    /**
     * Test method for normalize
     */
    @Test
    void normalize() {
        Vector v = new Vector(1, 2, 3);
        Vector u = v.normalize();

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the normalize() method
        assertTrue(isZero(u.length() - 1), "ERROR: the normalized vector is not a unit vector");
        assertFalse(v.dotProduct(u) < 0,"ERROR: the normalized vector is opposite to the original one");

    }
}