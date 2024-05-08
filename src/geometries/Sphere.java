package geometries;

import primitives.Point;
import primitives.Vector;

// A class representing a Sphere geometry
public class Sphere implements Geometry {
     private Point center; // Changed _center to center for naming convention
     private double radius;

    // Constructor for the Sphere class
    public Sphere(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    @Override
    // Method returns null, revise documentation and method name
    public Vector getNormal(Point point) {
        return null;
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + center +
                ", radius=" + radius +
                '}';
    }
}
