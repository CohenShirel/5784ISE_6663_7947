package geometries;

/**
 * Class RadialGeometry is the basic class representing a radial geometry in Euclidean geometry
 */
public abstract class RadialGeometry extends Geometry{
    /**
     * The radius of the geometry
     */
    protected final double radius;

    /**
     * @param radius the radius of the geometry
     */
    public RadialGeometry(double radius) {
        this.radius = radius;
    }
}