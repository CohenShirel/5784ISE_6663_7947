package geometries;

import primitives.Point;
import primitives.Material;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;
import java.util.List;

public class Triangle extends Polygon {

    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }

    @Override
    public Triangle setMaterial(Material material) {
        super.setMaterial(material);
        return this;
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "vertices=" + vertices.get(0) + "," + vertices.get(1) + "," + vertices.get(2) +
                '}';
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        // Check if the ray intersects the plane.
        List<GeoPoint> intersections = _plane.findGeoIntersections(ray);
        if (intersections == null) return null;

        Point p0 = ray.getP0();
        Vector v = ray.getDir();

        Vector v1 = vertices.get(0).subtract(p0);
        Vector v2 = vertices.get(1).subtract(p0);
        Vector v3 = vertices.get(2).subtract(p0);

        // Check every side of the triangle
        double s1 = v.dotProduct(v1.crossProduct(v2));
        if (isZero(s1)) return null;

        double s2 = v.dotProduct(v2.crossProduct(v3));
        if (isZero(s2)) return null;

        double s3 = v.dotProduct(v3.crossProduct(v1));
        if (isZero(s3)) return null;

        if (!((s1 > 0 && s2 > 0 && s3 > 0) || (s1 < 0 && s2 < 0 && s3 < 0))) return null;

        return List.of(new GeoPoint(this, intersections.get(0).point));
    }
}
