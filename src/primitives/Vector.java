package primitives;

// This class represents a vector in 3D space.
public class Vector extends Point {

    // Constructor for creating a Vector object from a Double3 object.
    public Vector(Double3 xyz) {
        super(xyz);

        // Ensure the vector is not a zero vector.
        if (xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("ZERO vector is not allowed");
        }
    }

    // Constructor for creating a Vector object from individual coordinates.
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("ZERO vector is not allowed");
        }
    }

    /**
     * Adds another vector to this vector and returns the result as a new Vector object.
     *
     * @param vector The vector to add to this vector.
     * @return A new Vector object representing the sum of this vector and the input vector.
     */
    public Vector add(Vector vector) {
        return new Vector(xyz.add(vector.xyz));
    }

    /**
     * Scales this vector by a scalar value and returns the result as a new Vector object.
     *
     * @param scalar The scalar value to multiply this vector by.
     * @return A new Vector object representing the scaled vector.
     */
    public Vector scale(double scalar) {
        return new Vector(xyz.scale(scalar));
    }

    /**
     * Computes the dot product of this vector with another vector.
     *
     * @param vector The vector to compute the dot product with.
     * @return The dot product of this vector and the input vector.
     */
    public double dotProduct(Vector vector) {
        double u1 = xyz.d1;
        double u2 = xyz.d2;
        double u3 = xyz.d3;

        double v1 = vector.xyz.d1;
        double v2 = vector.xyz.d2;
        double v3 = vector.xyz.d3;

        return (u1 * v1 + u2 * v2 + u3 * v3);
    }

    /**
     * Computes the cross product of this vector with another vector.
     *
     * @param vector The vector to compute the cross product with.
     * @return A new Vector object representing the cross product of this vector and the input vector.
     */
    public Vector crossProduct(Vector vector) {
        double u1 = xyz.d1;
        double u2 = xyz.d2;
        double u3 = xyz.d3;

        double v1 = vector.xyz.d1;
        double v2 = vector.xyz.d2;
        double v3 = vector.xyz.d3;

        double newX = u2 * v3 - v2 * u3;
        double newY = u3 * v1 - v3 * u1;
        double newZ = u1 * v2 - v1 * u2;

        return new Vector(new Double3(newX, newY, newZ));
    }

    /**
     * Computes the square of the length (magnitude) of this vector.
     *
     * @return The squared length of this vector.
     */
    public double lengthSquared() {
        double u1 = xyz.d1;
        double u2 = xyz.d2;
        double u3 = xyz.d3;

        return u1 * u1 + u2 * u2 + u3 * u3;
    }

    /**
     * Computes the length (magnitude) of this vector.
     *
     * @return The length of this vector.
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Computes the unit vector (normalized vector) of this vector.
     *
     * @return A new Vector object representing the unit vector of this vector.
     */
    public Vector normalize() {
        double len = length();
        return new Vector(xyz.reduce(len));
    }

    // Override equals method to compare Vector objects.
    @Override
    public boolean equals(Object o) {
        return super.equals(o); // Delegate to superclass for equality check
    }

    // Override hashCode method for consistent hashing.
    @Override
    public int hashCode() {
        return super.hashCode(); // Delegate to superclass for hash code generation
    }

    // Override toString method to provide a string representation of the Vector object.
    @Override
    public String toString() {
        return "Vector" + xyz; // Concatenate "Vector" with the coordinates
    }
}