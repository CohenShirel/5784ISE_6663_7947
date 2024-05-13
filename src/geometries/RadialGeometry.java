package geometries;

/**
 * An abstract class representing a radial geometry, such as a sphere or a cylinder, defined by a radius.
 */
public abstract class RadialGeometry {

    private double radius;

    /**
     * Constructs a new RadialGeometry object with the specified radius.
     *
     * @param radius The radius of the radial geometry.
     */
    RadialGeometry(double radius) {
        this.radius = radius;
    }
}
