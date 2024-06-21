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

    public Geometries() {
        _intersectables = new LinkedList<Intersectable>();
    }

    public Geometries(Intersectable...intersectables) {
        _intersectables = new LinkedList<Intersectable>();
        Collections.addAll(_intersectables,intersectables);
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
}

