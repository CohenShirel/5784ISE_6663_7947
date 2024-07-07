package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

class PlaneTest {
    /**
     * Unit tests for geometries.Plane class
     */

    Point p1 = new Point(0,0,1);
    Point p11_sameLine = new Point(0,0,2);
    Point p12_sameLine = new Point(0,0,3);
    Point p2 = new Point(1,0,0);
    Point p3 = new Point(0,1,0);

    @Test
    void  testConstructor() {
        //=============== Boundary Values Tests ==================
        //TC01: test two or more point are collided and it's not allowed
        assertThrows(IllegalArgumentException.class, () -> new Plane(p1,p1,p2),"ERROR: two or more point are collided and it's not allowed");
        //TC02: test 3 points are on the same line and it's not allowed
        assertThrows(IllegalArgumentException.class, () -> new Plane(p1,p11_sameLine,p12_sameLine),"ERROR: 3 Points on the same line and it's not allowed");
    }

    @Test
    void getNormal() {
        Plane plane = new Plane(p1,p2,p3);
        Vector normal = plane.getNormal();
        // ================ Equivalence Partitions Tests ==============
        //TC01: test that normal is correct
        assertTrue(normal.equals(new Vector(1,1,1).normalize()) || normal.equals(new Vector(1,1,1).normalize().scale(-1)), "ERROR: plane normal is not correct");
        //TC02: test that normal is in the right length
        assertEquals(1, normal.length(), "ERROR: plane normal is not in the right length");

    }

    Point basePoint1 = new Point(0,0,2);
    Point basePoint2 = new Point(0,2,0);
    Point basePoint3 = new Point(2,0,0);
    Plane plane = new Plane(basePoint3,basePoint2,basePoint1);

    @Test
    void findIntersections() {
        //=============== Equivalence Partitions Tests ==============
        //TC01: test of a usual ray intersects the plane
        Ray rayUsual = new Ray(new Point(0,0,5),new Vector(-1,1,-3));
        assertEquals(1,plane.findIntersections(rayUsual).size() , "ERROR: the ray intersect the plane more than 1 time");
        assertEquals(List.of(new Point(-1,1,2)),plane.findIntersections(rayUsual),"ERROR: the intersection point does not equal to the expected point");
        //TC02: test of a usual ray beyond the plane that intersects the plane
        Ray rayBeyond = new Ray(new Point(-3,0,0),new Vector(-3,0,-1));
        assertNull(plane.findIntersections(rayBeyond), "ERROR: parallel ray does not intersect the plane");

        //=============== Boundary Values Tests ==================
        //*** Test cases of parallel ***
        //TC11: test of parallel ray intersects the plane
        Ray rayParallel = new Ray(new Point(0,5,0),new Vector(-1,-1,2));
        assertNull(plane.findIntersections(rayParallel), "ERROR: parallel ray doesn't intersect the plane");
        //TC12: test a parallel ray that  intersects the plane
        Ray rayParallelIncluded = new Ray(new Point(-1,1,2),new Vector(0,1,-1));
        assertNull(plane.findIntersections(rayParallelIncluded), "ERROR: parallel and included ray does not intersect the plane");

        //*** Test cases of orthogonal ***
        //TC13: test of orthogonal before ray intersects the plane
        Ray rayOrthogonalBefore = new Ray(new Point(0,0,5),new Vector(-1,-1,-1));
        assertEquals(1,plane.findIntersections(rayOrthogonalBefore).size() , "ERROR: orthogonal before ray intersect the plane 1 time");
        assertEquals(List.of(new Point(-1,-1,4)),plane.findIntersections(rayOrthogonalBefore),"ERROR: the intersection point does not equal to the expected point");
        //TC14: test of orthogonal in ray intersects the plane
        Ray rayOrthogonalIn = new Ray(new Point(-2,1,3),new Vector(-1,-1,-1));
        assertNull(plane.findIntersections(rayOrthogonalIn) , "ERROR: orthogonal in ray intersect the plane 0 time");
        //TC14: test of orthogonal after ray intersects the plane
        Ray rayOrthogonalAfter = new Ray(new Point(-2,-2,3),new Vector(-1,-1,-1));
        assertNull(plane.findIntersections(rayOrthogonalAfter) , "ERROR: orthogonal after ray intersect the plane 0 time");
        //*** Test cases of beyond the plane ***
        //TC15: test of beyond ray intersects the plane starting from the plane
        Ray rayBeyondRay = new Ray(new Point(-1,1,2),new Vector(-1,1,-2));
        assertNull(plane.findIntersections(rayBeyondRay) , "ERROR: beyond ray intersect the plane 0 time");
        //TC16: test of beyond ray intersects the plane starting from the reference point in the plane
        Ray rayBeyondRay2 = new Ray(new Point(0,0,2),new Vector(-2,2,-2));
        assertNull(plane.findIntersections(rayBeyondRay2) , "ERROR: beyond ray intersect the plane 0 time");

        //Test with distance

        List<Point> result02 = plane.findIntersections(rayUsual,1000);
        //TC21: Test all the geometries are intersecting with max distance
        assertEquals(1,result02.size(),"ERROR: some of the geometries are not intersecting");

        List<Point> result04 = plane.findIntersections(rayUsual,1);
        //TC21: Test none of the geometries are intersecting because of the max distance
        assertNull(result04,"ERROR: the geometries aren't intersecting because of the max distance");
    }
}