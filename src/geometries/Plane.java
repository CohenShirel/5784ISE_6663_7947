package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * A class representing a plane geometry.
 */
public class Plane implements Geometry {

    final Point origin;
    final Vector normal;

    /**
     * Constructs a new Plane object from three points on the plane.
     * The normal vector is computed as the cross product of vectors formed by subtracting point1 from point2 and point3.
     *
     * @param point1 The first point on the plane.
     * @param point2 The second point on the plane.
     * @param point3 The third point on the plane.
     */
    public Plane(Point point1, Point point2, Point point3) {
        this.origin = point1;
        Vector U = point2.subtract(point1);
        Vector V = point3.subtract(point1);
        Vector W = U.crossProduct(V);
        normal = W.normalize();
    }

    /**
     * Constructs a new Plane object with a given origin point and normal vector.
     *
     * @param origin        The origin point of the plane.
     * @param normalVector  The normal vector of the plane.
     */
    public Plane(Point origin, Vector normalVector) {
        this.origin = origin;
        this.normal = normalVector.normalize();
    }
    @Override
    public String toString() {
        return "Plane{" +
                "origin=" + origin +
                ", normal=" + normal +
                '}';
    }
    @Override
    public Vector getNormal(Point point) {
        return null; // Currently not implemented.
    }
    
    /**
     * Returns the normal vector of the plane.
     *
     * @return The normal vector of the plane.
     */
    public Vector getNormal() {
        return normal;
    }
}
