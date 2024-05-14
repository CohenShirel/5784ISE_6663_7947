package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * A class representing a tube geometry.
 */
public class Tube implements Geometry {

    protected Ray _axisRay;
    protected double _radius;

    /**
     * Constructs a new Tube object with a given axis ray and radius.
     *
     * @param axisRay The axis ray of the tube.
     * @param radius  The radius of the tube.
     */
    public Tube(Ray axisRay, double radius) {
        this._axisRay = axisRay;
        this._radius = radius;
    }
    @Override
    public Vector getNormal(Point point) {
        return null; // Currently not implemented.
    }
    @Override
    public String toString() {
        return "Tube{" +
                "axisRay=" + _axisRay +
                ", radius=" + _radius +
                '}';
    }
}
