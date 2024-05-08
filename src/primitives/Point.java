package primitives;

import java.util.Objects;

/**
 * Represents a point in 3D space, defined by its coordinates.
 */
public class Point {

    final Double3 xyz; 
    
    /**
     * The origin point (0, 0, 0).
     */
    public static final Point ZERO = new Point(0, 0, 0);

    /**
     * Constructs a new Point object with the specified coordinates.
     *
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     * @param z The z-coordinate of the point.
     */
    public Point(double x, double y, double z) {
        this.xyz = new Double3(x, y, z);
    }

    /**
     * Constructs a new Point object from another Double3 object.
     *
     * @param other The Double3 object representing the coordinates of the point.
     */
    public Point(Double3 other) {
        this.xyz = other;
    }

    /**
     * Computes the vector from this point to another point.
     *
     * @param other The other point.
     * @return The vector from this point to the other point.
     */
    public Vector subtract(Point other) {
        Double3 result = xyz.subtract(other.xyz);
        if (result.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("Resulting vector is a ZERO vector");
        }
        return new Vector(result);
    }

    /**
     * Adds the coordinates of another point to this point.
     *
     * @param other The other point.
     * @return A new Point object representing the sum of this point and the other point.
     */
    public Point add(Point other) {
        return new Point(xyz.add(other.xyz));
    }

    /**
     * Calculates the squared distance between this point and another point.
     *
     * @param other The other point.
     * @return The squared distance between this point and the other point.
     */
    public double distanceSquared(Point other) {
        double dx = xyz.d1 - other.xyz.d1;
        double dy = xyz.d2 - other.xyz.d2;
        double dz = xyz.d3 - other.xyz.d3;
        return dx * dx + dy * dy + dz * dz;
    }

    /**
     * Calculates the distance between this point and another point.
     *
     * @param other The other point.
     * @return The distance between this point and the other point.
     */
    public double distance(Point other) {
        return Math.sqrt(distanceSquared(other));
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return xyz.equals(point.xyz);
    }
    @Override
    public int hashCode() {
        return Objects.hash(xyz);
    }
    @Override
    public String toString() {
        return "Point{" +
                "xyz=" + xyz +
                '}';
    }
}
