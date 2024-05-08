package geometries;

import primitives.Point;

/**
 * A class representing a triangle geometry, which is a specific type of polygon.
 */
public class Triangle extends Polygon {

    /**
     * Constructs a new Triangle object with three given vertices.
     *
     * @param p1 The first vertex of the triangle.
     * @param p2 The second vertex of the triangle.
     * @param p3 The third vertex of the triangle.
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }
}
