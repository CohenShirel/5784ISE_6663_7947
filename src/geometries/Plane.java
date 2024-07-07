package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.*;

/**
 * Class Plane is the basic class representing a plane in Euclidean geometry
 */
public class Plane extends Geometry{
    /**
     * The point in the plane
     */
    private final Point q;
    /**
     * The normal vector of the plane
     */
    private final Vector normal;

    /**
     * @param a point in the plane
     * @param b point in the plane
     * @param c point in the plane
     */
    public Plane(Point a,Point b,Point c) {
        if (a.equals(b) || b.equals(c) ||c.equals(a))
            throw new IllegalArgumentException("ERROR : two or more point are collided and it's not allowed");
        if (isZero(a.subtract(b).crossProduct(c.subtract(b)).length()))
            throw new IllegalArgumentException("ERROR: Points in the same plane are not allowed");
        this.q = a;
        Vector v1 = b.subtract(a);
        Vector v2 = c.subtract(a);
        this.normal = v1.crossProduct(v2).normalize();
    }

    /**
     * @param q the point in the plane
     * @param normal the normal vector of the plane
     */
    public Plane(Point q, Vector normal) {
        this.q = q;
        this.normal = normal.normalize();
    }

    /**
     * @param p the point at which to calculate the normal vector
     * @return the normal vector of the plane at the point
     */
    @Override
    public Vector getNormal(Point p) {
        return this.normal;
    }

    /**
     * @return the normal vector of the plane
     */
    public Vector getNormal() {
        return this.normal;
    }

    /**
     * Finds the intersections of a given ray with the geometry represented by the class.
     *
     * @param  ray  the ray to find intersections with
     * @return      a list of GeoPoint objects representing the intersections, or null if no intersections found
     */
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance) {
        double numerator = normal.dotProduct(q.subtract(ray.head));
        double denominator = normal.dotProduct(ray.direction);
        if (isZero(denominator))
        {
            return null;
        }
        double t = alignZero(numerator / denominator);
        if (t > 0  && alignZero(t - maxDistance) <= 0)
        {
            return List.of(new GeoPoint(this,ray.getPoint(t)));
        }
        return null;
    }
}