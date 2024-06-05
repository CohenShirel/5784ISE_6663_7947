package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;

/**
 * Class to implement Camera to render the picture
 *
 * we want to get the camera to our project, so we will open the Camera class
 */
public class Camera {

    private Point p0; // the center's camera point
    private Vector vTo; // the vector towards the view plane
    private Vector vUp; // vector to camera's up direction
    private Vector vRight; // vector to camera's right direction
    private double distance; // the distance between the camera and the view plane

    //=== the view plane size ===//
    private double width;
    private double height;

    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;

    //===== private constructor to initialize camera via Builder =======//
    private Camera(Builder builder) {
        this.p0 = builder.p0;
        this.vTo = builder.vTo;
        this.vUp = builder.vUp;
        this.vRight = this.vTo.crossProduct(this.vUp);
        this.distance = builder.distance;
        this.width = builder.width;
        this.height = builder.height;
        this.imageWriter = builder.imageWriter;
        this.rayTracer = builder.rayTracer;
    }

    public Point getP0() { return this.p0; }

    public Vector getvTo() { return this.vTo; }

    public Vector getvUp() { return this.vUp; }

    public Vector getvRight() { return this.vRight; }

    public double getWidth() { return this.width; }

    public double getHeight() { return this.height; }

    //================== Builder class ==================//
    public static class Builder {
        private Point p0; // the center's camera point
        private Vector vTo; // the vector towards the view plane
        private Vector vUp; // vector to camera's up direction
        private double distance; // the distance between the camera and the view plane

        //=== the view plane size ===//
        private double width;
        private double height;

        private ImageWriter imageWriter;
        private RayTracerBase rayTracer;

        public Builder setLocation(Point p0) {
            this.p0 = p0;
            return this;
        }

        public Builder setDirection(Vector vTo, Vector vUp) {
            if (!isZero(vTo.dotProduct(vUp))) {
                throw new IllegalArgumentException("vTo and vUp are not orthogonal");
            }
            this.vTo = vTo.normalize();
            this.vUp = vUp.normalize();
            return this;
        }

        public Builder setVPDistance(double distance) {
            this.distance = distance;
            return this;
        }

        public Builder setVPSize(double width, double height) {
            this.width = width;
            this.height = height;
            return this;
        }

        public Builder setImageWriter(ImageWriter imageWriter) {
            this.imageWriter = imageWriter;
            return this;
        }

        public Builder setRayTracer(RayTracerBase rayTracer) {
            this.rayTracer = rayTracer;
            return this;
        }

        public Camera build() {
            return new Camera(this);
        }
    }

    //================== Static method to get Builder instance ==================//
    public static Builder getBuilder() {
        return new Builder();
    }

    /**
     * Constructs a ray from Camera location through the center of a
     * pixel (i,j) in the view plane.
     *
     * @param Nx number of pixels in a row of view plane
     * @param Ny number of pixels in a column of view plane
     * @param j  number of the pixel in a row
     * @param i  number of the pixel in a column
     * @return The ray through pixel's center
     */
    public Ray constructRay(int Nx, int Ny, int j, int i) {
        double Ry = (double) this.height / Ny;
        double Rx = (double) this.width / Nx;

        Point Pc = this.p0.add(this.vTo.scale(this.distance));
        Point Pij = Pc;
        double Yi = - (i - ((Ny - 1) / 2d)) * Ry;
        double Xj = (j - ((Nx - 1) / 2d)) * Rx;

        if (!isZero(Xj)) {
            Pij = Pij.add(this.vRight.scale(Xj));
        }
        if (!isZero(Yi)) {
            Pij = Pij.add(this.vUp.scale(Yi));
        }
        return new Ray(this.p0, Pij.subtract(this.p0));
    }

    public void renderImage() {
        if (this.imageWriter == null)
            throw new UnsupportedOperationException("Missing imageWriter");
        if (this.rayTracer == null)
            throw new UnsupportedOperationException("Missing rayTracerBase");

        for (int i = 0; i < this.imageWriter.getNy(); i++) {
            for (int j = 0; j < this.imageWriter.getNx(); j++) {
                Color color = castRay(j, i);
                this.imageWriter.writePixel(j, i, color);
            }
        }
    }

    public void printGrid(int interval, Color color) {
        for (int i = 0; i < imageWriter.getNx(); i++) {
            for (int j = 0; j < imageWriter.getNy(); j++) {
                if (i % interval == 0 || j % interval == 0) {
                    imageWriter.writePixel(i, j, color);
                }
            }
        }
    }

    public void writeToImage() {
        this.imageWriter.writeToImage();
    }

    private Color castRay(int j, int i) {
        Ray ray = constructRay(this.imageWriter.getNx(), this.imageWriter.getNy(), j, i);
        return this.rayTracer.traceRay(ray);
    }
}
