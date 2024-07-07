package primitives;


import java.util.Objects;

/**
 * A point in 3D space.
 */
public class Point {
    /**
     * The coordinates of the point.
     */
    protected final Double3 xyz;
    /**
     * A point at the origin (0,0,0).
     */
    public static final Point ZERO = new Point(Double3.ZERO);

    /**
     * @param x
     * @param y
     * @param z
     */
    public Point(double x,double y,double z) {
        this.xyz = new Double3(x,y,z);
    }

    /**
     * @param xyz
     */
    Point(Double3 xyz) {
        this.xyz = xyz;
    }

    /**
     * Subtracts the coordinates of the given point from the coordinates of this point,
     * and returns a new Vector representing the result.
     *
     * @param  p  the point to subtract
     * @return    a new Vector representing the result of the subtraction
     */
    public Vector subtract(Point p) {
        return new Vector(xyz.subtract(p.xyz));
    }

    /**
     * Adds a given Point object to this Point object and returns a new Point object.
     *
     * @param  p  the Point object to be added
     * @return    a new Point object that is the result of the addition
     */
    public Point add(Point p) {
        return new Point(xyz.add(p.xyz));
    }

    /**
     * Calculates the squared distance between this point and the given point.
     *
     * @param  p The point to calculate the distance to.
     * @return   The squared distance between this point and the given point.
     */
    public double distanceSquared(Point p) {
        return ((xyz.d1 - p.xyz.d1) * (xyz.d1 - p.xyz.d1)
                + (xyz.d2 - p.xyz.d2) * (xyz.d2 - p.xyz.d2)
                + (xyz.d3 - p.xyz.d3) * (xyz.d3 - p.xyz.d3));
    }

    /**
     * Calculates the distance between this point and the given point.
     *
     * @param  p  the point to calculate the distance to
     * @return    the distance between the two points
     */
    public double distance(Point p) {
        return Math.sqrt(this.distanceSquared(p));
    }



    /**
     * Checks if this object is equal to another object.
     * @param  obj	the object to compare to
     * @return     	true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Point other)
                && this.xyz.equals(other.xyz);
    }

    /**
     * Convert the object to a string representation.
     *
     * @return  a string representation of the object
     */
    @Override
    public String toString() {
        return xyz.toString();
    }
    @Override
    public int hashCode() {
        return Objects.hash(xyz);
    }
}