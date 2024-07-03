package geometries;

import primitives.Color;
import static primitives.Util.alignZero;

import primitives.Material;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;
import java.util.Objects;

public class Sphere extends Geometry {
    private final Point _center;
    private final double _radius;
    private Color _emission = Color.BLACK;
    private Material _material = new Material();

    public Sphere(Point center, double radius) {
        _center = center;
        _radius = radius;
    }

    public Point getCenter() {
        return _center;
    }

    public double getRadius() {
        return _radius;
    }

    public Sphere setEmission(Color emission) {
        _emission = emission;
        return this;
    }

    public Sphere setMaterial(Material material) {
        _material = material;
        return this;
    }

    @Override
    public Color getEmission() {
        return _emission;
    }

    @Override
    public Material getMaterial() {
        return _material;
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "_center=" + _center +
                ", _radius=" + _radius +
                ", _emission=" + _emission +
                ", _material=" + _material +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sphere sphere = (Sphere) o;
        return Double.compare(sphere._radius, _radius) == 0 && _center.equals(sphere._center);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_center, _radius);
    }

    @Override
    public Vector getNormal(Point point) {
        Vector v = point.subtract(_center);
        return v.normalize(); //Return normalize normal vector.
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        Point p0 = ray.getP0(); // ray's starting point
        Point O = this._center; //the sphere's center point
        Vector V = ray.getDir(); // "the v vector" from the presentation

        // if p0 on center, calculate with line parametric representation
        // the direction vector normalized.
        if (O.equals(p0)) {
            Point newPoint = p0.add(ray.getDir().scale(this._radius));
            return List.of(new GeoPoint(this, newPoint));
        }

        Vector U = O.subtract(p0);
        double tm = V.dotProduct(U);
        double d = Math.sqrt(U.lengthSquared() - tm * tm);
        if (d >= this._radius) {
            return null;
        }

        double th = Math.sqrt(this._radius * this._radius - d * d);
        double t1 = tm - th;
        double t2 = tm + th;

        if (t1 > 0 && t2 > 0) {
            Point p1 = ray.getPoint(t1);
            Point p2 = ray.getPoint(t2);
            return List.of(new GeoPoint(this, p1), new GeoPoint(this, p2));
        }

        if (t1 > 0) {
            Point p1 = ray.getPoint(t1);
            return List.of(new GeoPoint(this, p1));
        }

        if (t2 > 0) {
            Point p2 = ray.getPoint(t2);
            return List.of(new GeoPoint(this, p2));
        }
        return null;
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
        if (_center.equals(ray.getP0())) {
            d = 0;
        }
        else {
            Vector u = _center.subtract(ray.getP0());
            Tm = ray.getDir().dotProduct(u);
            d = Math.sqrt(u.lengthSquared() - Tm * Tm);
            if (d >= _radius) {
                return null;
            }
        }
        double Th = Math.sqrt(_radius * _radius - d * d);
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
}