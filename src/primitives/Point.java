package primitives;

import java.util.Objects;

public class Point{

    final Double3 xyz;
    public static final Point ZERO = new Point(0, 0, 0);
    // Constructor to create a Point from x, y, z coordinates
    public Point(double x, double y, double z) {
        this.xyz = new Double3(x, y, z);
    }

    // Constructor to create a Point from another Point
    public Point(Double3 other) {
        this.xyz = other;
    }

    // Subtracting the coordinates of another Point from this Point
    public Vector subtract(Point other) {
        Double3 result = xyz.subtract(other.xyz);
        if (result.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("Resulting vector is a ZERO vector");
        }
        return new Vector(result);
    }

    // Adding the coordinates of another Point to this Point
    public Point add(Point other) {
        return new Point(xyz.add(other.xyz));
    }

    // Calculates the squared distance between this Point and another Point
    public double distanceSquared(Point other) {
        double dx = xyz.d1 - other.xyz.d1;
        double dy = xyz.d2 - other.xyz.d2;
        double dz = xyz.d3 - other.xyz.d3;
        return dx * dx + dy * dy + dz * dz;
    }

    // Calculates the distance between this Point and another Point
    public double distance(Point other) {
        return Math.sqrt(distanceSquared(other));
    }

    // Equals method to compare this Point with another object
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return xyz.equals(point.xyz);
    }

    // Generates a hash code for this Point
    @Override
    public int hashCode() {
        return Objects.hash(xyz);
    }

    // Returns a string representation of this Point
    @Override
    public String toString() {
        return "Point=" + xyz;
    }
}
