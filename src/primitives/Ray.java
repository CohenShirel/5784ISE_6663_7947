package primitives;


import geometries.Intersectable.GeoPoint;

import java.util.List;
import java.util.Objects;
import static primitives.Util.isZero;

//Opening a Class for representation Ray in the space (3D)//
public class Ray {
    private static final double DELTA = 0.1;
    private final Point p0;
    private final Vector dir;

    // Creating a constructor for the class Ray.
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
    }

    //Getters
    public Point getP0() {
        return this.p0;
    }

    public Vector getDir() {
        return this.dir;
    }
    
    /**
     * Constructor for ray deflected by DELTA
     *
     * @param p origin
     * @param n   normal vector
     * @param dir direction
     */
    public Ray(Point p, Vector n, Vector dir) {
        this.dir = dir.normalize();
        double nv = n.dotProduct(this.dir);
        Vector delta  =n.scale(DELTA);
        if (nv < 0)
            delta = delta.scale(-1);
        this.p0 = p.add(delta);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return this.p0.equals(ray.p0) && this.dir.equals(ray.dir);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.p0, this.dir);
    }

    @Override
    public String toString() {
        return "primitives.Ray{" +
                "p0=" + this.p0 +
                ", dir=" + this.dir +
                '}';
    }

    /**
     * get Point at specific distance in the ray's direction
     *
     * @param t is a distance for reaching new Point
     * @return new {@link Point}
     */
    public Point getPoint(double t) {
        if (isZero(t)) {
            throw new IllegalArgumentException("t is equal to 0 produce an illegal ZERO vector");
        }
        return this.p0.add(this.dir.scale(t));
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
            double distance = p0.distance(p.point);
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
}