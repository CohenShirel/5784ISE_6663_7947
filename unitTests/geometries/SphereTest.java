package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {
    /* Unit tests for geometries.Sphere class */
    private final Point p100 = new Point(1, 0, 0);
    Sphere normalSphere = new Sphere(new Point(0,0,0), 1);
    Sphere sphere = new Sphere(p100, 1d);
    private final Point p001 = new Point(0, 0, 1);
    private final Vector v001 = new Vector(0, 0, 1);


    @Test
    void getNormal() {
        Vector normal = normalSphere.getNormal(new Point(1,0,0));
        // ================ Equivalence Partitions Tests ==============
        //TC01: test that normal is correct
        assertTrue(normal.equals(new Vector(1,0,0).normalize()) || normal.equals(new Vector(1,0,0).normalize().scale(-1)),"ERROR: sphere normal is not correct");
        //TC02: test that normal is in the right length
        assertEquals(1, normal.length(), "ERROR: plane normal is not in the right length");

    }

    /* Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.*/
    @Test
    void testFindIntersections() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(4,-1,0),new Vector(-2,-1,0))), "Ray starts after the sphere");
        // TC02: Ray starts before and crosses the sphere (2 points)======================
        List<Point> result1 = sphere.findIntersections(new Ray(new Point(2.5,-0.5,0), new Vector(-1,1,0)));
        assertEquals(2, result1.size(), "Wrong number of points");
        assertEquals(List.of(new Point(2,0,0), new Point(1,1,0)), result1, "Ray crosses sphere");
        // TC03: Ray starts inside the sphere (1 point)
        final var result2 = sphere.findIntersections(new Ray(new Point(1.5,0,0),new Vector(-0.5,1,0)));
        assertEquals(1, result2.size(), "Wrong number of points");
        assertEquals(List.of(new Point(1,1,0)), result2, "Ray starts inside the sphere");
        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(-2,-1,0))), "Ray's line out of sphere");


        // =============== Boundary Values Tests ==================
        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        final var result3 = sphere.findIntersections(new Ray(new Point(2,0,0),new Vector(-1,1,0)));
        assertEquals(1, result3.size(), "Wrong number of points");
        assertEquals(List.of(new Point(1,1,0)), result2, "Ray starts at sphere and goes inside");
        // TC12: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(1,1,0),new Vector(-1,1,0))), "Ray starts at sphere and goes outside");

        // **** Group: Ray's line goes through the center ****
        // TC13: Ray starts before the sphere (2 points)
        final var result4 = sphere.findIntersections(new Ray(new Point(3,0,0),new Vector(-1,0,0)));
        assertEquals(2, result4.size(), "Wrong number of points");
        assertEquals(List.of(new Point(2,0,0), new Point(0,0,0)), result4, "Ray starts before the sphere");
        // TC14: Ray starts at sphere and goes inside (1 points)
        final var result5 = sphere.findIntersections(new Ray(new Point(2,0,0),new Vector(-1,0,0)));
        assertEquals(1, result5.size(), "Wrong number of points");
        assertEquals(List.of(new Point(0,0,0)), result5, "Ray starts at sphere and goes inside");
        // TC15: Ray starts inside (1 points)
        final var result6 = sphere.findIntersections(new Ray(new Point(1.5,0,0),new Vector(-1,0,0)));;
        assertEquals(1, result6.size(), "Wrong number of points");
        assertEquals(List.of(new Point(0,0,0)), result6, "Ray starts inside");
        // TC16: Ray starts at the center (1 points)
        final var result7 = sphere.findIntersections(new Ray(new Point(1,0,0),new Vector(-1,0,0)));
        assertEquals(1, result2.size(), "Wrong number of points");
        assertEquals(List.of(new Point(0,0,0)), result7, "Ray starts at the center");
        // TC17: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(0,0,0),new Vector(-1,0,0))), "Ray starts at sphere and goes outside");
        // TC18: Ray starts after sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(-1,0,0),new Vector(-1,0,0))), "Ray starts at sphere and goes outside");

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(0,-1,0),new Vector(0,1,0))), "Ray starts before the tangent point");
        // TC20: Ray starts at the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(0,0,0),new Vector(0,1,0))), "Ray starts at the tangent point");
        // TC21: Ray starts after the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(0,1,0),new Vector(0,1,0))), "Ray starts after the tangent point");


        // **** Group: Special cases
        // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertNull(sphere.findIntersections(new Ray(new Point(1,-2,0),new Vector(0,0,-2))), "Ray's line is outside, ray is orthogonal to ray start to sphere's center line");

        //Test with distance

        List<Point> result02 = sphere.findIntersections(new Ray(new Point(3,0,0),new Vector(-1,0,0)),1000);
        //TC21: Test all the geometries are intersecting with max distance
        assertEquals(2,result02.size(),"ERROR: some of the geometries are not intersecting");

        List<Point> result04 = sphere.findIntersections(new Ray(new Point(30,0,0),new Vector(-1,0,0)),1);
        //TC21: Test none of the geometries are intersecting because of the max distance
        assertNull(result04,"ERROR: the geometries aren't intersecting because of the max distance");
    }
}