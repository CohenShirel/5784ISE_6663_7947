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
    public void createBoundingBox() {
        if (vertices == null)
            return;

        // Initialize the minimum and maximum coordinates with extreme values
        double minX = Double.POSITIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double minZ = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;
        double maxZ = Double.NEGATIVE_INFINITY;

        // Iterate over each vertex in the list of vertices
        for (Point vertex : vertices) {
            // Update the minimum and maximum coordinates based on the vertex's components
            minX = Math.min(minX, vertex.getX());
            minY = Math.min(minY, vertex.getY());
            minZ = Math.min(minZ, vertex.getZ());
            maxX = Math.max(maxX, vertex.getX());
            maxY = Math.max(maxY, vertex.getY());
            maxZ = Math.max(maxZ, vertex.getZ());
        }

        // Create a new BoundingBox object using the calculated minimum and maximum coordinates
        box = new BoundingBox(new Point(minX, minY, minZ), new Point(maxX, maxY, maxZ));
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

  /**  @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
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
    **/
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<Point> intersections = _plane.findIntersections(ray);
        // if there are no intersections with the plane, there are no intersections with the triangle
        if (intersections == null) {
            return null;
        }

        // if the ray intersects the plane at the triangle's plane
        Vector v1 = vertices.get(0).subtract(ray.getP0());
        Vector v2 = vertices.get(1).subtract(ray.getP0());
        Vector v3 = vertices.get(2).subtract(ray.getP0());

        Vector n1 = v1.crossProduct(v2).normalize();
        Vector n2 = v2.crossProduct(v3).normalize();
        Vector n3 = v3.crossProduct(v1).normalize();

        double s1 = ray.getDir().dotProduct(n1);
        double s2 = ray.getDir().dotProduct(n2);
        double s3 = ray.getDir().dotProduct(n3);

        // if the ray is parallel to the triangle's plane
        if (isZero(s1) || isZero(s2) || isZero(s3)) {
            return null;
        }

        if ((s1 > 0 && s2 > 0 && s3 > 0) || (s1 < 0 && s2 < 0 && s3 < 0)) {
            return List.of(new GeoPoint(this, intersections.get(0)));
        }
        // if the ray intersects the plane but not the triangle
        return null;
    }
}