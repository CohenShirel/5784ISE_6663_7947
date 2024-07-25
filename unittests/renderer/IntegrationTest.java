package renderer;

import geometries.Geometry;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class IntegrationTest {
    private final int vpHeight = 3;
    private final int vpWidth = 3;
    private final int nX = 3;
    private final int nY = 3;
    private static final Point ZERO_POINT = new Point(0, 0, 0);
    private Camera camera;

    /**
     * Counts the total amount of intersections between all the camera's ray and a given geometry
     *
     * @param geometry the geometry
     * @param nX1 Nx
     * @param nY1 Ny
     * @return total amount of intersections
     */
    private int countIntersections(Geometry geometry, int nX1, int nY1) {
        int counter = 0;
        List<Point> intersections;

        for (int i = 0; i < nX1; i++)
            for (int j = 0; j < nY1; j++)
            {
                List <Ray> rayList = camera.constructRay(this.nX, this.nY, j, i);

                for (Ray ray: rayList) {
                    intersections = geometry.findIntersections(ray);

                    if (intersections != null)
                        counter += intersections.size();
                }
            }

        return counter;
    }

    /**
     * Test method for {@link renderer.Camera} constructed {@link primitives.Ray} intersections with
     * {@link geometries.Sphere}
     */
    @Test
    void testSphereIntersections() {
        this.camera = new Camera(
                ZERO_POINT, new Vector(0, 0, -1),
                new Vector(0, 1, 0)
        ).setVPDistance(1).setVPSize(vpWidth, vpHeight);

        // TC01: Small Sphere in front of the View Plane
        Sphere sphere = new Sphere(new Point(0, 0, -3), 1);
        assertEquals(
                2,
                countIntersections(sphere, nX, nY),
                "TC01: Wrong amount of intersections"
        );

        // TC02: Big Sphere which almost completely includes the View Plane
        this.camera.moveCamera(new Point(0, 0, 0.5));
        sphere = new Sphere(new Point(0, 0, -2.5), 2.5);
        assertEquals(
                18,
                countIntersections(sphere, nX, nY),
                "TC02: Wrong amount of intersections"
        );

        // TC03: Small Sphere which almost completely includes the View Plane
        sphere = new Sphere(new Point(0, 0, -2), 2);
        assertEquals(
                10,
                countIntersections(sphere, nX, nY),
                "TC03: Wrong amount of intersections"
        );

        // TC04: Big Sphere which completely includes the View Plane and the camera
        sphere = new Sphere(ZERO_POINT, 4);
        assertEquals(
                9,
                countIntersections(sphere, nX, nY),
                "TC04: Wrong amount of intersections"
        );

        // TC05: Small sphere behind the View Plane and the camera
        this.camera.moveCamera(ZERO_POINT);
        sphere = new Sphere(new Point(0, 0, 1), 0.5);
        assertEquals(
                0,
                countIntersections(sphere, nX, nY),
                "TC05: Wrong amount of intersections"
        );
    }

    /**
     * Test method for {@link renderer.Camera} constructed {@link primitives.Ray} intersections with
     * {@link geometries.Plane}
     */
    @Test
    void testPlaneIntersections() {
        this.camera = new Camera(
                ZERO_POINT, new Vector(0, 0, -1),
                new Vector(0, 1, 0)
        ).setVPDistance(1).setVPSize(vpWidth, vpHeight);

        // TC01: Plane is parallel to the View Plane and in front of it
        Plane plane = new Plane(new Point(0, 0, -4), new Vector(0, 0, 1));
        assertEquals(
                9,
                countIntersections(plane, nX, nY),
                "TC01: Wrong amount of intersections"
        );

        // TC02: Plane is not parallel to the View Plane in front of it and has 9 intersection points
        plane = new Plane(new Point(0, 2, -1), new Vector(0, 0.5, -1));
        assertEquals(
                9,
                countIntersections(plane, nX, nY),
                "TC02: Wrong amount of intersections"
        );

        // TC03: Plane is not parallel to the View Plane in front of it and has 6 intersection points
        plane = new Plane(new Point(0, 2, -1), new Vector(0, 5, -1));
        assertEquals(
                6,
                countIntersections(plane, nX, nY),
                "TC03: Wrong amount of intersections"
        );
    }

    /**
     * Test method for {@link renderer.Camera} constructed {@link primitives.Ray} intersections with
     * {@link geometries.Triangle}
     */
    @Test
    void testTriangleIntersections() {
        this.camera = new Camera(
                ZERO_POINT, new Vector(0, 0, -1),
                new Vector(0, 1, 0)
        ).setVPDistance(1).setVPSize(vpWidth, vpHeight);

        // TC01: Small triangle in front of the View Plane and has 1 intersection point
        Triangle triangle = new Triangle(
                new Point(0, 1, -2),
                new Point(1, -1, -2),
                new Point(-1, -1, -2)
        );
        assertEquals(
                1,
                countIntersections(triangle, nX, nY),
                "TC01: Wrong amount of intersections"
        );

        // TC02: Triangle is tall and thin (lanky) in front of the View Plane and has 2 intersection points
        triangle = new Triangle(
                new Point(0, 20, -2),
                new Point(1, -1, -2),
                new Point(-1, -1, -2)
        );
        assertEquals(
                2,
                countIntersections(triangle, nX, nY),
                "TC02: Wrong amount of intersections"
        );
    }
}
