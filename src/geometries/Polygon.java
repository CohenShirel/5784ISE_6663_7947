package geometries;

import primitives.*;
import java.util.ArrayList;

import static primitives.Util.*;
import java.util.List;

public class Polygon extends Geometry {

    protected List<Point> vertices;
    protected Plane _plane;
    private Material _material = new Material();

    public Polygon(Point... vertices) {
        if (vertices.length < 3)
            throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
        this.vertices = List.of(vertices);
        _plane = new Plane(vertices[0], vertices[1], vertices[2]);
        if (vertices.length == 3)
            return;

        Vector n = _plane.getNormal();

        Vector edge1 = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
        Vector edge2 = vertices[0].subtract(vertices[vertices.length - 1]);

        boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
        for (int i = 1; i < vertices.length; ++i) {
            if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
                throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");

            edge1 = edge2;
            edge2 = vertices[i].subtract(vertices[i - 1]);
            if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
                throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
        }
    }

    @Override
    public Vector getNormal(Point point) {
        return _plane.getNormal(point);
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<GeoPoint> intersections = _plane.findGeoIntersections(ray);

        if (intersections == null) {
            return null;
        }

        Point p0 = ray.getP0();
        Vector v = ray.getDir();

        Vector v1 = vertices.get(1).subtract(p0);
        Vector v2 = vertices.get(0).subtract(p0);

        double sign = v.dotProduct(v1.crossProduct(v2));

        if (isZero(sign)) {
            return null;
        }

        boolean positive = sign > 0;

        for (int i = vertices.size() - 1; i > 0; --i) {
            v1 = v2;
            v2 = vertices.get(i).subtract(p0);
            sign = alignZero(v.dotProduct(v1.crossProduct(v2)));

            if (isZero(sign)) {
                return null;
            }

            if (positive != (sign > 0)) {
                return null;
            }
        }

        return List.of(new GeoPoint(this, intersections.get(0).point));
    }

    public Polygon setMaterial(Material material) {
        this._material = material;
        return this;
    }

    @Override
    public Material getMaterial() {
        return _material;
    }
    
    /**
     * Finds intersections between the given ray and the list of points.
     * @param ray The ray to find intersections with.
     * @return A list of points representing the intersections with the ray.
     */
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance) {
         List <Point> intersectionList = _plane.findIntersections(ray);
            if(intersectionList == null)
                return null;
            Point intersectionPoint = intersectionList.getFirst();
            List <Double> listNum = new ArrayList<>();
            // Check if intersection point is inside the polygon
            for (int i = 0; i < vertices.size(); i++) {
                Vector v1 =  vertices.get(i).subtract(ray.getP0());
                Vector v2 =  vertices.get((i+1)%vertices.size()).subtract(ray.getP0());
                Vector n = v1.crossProduct(v2);
                double num = n.dotProduct(ray.getDir());
                if (isZero(num)) {
                    return null;
                }
                listNum.add(num);
            }
            if (listNum.stream().allMatch(value -> value > 0) || listNum.stream().allMatch(value -> value < 0)) {
                return List.of(new GeoPoint(this,intersectionPoint));
            }
            return null;
    }
}