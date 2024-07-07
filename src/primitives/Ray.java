package primitives;

import static geometries.Intersectable.GeoPoint;
import static primitives.Util.alignZero;

import java.util.List;
import java.util.Objects;

/**
 * Class Ray is the basic class representing a ray in 3D space.
 */
public class Ray {
    /**
     * The delta constant
     */
    private static final double DELTA = 0.1;
    /**
     * head point of the ray
     */
    public final Point head;
    /**
     * direction of the ray
     */
    public final Vector direction;

    /**
     * @param head head point of the ray
     * @param direction direction of the ray
     */
    public Ray(Point head,Vector direction) {
        this.head = head;
        this.direction = direction.normalize();
    }

    /**
     * Constructor for ray deflected by DELTA
     *
     * @param p origin
     * @param n   normal vector
     * @param dir direction
     */
    public Ray(Point p, Vector n, Vector dir) {
        this.direction = dir.normalize();
        double nv = n.dotProduct(this.direction);
        Vector delta  =n.scale(DELTA);
        if (nv < 0)
            delta = delta.scale(-1);
        this.head = p.add(delta);
    }

    /**
     * @param t
     * @return the point "o" of the ray
     */
    public Point getPoint(double t) {
        return head.add(direction.scale(t));
    }

    /**
     * Calculates the value of T for a given point.
     *
     * @param  p    the point for which to calculate T
     * @return      the value of T for the given point
     */
    public double getT(Point p) {
        return (p.subtract(head)).dotProduct(direction);
    }

    /**
     * Finds the closest Point in the given list.
     *
     * @param  list  the list of Points
     * @return       the closest Point in the list
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> list){
        if (list == null) {
            return null;
        }
        GeoPoint closestPoint = list.getFirst();
        double minDistance = Double.MAX_VALUE;
        for (GeoPoint p : list) {
            double distance = head.distance(p.point);
            if (distance < minDistance) {
                minDistance = distance;
                closestPoint = p;
            }
        }
        return closestPoint;
    }
    public Point findClosestPoint(List<Point> intersections) {
        return intersections == null ? null : findClosestGeoPoint(intersections.stream()
                .map(p -> new GeoPoint(null, p)).toList()).point;
    }
    /**
     * Overrides the equals() method to compare if two Ray objects are equal.
     *
     * @param  obj the object to compare with
     * @return     true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Ray other)
                && this.head.equals(other.head)
                && this.direction.equals(other.direction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(head, direction);
    }

    /**
     * Returns a string representation of this object.
     *
     * @return          a string representation of this object
     */
    @Override
    public String toString() {
        return "head=" + head.toString() +
                ", direction=" + direction.toString();

    }



}