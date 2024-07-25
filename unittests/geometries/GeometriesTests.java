package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Geometries
 * @author 
 */
class GeometriesTest {
    /**
     * Test method for {@link geometries.Geometries#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersections() {
        Geometries geometries = new Geometries(
                new Plane(new Point(1, 1, 1), new Vector(1, 1, 1)),
                new Sphere(new Point(1, 1, 0), 2),
                new Triangle(new Point(2, 0, 0), new Point(2, 2, 0), new Point(0, 2, 0))
        );
        Ray ray = new Ray(new Point(1, 1.5, 4), new Vector(0.5, 0, -1));

        // ============ Equivalence Partitions Tests ==============
        // ray intersect with some geometries
        List<Point> result = geometries.findIntersections(ray);
        assertEquals(
                3,
                result.size(),
                "EP01: Wrong intersections"
        );

        // ============= Boundary Values Tests =================
        // BVA01: geometries is empty
        assertNull(new Geometries().findIntersections(ray), "BVA01: Empty geometries");

        // BVA02: ray intersect with no geometries
        ray = new Ray(new Point(1, 1.5, 4), new Vector(0, -1, 1));
        result = geometries.findIntersections(ray);
        assertNull(
                result,
                "BVA02: Wrong intersections"
        );

        // BVA03: ray intersect with one geometry only
        ray = new Ray(new Point(1, 1.5, 4), new Vector(0, -1, 0));
        result = geometries.findIntersections(ray);
        assertEquals(
                1,
                result.size(),
                "BVA03: find intersection not working");


        // BVA04: ray intersect with all geometries
        ray = new Ray(new Point(1, 1.5, 4), new Vector(0, 0, -1));

        result = geometries.findIntersections(ray);
        assertEquals(
                4,
                result.size(),
                "BVA04: Wrong intersections"
        );
    }
}