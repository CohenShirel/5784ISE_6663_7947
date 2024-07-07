package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Class Triangle is the basic class representing a triangle in Euclidean geometry
 *
 */
public class Triangle extends Polygon{
    /**
     * @param a point in the triangle
     * @param b point in the triangle
     * @param c point in the triangle
     */
    public Triangle(Point a,Point b,Point c) {
        super(a,b,c);
    }

    /**
     * @param ray the ray to find intersections with Triangle
     * @return the list of intersections
     */
    //Barycentric Coordinates
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance) {
        List <GeoPoint> intersectionList = plane.findGeoIntersections(ray,maxDistance);
        if(intersectionList == null)
            return null;
        GeoPoint intersectionPoint = intersectionList.getFirst();
        Vector v0;
        Vector v1;
        Vector v2;
        try {
            v0 = vertices.get(1).subtract(vertices.get(0));
            v1 = vertices.get(2).subtract(vertices.get(0));
            v2 = intersectionPoint.point.subtract(vertices.get(0));
        }
        catch (IllegalArgumentException e) {
            return null;
        }

        double d00 = v0.dotProduct(v0);
        double d01 = v0.dotProduct(v1);
        double d11 = v1.dotProduct(v1);
        double d20 = v2.dotProduct(v0);
        double d21 = v2.dotProduct(v1);

        double denom = d00 * d11 - d01 * d01;

        double v = (d11 * d20 - d01 * d21) / denom;
        double w = (d00 * d21 - d01 * d20) / denom;
        double u = 1.0 - v - w;

        if (v > 0 && w > 0 && u > 0 && v < 1 && w < 1 && u < 1) {
            return List.of(new GeoPoint(this,intersectionPoint.point));
        } else {
            return null;            // Point is outside the triangle

        }
    }
}