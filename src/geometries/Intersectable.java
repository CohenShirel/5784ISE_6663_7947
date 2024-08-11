package geometries;

import primitives.*;

import java.util.List;
import java.util.Objects;

/**
 * an abstract class for finding the intersection point between the ray and the complex object
 *
 * As we said - because there is no necessary to implement the "getNormal" function in a complex object,
 * we separated the Geometry interface (Targil 5) into two interfaces (ISP principle), and this is one of them.
 * (from Targil 6 on, it's becomes to be an abstract class)
 *
 * We define here only the "findIntersection" function to find intersection points between the ray
 * and the complex object
 */
public abstract class Intersectable {
	// for bvh use
    public static boolean BVH=true;
    /**
     * class representing boundary box
     */
    public class BoundingBox {
        public Point _minimums;
        public Point _maximums;

        /**
         * Constructs a bounding box with the specified minimum and maximum points.
         *
         * @param minimums The minimum point of the bounding box.
         * @param maximums The maximum point of the bounding box.
         */
        public BoundingBox(Point minimums, Point maximums) {
            _minimums = minimums;
            _maximums = maximums;
        }
    }

    //bounding box for intersectable
    public BoundingBox box;
    /**
     * return true if ray intersects object
     *
     * @param ray ray to check
     * @return whether ray intersects box
     * code taken from scratchapixel.com
     * https://www.scratchapixel.com/lessons/3d-basic-rendering
     * /introductionacceleration-structure/bounding-volume-hierarchy-BVH-part1
     */
    public boolean intersectingBoundingBox(Ray ray) {
        if (!BVH || box == null)
            return true;
        Vector dir = ray.getDir();
        Point p0 = ray.getP0();

        // Calculate the intersection intervals on the x-axis
        double xMin = (box._minimums.getX() - p0.getX()) / dir.getX();
        double xMax = (box._maximums.getX() - p0.getX()) / dir.getX();

        // Ensure xMin is smaller than xMax
        if (xMin > xMax) {
            double temp = xMin;
            xMin = xMax;
            xMax = temp;
        }

        // Calculate the intersection intervals on the y-axis
        double yMin = (box._minimums.getY() - p0.getY()) / dir.getY();
        double yMax = (box._maximums.getY() - p0.getY()) / dir.getY();

        // Ensure yMin is smaller than yMax
        if (yMin > yMax) {
            double temp = yMin;
            yMin = yMax;
            yMax = temp;
        }

        // Check for non-overlapping intervals on the x-axis and y-axis
        if ((xMin > yMax) || (yMin > xMax))
            return false;

        // Update xMin to the maximum of yMin and xMin
        if (yMin > xMin)
            xMin = yMin;

        // Update xMax to the minimum of yMax and xMax
        if (yMax < xMax)
            xMax = yMax;

        // Calculate the intersection intervals on the z-axis
        double zMin = (box._minimums.getZ() - p0.getZ()) / dir.getZ();
        double zMax = (box._maximums.getZ() - p0.getZ()) / dir.getZ();

        // Ensure zMin is smaller than zMax
        if (zMin > zMax) {
            double temp = zMin;
            zMin = zMax;
            zMax = temp;
        }

        // Check for non-overlapping intervals on the x-axis and z-axis
        if ((xMin > zMax) || (zMin > xMax))
            return false;

        // Update xMin to the maximum of zMin and xMin
        if (zMin > xMin)
            xMin = zMin;

        // Update xMax to the minimum of zMax and xMax
        if (zMax < xMax)
            xMax = zMax;

        return true;
    }

    /**
     * create the boundary box for the objects
     */
    public abstract void createBoundingBox();
//תחתית העמוד
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
	/*
     * @param ray {@link Ray} pointing toward the object
     * @return list of intersection Point between the ray and the object
     */
    public final List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream().map(gp -> gp.point).toList();
    }


    //======== the NVI design pattern =======//
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);

    public List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersectionsHelper(ray);
    }

    /**
     * this class has been written because we want to know the specific geometry the ray cross it over
     * because we added the emission light for each geometry and if we want to calculate the color at the point
     * we have to mind the geometry's color (this class is PDS)
     */
    
    public static class GeoPoint {
        public final Geometry geometry;
        public final Point point;

        //--- constructor ---//
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }
        
        public Geometry setBVH(boolean bvh) {
            BVH = bvh;
            return this.geometry;
        }
      
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return Objects.equals(geometry, geoPoint.geometry) && point.equals(geoPoint.point);
        }

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
    
    public final List<Point> findIntersections(Ray ray, double maxDistance) {
        var geoList = findGeoIntersections(ray, maxDistance);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }
    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
    	  if (BVH && !intersectingBoundingBox(ray))
          {
              return null;
          }
        return findGeoIntersectionsHelper(ray, maxDistance);
    }
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);

}
