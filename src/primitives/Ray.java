package primitives;

import java.util.Objects;

/**
 * Represents a ray in 3D space, defined by an origin point and a direction vector.
 */
public class Ray {

    final Point head;
    final Vector direction;

    /**
     * Constructs a new Ray object with a given origin point and direction vector.
     *
     * @param p0  The origin point of the ray.
     * @param dir The direction vector of the ray.
     */
    public Ray(Point p0, Vector dir) {
        head = p0;
        direction = dir.normalize();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return head.equals(ray.head) && direction.equals(ray.direction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(head, direction);
    }
    @Override
    public String toString() {
        return "Ray{" +
                "_p0=" + head +
                ", _dir=" + direction +
                '}';
    }
}
