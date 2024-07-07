package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * Class Sphere is the basic class representing a sphere in Euclidean geometry
 */
public class Sphere extends RadialGeometry{
    /**
     * The axis of the sphere
     */
    protected final Point center;

    /**
     * @param center axis of the sphere
     * @param radius radius of the sphere
     */
    public Sphere(Point center, double radius) {
        super(radius);
        this.center = center;
    }
    /**
     * Finds the intersections of a given ray with the geometry of the object.
     *
     * @param  ray  the ray to find intersections with
     * @return      a list of GeoPoint objects representing the intersections, or null if there are no intersections
     */
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance) {
        double d;
        double Tm=0;
        if (center.equals(ray.head)) {
            d = 0;
        }
        else {
            Vector u = center.subtract(ray.head);
            Tm = ray.direction.dotProduct(u);
            d = Math.sqrt(u.lengthSquared() - Tm * Tm);
            if (d >= radius) {
                return null;
            }
        }
        double Th = Math.sqrt(radius * radius - d * d);
        double t1 = Tm - Th;
        double t2 = Tm + Th;
        if (t1 > 0 && t2 > 0 && t1 != t2 && alignZero(t1 - maxDistance) <= 0 && alignZero(t2 - maxDistance) <= 0) {
            return List.of(new GeoPoint(this,ray.getPoint(t1)),new GeoPoint(this,ray.getPoint(t2)));
        }
        if (t1 > 0 && alignZero(t1 - maxDistance) <= 0) {
            return List.of(new GeoPoint(this,ray.getPoint(t1)));
        }
        if (t2 > 0 && alignZero(t2 - maxDistance) <= 0) {
            return List.of(new GeoPoint(this,ray.getPoint(t2)));
        }
        return null;
    }
    /**
     * @param p the point at which to calculate the normal vector
     * @return the normal vector of the sphere at the point
     */
    @Override
    public Vector getNormal(Point p) {
        Vector v = p.subtract(this.center);
        return v.normalize();
    }
}