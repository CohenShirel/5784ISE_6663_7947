package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {
    private final double DELTA = 0.000001;
    Point p1N = new Point(0,0,1);
    Point p2N = new Point(1,0,0);
    Point p3N = new Point(0,1,0);
    Triangle triangleNormal = new Triangle(p1N, p2N, p3N);

    Point p1I = new Point(0,0,2);
    Point p2I = new Point(2,0,0);
    Point p3I = new Point(0,-2,0);
    Triangle triangleIntersection = new Triangle(p1I, p2I, p3I);
    @Test
    void getNormal() {
        Vector normal = triangleNormal.getNormal(new Point(0.5, 0.5, 0));
        // ================ Equivalence Partitions Tests ==============
        //TC01: test that normal is correct
        assertTrue(normal.equals(new Vector(1,1,1).normalize()) || normal.equals(new Vector(1,1,1).normalize().scale(-1)), "ERROR: plane normal is not correct");
        //TC02: test that normal is in the right length
        assertEquals(1, normal.length(), "ERROR: plane normal is not in the right length");

        // TC03: test that normal is orthogonal to each one of the edges
        assertEquals(0d, normal.dotProduct(p1N.subtract(p2N)), DELTA,"Polygon's normal is not orthogonal to one of the edges");
        assertEquals(0d, normal.dotProduct(p2N.subtract(p3N)), DELTA,"Polygon's normal is not orthogonal to one of the edges");
        assertEquals(0d, normal.dotProduct(p1N.subtract(p3N)), DELTA,"Polygon's normal is not orthogonal to one of the edges");
    }

    @Test
    void testFindIntersections() {
        // =============== Equivalence Partitions Tests ==============
        //TC01: test of a usual ray intersects in the triangle
        Ray rayUsual = new Ray(new Point(2,-2,1),new Vector(-1.5,1.5,0));
        assertEquals(1, triangleIntersection.findIntersections(rayUsual).size() , "ERROR: the ray intersect the triangle more than 1 time");
        assertEquals(List.of(new Point(0.5,-0.5,1)),triangleIntersection.findIntersections(rayUsual),"ERROR: the intersection point does not equal to the expected point");
        //TC02: test of a usual ray outside the triangle
        Ray rayOutside = new Ray(new Point(2,-2,1),new Vector(-3,1,1));
        assertNull(triangleIntersection.findIntersections(rayOutside), "ERROR: outside ray does not intersect the triangle");
        //TC03: test of a usual ray outside but between the continues of the sides at the triangle
        Ray rayOutsideBetweenSides = new Ray(new Point(2,-2,1),new Vector(-3,3,3));
        assertNull(triangleIntersection.findIntersections(rayOutsideBetweenSides), "ERROR: outside ray does not intersect the triangle");

        // =============== Boundary Values Tests ==============
        //TC04: test of a ray on the side in the triangle
        Ray rayOnSide = new Ray(new Point(2,-2,1),new Vector(-1,2,0));
        assertNull(triangleIntersection.findIntersections(rayOnSide), "ERROR: on edge ray does not intersect with the triangle");
        //TC05: test of a ray on the vertex in the triangle
        Ray rayOnVertex = new Ray(new Point(2,-2,1),new Vector(-2,2,1));
        assertNull(triangleIntersection.findIntersections(rayOnVertex), "ERROR: on vertex ray does not intersect with the triangle");
        //TC06: test of a ray that on the continues of the side at the triangle
        Ray rayOnContinuesSides = new Ray(new Point(2,-2,1),new Vector(-3,2,2));
        assertNull(triangleIntersection.findIntersections(rayOnContinuesSides), "ERROR: on the continues of the side ray does not intersect with the triangle");

        //Test with distance

        List<Point> result02 = triangleIntersection.findIntersections(rayUsual,1000);
        //TC21: Test all the geometries are intersecting with max distance
        assertEquals(1,result02.size(),"ERROR: some of the geometries are not intersecting");

        List<Point> result04 = triangleIntersection.findIntersections(rayUsual,1);
        //TC21: Test none of the geometries are intersecting because of the max distance
        assertNull(result04,"ERROR: the geometries aren't intersecting because of the max distance");
    }
}