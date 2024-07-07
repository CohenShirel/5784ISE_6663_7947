package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

/**
 * Class Cylinder is the basic class representing a cylinder in Euclidean geometry
 */
public class Cylinder extends Tube {
    /**
     * The height of the Cylinder
     */
    private final double height;

    /**
     * @param radius the radius of the cylinder
     * @param axis   the center of the cylinder
     * @param height the height of the cylinder
     */
    public Cylinder(double radius, Ray axis, double height) {
        super(radius, axis);
        this.height = height;
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance) {
        return null;
    }

    /**
     * @param p the point at which to calculate the normal vector
     * @return the normal vector of the Cylinder at the point
     */
    @Override
    public Vector getNormal(Point p) {
        if (p.equals(axis.head))
            return axis.direction.normalize();
        double t = axis.getT(p);
        if (isZero(t) || isZero(t - height)) {
            // Point is on one of the bases
            return axis.direction.normalize();
        }
        // Point is on the side surface
        return super.getNormal(p);
    }
}