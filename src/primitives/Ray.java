package primitives;

import java.util.Objects;

// Represents a ray in 3D space, defined by an origin point (_p0) and a direction vector (_dir).
public class Ray {

    final Point head; // The origin point of the ray.
    final Vector direction; // The normalized direction vector of the ray.

    // Constructs a new Ray object with a given origin point and direction vector.
    // The direction vector is automatically normalized to ensure it represents a unit vector.
    public Ray(Point p0, Vector dir) {
    	head = p0;
        direction = dir.normalize(); // Normalize the direction vector to ensure it's a unit vector.
    }

    /**
     * Checks whether this Ray object is equal to another object.
     *
     * @param o The object to compare with this Ray.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Check if the objects are identical (same memory reference).
        if (o == null || getClass() != o.getClass()) return false; // Check if the object is of the same class.
        Ray ray = (Ray) o; // Cast the object to Ray class for comparison.
        return head.equals(ray.head) && direction.equals(ray.direction); // Compare origin point and direction vector for equality.
    }

    /**
     * Generates a hash code value for this Ray object.
     *
     * @return The hash code value based on the origin point and direction vector.
     */
    @Override
    public int hashCode() {
        return Objects.hash(head, direction); // Compute hash code based on origin point and direction vector.
    }

    /**
     * Returns a string representation of this Ray object.
     *
     * @return A string representation containing details of the origin point and direction vector.
     */
    @Override
    public String toString() {
        return "Ray{" +
                "_p0=" + head +
                ", _dir=" + direction +
                '}';
    }
}