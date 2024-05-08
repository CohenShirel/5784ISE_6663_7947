package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * A class representing a cylinder geometry, which is a type of tube geometry with a finite height.
 */
public class Cylinder extends Tube implements Geometry {

    private double cylinderHeight; // The height of the cylinder.

    /**
     * Constructs a new Cylinder object with a given axis ray, radius, and cylinder height.
     *
     * @param axisRay        The axis ray of the cylinder.
     * @param radius         The radius of the cylinder.
     * @param cylinderHeight The height of the cylinder.
     */
    public Cylinder(Ray axisRay, double radius, double cylinderHeight) {
        super(axisRay, radius); // Call to superclass constructor
        this.cylinderHeight = cylinderHeight; // Assigning cylinderHeight
    }

    /**
     * Returns the height of the cylinder.
     *
     * @return The height of the cylinder.
     */
    public double getCylinderHeight() {
        return cylinderHeight; // Changed method name to getCylinderHeight
    }
    @Override
    public Vector getNormal(Point point) {
        return super.getNormal(point);
    }
}
