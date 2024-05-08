package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

// A class representing a tube geometry
public class Tube implements Geometry {

    protected Ray axisRay; // Changed _axisRay to axisRay for naming convention
    protected double radius;

    // Constructor for the Tube class
    public Tube(Ray axisRay, double radius) {
        this.axisRay = axisRay;
        this.radius = radius;
    }

    @Override
    // Returning a vector that is perpendicular to the surface of the tube.
    public Vector getNormal(Point point) {
        return null;
    }
    @Override
    public String toString() {
        return "Tube{" +
                "axisRay=" + axisRay +
                ", radius=" + radius +
                '}';
    }
}
