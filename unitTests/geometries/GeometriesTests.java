package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTest {
    Geometries geometries;

    @Test
    void findIntersections() {
        //============== Equivalence Partitions Tests ===============
        Geometries geometries01 = new Geometries(
                new Sphere(new Point(0,-2,0), 1),
                new Sphere(new Point(2,0,0), 1),
                new Triangle(new Point(0,0,0), new Point(0,1,0), new Point(-1,0,0)));
        List<Point> result01 = geometries01.findIntersections(new Ray(new Point(-1,-4,0),new Vector(2,2,0)));

        //TC01: Test some of the geometries are intersecting but other don't
        assertEquals(4,result01.size(),"ERROR: some of the geometries are not intersecting");
        //============= Boundary Values Tests ==================
        //TC11: Test empty geometries collection
        Geometries geometries11 = new Geometries();
        assertNull(geometries11.findIntersections(new Ray(new Point(-1,-4,0),new Vector(2,2,0))),"ERROR: some of the geometries are not intersecting");

        //TC12: Test none of the geometries are intersecting
        Geometries geometries12 = new Geometries(
                new Sphere(new Point(0,-2,0), 1),
                new Sphere(new Point(2,0,0), 1),
                new Triangle(new Point(0,0,0), new Point(0,1,0), new Point(-1,0,0)));
        assertNull(geometries12.findIntersections(new Ray(new Point(-1,-4,0),new Vector(-2,-2,0))),"ERROR: some of the geometries are not intersecting");

        //TC13: Test only 1 of the geometries are intersecting
        Geometries geometries13 = new Geometries(
                new Sphere(new Point(2,0,0), 1),
                new Triangle(new Point(0,0,0), new Point(0,1,0), new Point(-1,0,0)));
        List<Point> result13 = geometries13.findIntersections(new Ray(new Point(-1,-4,0),new Vector(2,2,0)));
        assertEquals(2,result13.size(),"ERROR: some of the geometries are not intersecting");
              //TC 14: Test all the geometries are intersecting
        Geometries geometries14 = new Geometries(
                new Sphere(new Point(0,-2,0), 1),
                new Sphere(new Point(2,0,0), 1),
                new Triangle(new Point(7,2.5,2), new Point(7,2.5,-2), new Point(4,4,0)));
        List<Point> result14 = geometries14.findIntersections(new Ray(new Point(-1,-4,0),new Vector(2,2,0)));
        assertEquals(5,result14.size(),"ERROR: some of the geometries are not intersecting");


        //Test with distance

        //TC21: Test all the geometries are intersecting with max distance
        List<Point> result02 = geometries01.findIntersections(new Ray(new Point(-1,-4,0),new Vector(2,2,0)),1000);
        assertEquals(4,result02.size(),"ERROR: some of the geometries are not intersecting");

        //TC21: Test some of the geometries are intersecting but other don't because of the max distance
        List<Point> result03 = geometries01.findIntersections(new Ray(new Point(-1,-4,0),new Vector(2,2,0)),2);
        assertEquals(1,result03.size(),"ERROR: some of the geometries are not intersecting");

        //TC21: Test none of the geometries are intersecting because of the max distance
        List<Point> result04 = geometries01.findIntersections(new Ray(new Point(-1,-4,0),new Vector(2,2,0)),1);
        assertNull(result04,"ERROR: the geometries aren't intersecting because of the max distance");
    }
}