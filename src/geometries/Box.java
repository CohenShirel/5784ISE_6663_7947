package geometries;

import java.util.List;

import geometries.Intersectable.GeoPoint;
import primitives.*;

public class Box {
    private final Geometries box = new Geometries();

    /**
     * Constructor to initialize a 3D box object
     *
     * @param center point of the box
     * @param length of the box
     * @param width of the box
     * @param height of the box
     * @param vUp of the box
     * @param vRight of the box
     * @param emission of the box
     * @param material of the box
     * @throws IllegalArgumentException when vUp and vRight are not orthogonal
     */
    public Box(
            Point center,
            double length,
            double width,
            double height,
            Vector vUp,
            Vector vRight,
            Color emission,
            Material material
    ) throws IllegalArgumentException {
        if (vUp.dotProduct(vRight) != 0)
            throw new IllegalArgumentException("vUp and vRight are not orthogonal");

        Vector vTo = vUp.crossProduct(vRight);

        box.add(
                constructsRectangle(
                        center.add(vTo.scale(length / 2)),
                        vUp,
                        vRight,
                        width,
                        height
                ).setEmission(emission).setMaterial(material),
                constructsRectangle(
                        center.add(vTo.scale(-length / 2)),
                        vUp,
                        vRight,
                        width,
                        height
                ).setEmission(emission).setMaterial(material),
                constructsRectangle(
                        center.add(vRight.scale(width / 2)),
                        vUp,
                        vTo,
                        length,
                        height
                ).setEmission(emission).setMaterial(material),
                constructsRectangle(
                        center.add(vRight.scale(-width / 2)),
                        vUp,
                        vTo,
                        length,
                        height
                ).setEmission(emission).setMaterial(material),
                constructsRectangle(
                        center.add(vUp.scale(height / 2)),
                        vRight,
                        vTo,
                        length,
                        width
                ).setEmission(emission).setMaterial(material),
                constructsRectangle(
                        center.add(vUp.scale(-height / 2)),
                        vRight,
                        vTo,
                        length,
                        width
                ).setEmission(emission).setMaterial(material)
        );
    }

    /**
     * Gets the box as geometries
     * @return box as geometries
     */
    public Geometries getBox() {
        return this.box;
    }

    /**
     * Constructs a rectangle of the box
     *
     * @param center point of the rectangle
     * @param vUp of the rectangle
     * @param vRight of the rectangle
     * @param width of the rectangle
     * @param height of the rectangle
     * @return the rectangle
     */
    private Polygon constructsRectangle(Point center, Vector vUp, Vector vRight, double width, double height) {
        double sideScale = width / 2;
        double upScale = height / 2;

        Point centerRight = center.add(vRight.scale(sideScale));
        Point centerLeft = center.add(vRight.scale(-sideScale));

        return new Polygon(
                centerRight.add(vUp.scale(upScale)),
                centerLeft.add(vUp.scale(upScale)),
                centerLeft.add(vUp.scale(-upScale)),
                centerRight.add(vUp.scale(-upScale))
        );
    }

 
}
