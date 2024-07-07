package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class CylinderTest {
    /**
     * Unit tests for geometries.Cylinder class
     */
    Cylinder cylinder = new Cylinder(1,new Ray(new Point(0,0,1),new Vector(0,0,1)),3);
    @Test
    void getNormal() {
        Vector normal = cylinder.getNormal(new Point(1,0,2));
        Vector normal_bottom = cylinder.getNormal(new Point(0.5,0.5,1));
        Vector normal_top = cylinder.getNormal(new Point(-0.5,0.5,4));
        Vector normal_bottom_center = cylinder.getNormal(new Point(0,0,1));
        Vector normal_top_center = cylinder.getNormal(new Point(0,0,4));

        // ================ Equivalence Partitions Tests ==============
        //TC01: test that normal is correct
        Vector normalize = new Vector(1, 0, 0).normalize();
        assertTrue(normal.equals(normalize) || normal.equals(normalize.scale(-1)),"ERROR: cylinder normal is not correct");
        //TC02: test that normal is in the right length
        assertEquals(1, normal.length(), "ERROR: cylinder normal is not in the right length");

        //TC03: test that normal on the bottom is correct
        normalize = new Vector(0, 0, -1).normalize();
        assertTrue(normal_bottom.equals(normalize) || normal_bottom.equals(normalize.scale(-1)),"ERROR: cylinder normal is not correct");
        //TC04: test that normal on the bottom is in the right length
        assertEquals(1, normal_bottom.length(), "ERROR: cylinder normal is not in the right length");

        //TC05: test that normal on the top is correct
        normalize = new Vector(0, 0, 1).normalize();
        assertTrue(normal_top.equals(normalize) || normal_top.equals(normalize.scale(-1)),"ERROR: cylinder normal is not correct");
        //TC04: test that normal on the top is in the right length
        assertEquals(1, normal_top.length(), "ERROR: cylinder normal is not in the right length");

        // =============== Boundary Values Tests ==================
        //TC11: test that normal on the top center is correct
        normalize = new Vector(0, 0, 1).normalize();
        assertTrue(normal_bottom_center.equals(normalize) || normal_bottom_center.equals(normalize.scale(-1)),"ERROR: cylinder normal is not correct");
        //TC12: test that normal on the top center is in the right length
        assertEquals(1, normal_bottom_center.length(), "ERROR: cylinder normal is not in the right length");

        //TC13: test that normal on the bottom center is correct
        normalize = new Vector(0, 0, -1).normalize();
        assertTrue(normal_top_center.equals(normalize) || normal_top_center.equals(normalize.scale(-1)),"ERROR: cylinder normal is not correct");
        //TC14: test that normal on the bottom center is in the right length
        assertEquals(1, normal_top_center.length(), "ERROR: cylinder normal is not in the right length");
    }
}