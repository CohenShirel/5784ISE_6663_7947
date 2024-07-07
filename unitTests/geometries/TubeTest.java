package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TubeTest {
    /**
     * Unit tests for geometries.Tube class
     */
    @Test
    void getNormal() {
        Tube tube = new Tube(1,new Ray(new Point(0,0,-1),new Vector(0,0,1)));

        Vector normal = tube.getNormal(new Point(1,0,0));
        // ================ Equivalence Partitions Tests ==============
        //TC01: test that normal is correct
        assertTrue(normal.equals(new Vector(1,0,0).normalize()) || normal.equals(new Vector(1,0,0).normalize().scale(-1)),"ERROR: tube normal is not correct");
        //TC02: test that normal is in the right length
        assertEquals(1, normal.length(), "ERROR: tube normal is not in the right length");

        // =============== Boundary Values Tests ==================
        normal = tube.getNormal(new Point(1,0,1));
        //TC11: test that normal is correct
        assertTrue(normal.equals(new Vector(1,0,0).normalize()) || normal.equals(new Vector(1,0,0).normalize().scale(-1)),"ERROR: tube normal is not correct");
        //TC12: test that normal is in the right length
        assertEquals(1, normal.length(), "ERROR: tube normal is not in the right length");
    }
}