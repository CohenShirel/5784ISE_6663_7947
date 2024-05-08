package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * A class representing a tube geometry.
 */
public class Tube implements Geometry {

    protected Ray axisRay;
    protected double radius;

    /**
     * Constructs a new Tube object with a given axis ray and radius.
     *
     * @param axisRay The axis ray of the tube.
     * @param radius  The radius of the tube.
     */
    public Tube(Ray axisRay, double radius) {
        this.axisRay = axisRay;
        this.radius = radius;
    }
    @Override
    public Vector getNormal(Point point) {
        return null; // Currently not implemented.
    }
    @Override
    public String toString() {
        return "Tube{" +
                "axisRay=" + axisRay +
                ", radius=" + radius +
                '}';
    }
}
