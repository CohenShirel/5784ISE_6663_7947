package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * An interface representing a geometric shape in three-dimensional space.
 */
public interface Geometry {

    /**
     * Computes and returns the normal vector to the surface of the geometry at a given point.
     *
     * @param point The point on the surface of the geometry.
     * @return A vector perpendicular to the surface of the geometry at the given point.
     */
    Vector getNormal(Point point);
}
