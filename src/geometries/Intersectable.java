package geometries;


import primitives.Point;
import primitives.Ray;

import java.util.List;
import java.util.Objects;

/**
 * Intersectable interface for geometries that can be intersected.
 */
public abstract class Intersectable {


    /**
     * The geometry that was hit.
     */
    public static class GeoPoint {
        /**
         * The geometry that was hit.
         */
        public Geometry geometry;
        /**
         * The point where the geometry was hit.
         */
        public Point point;

        /**
         * @param geometry
         * @param point
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }


        /**
         * Compares this GeoPoint with the specified object for equality.
         *
         * @param obj the object to be compared for equality with this GeoPoint
         * @return true if the specified object is equal to this GeoPoint
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            GeoPoint geoPoint = (GeoPoint) obj;
            return Objects.equals(geometry.getClass().getSimpleName(), geoPoint.geometry.getClass().getSimpleName())
                    && Objects.equals(point, geoPoint.point);
        }

        /**
         * Compute a hash code for the object based on its geometry and point.
         *
         * @return the computed hash code
         */
        @Override
        public int hashCode() {
            return Objects.hash(geometry, point);
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }
    public final List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray, Double.POSITIVE_INFINITY);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }
    public final List<Point> findIntersections(Ray ray, double maxDistance) {
        var geoList = findGeoIntersections(ray, maxDistance);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }
    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }
    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return findGeoIntersectionsHelper(ray, maxDistance);
    }
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);
}