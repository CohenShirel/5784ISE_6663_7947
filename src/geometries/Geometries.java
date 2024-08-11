package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Composite class for all geometries object implementing {@link Intersectable}
 */
public class Geometries extends Intersectable {
    List<Intersectable> _intersectables;
    private final List<Intersectable> geometries = new LinkedList<>();


    public Geometries() {
    	 //if bvh improvement is used
        if (BVH){
            //create bounding box around geometries
            createBoundingBox();
        }
        _intersectables = new LinkedList<Intersectable>();
    }

    public Geometries(Intersectable...intersectables) {
    	 //if bvh improvement is used
        if (BVH){
            //create bounding box around geometries
            createBoundingBox();
        }
        _intersectables = new LinkedList<Intersectable>();
        Collections.addAll(_intersectables,intersectables);
    }

    @Override
    public void createBoundingBox() {
        if (_intersectables == null)
            return;

        // Initialize minimum and maximum coordinates to infinity and negative infinity respectively
        double minX = Double.POSITIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double minZ = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;
        double maxZ = Double.NEGATIVE_INFINITY;

        // Iterate over the geometries in the list
        for (Intersectable geo : _intersectables) {
            if (geo.box != null) {
                // Update minimum and maximum coordinates based on the bounding box of each geometry
                minX = Math.min(minX, geo.box._minimums.getX());
                minY = Math.min(minY, geo.box._minimums.getY());
                minZ = Math.min(minZ, geo.box._minimums.getZ());
                maxX = Math.max(maxX, geo.box._maximums.getX());
                maxY = Math.max(maxY, geo.box._maximums.getY());
                maxZ = Math.max(maxZ, geo.box._maximums.getZ());
            }
        }

        // Create a new bounding box using the minimum and maximum coordinates
        box = new BoundingBox(new Point(minX, minY, minZ), new Point(maxX, maxY, maxZ));
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public void add(Intersectable...intersectables){
        Collections.addAll(_intersectables,intersectables);
    }

    @Override
    //=== find intersection point between a geometry (we know now) and the ray ===//
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {

        List<GeoPoint> intersection = null;

        for (Intersectable geometry : this._intersectables) { // loop on all the geometry that implement "the Intersectables"
            // list of crossing point between the ray ana the geometry//
            var geoIntersections = geometry.findGeoIntersections(ray);
            if (geoIntersections != null) { // if there is a crossing
                if (intersection == null) {
                    intersection = new LinkedList<>();
                }
                intersection.addAll(geoIntersections);
            }
        }

        return intersection;
    }

	@Override
	protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance) {
        List<GeoPoint> result = null;
        for (Intersectable geometry : geometries) {
            List<GeoPoint> intersections = geometry.findGeoIntersectionsHelper(ray,maxDistance);
            if (intersections != null) {
                if (result == null)
                    result = new LinkedList<>();
                result.addAll(intersections);
            }
        }
        return result;
    }
	    
}

