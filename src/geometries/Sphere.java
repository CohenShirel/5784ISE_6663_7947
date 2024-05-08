package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * A class representing a Sphere geometry.
 */
public class Sphere implements Geometry {

    private Point center;
    private double radius;

    /**
     * Constructs a new Sphere object with a given center point and radius.
     *
     * @param center The center point of the sphere.
     * @param radius The radius of the sphere.
     */
    public Sphere(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }
    @Override
    public Vector getNormal(Point point) {
        return null; // Currently not implemented.
    }
    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + center +
                ", radius=" + radius +
                '}';
    }
}
