package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

// A class representing a cylinder geometry
public class Cylinder extends Tube implements Geometry {

    private double cylinderHeight; 

    // Constructor for the Cylinder class
    public Cylinder(Ray axisRay, double radius, double cylinderHeight) {
        super(axisRay, radius); // Call to superclass constructor
        this.cylinderHeight = cylinderHeight; // Assigning cylinderHeight
    }

    // Getter for the height of the cylinder
    public double getCylinderHeight() {
        return cylinderHeight; // Changed method name to getCylinderHeight
    }

    @Override
    // Method that returns the normal vector of the cylinder at a given point
    public Vector getNormal(Point point) {
        return super.getNormal(point);
    }
}