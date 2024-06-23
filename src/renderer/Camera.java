package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;

public class Camera {

    private Point p0;
    private Vector vTo;
    private Vector vUp;
    private Vector vRight;
    private double distance;

    private double width;
    private double height;

    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;

    public Camera(Point p0, Vector vTo, Vector vUp) {
        if (!isZero(vTo.dotProduct(vUp))) {
            throw new IllegalArgumentException("vTo and vUp are not orthogonal");
        }

        this.p0 = p0;
        this.vTo = vTo.normalize();
        this.vUp = vUp.normalize();
        this.vRight = vTo.crossProduct(vUp);
    }

    public Point getP0() { return this.p0; }

    public Vector getvT0() { return this.vTo; }

    public Vector getvUp() { return this.vUp; }

    public Vector getvRight() { return this.vRight; }

    public double getWidth() { return this.width; }

    public double getHeight() { return this.height; }

    public Camera setVPDistance(double distance) {
        this.distance = distance;
        return this;
    }

    public Camera setVPSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public Ray constructRay(int Nx, int Ny, int j, int i) {
        double Ry = (double) this.height / Ny;
        double Rx = (double) this.width / Nx;

        Point Pc = this.p0.add(this.vTo.scale(this.distance));
        Point Pij = Pc;
        double Yi = -(i - ((Ny - 1) / 2d)) * Ry;
        double Xj = (j - ((Nx - 1) / 2d)) * Rx;

        if (!isZero(Xj)) {
            Pij = Pij.add(this.vRight.scale(Xj));
        }
        if (!isZero(Yi)) {
            Pij = Pij.add(this.vUp.scale(Yi));
        }
        return new Ray(this.p0, Pij.subtract(this.p0));
    }

    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    public Camera setRayTracer(RayTracerBase rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    public Camera renderImage() {
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
        return this;
    }

    public Camera printGrid(int interval, Color color) {
        for (int i = 0; i < imageWriter.getNx(); i++) {
            for (int j = 0; j < imageWriter.getNy(); j++) {
                if (i % interval == 0 || j % interval == 0) {
                    imageWriter.writePixel(i, j, color);
                }
            }
        }
        return this;
    }

    public Camera writeToImage() {
        this.imageWriter.writeToImage();
        return this;
    }

    private Color castRay(int j, int i) {
        Ray ray = constructRay(
                this.imageWriter.getNx(),
                this.imageWriter.getNy(),
                j,
                i);
        return this.rayTracer.traceRay(ray);
    }

    public static class Builder {
        private Point location;
        private Vector vTo;
        private Vector vUp;
        private double distance;
        private double width;
        private double height;
        private ImageWriter imageWriter;
        private RayTracerBase rayTracer;

        public Builder setLocation(Point location) {
            this.location = location;
            return this;
        }

        public Builder setDirection(Point location, Vector vUp) {
            this.location = location;
            this.vUp = vUp;
            return this;
        }

        public Builder setVpDistance(double distance) {
            this.distance = distance;
            return this;
        }

        public Builder setVpSize(double width, double height) {
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
            Camera camera = new Camera(location, vTo, vUp);
            camera.setVPDistance(distance);
            camera.setVPSize(width, height);
            camera.setImageWriter(imageWriter);
            camera.setRayTracer(rayTracer);
            return camera;
        }
    }

    public static Builder getBuilder() {
        return new Builder();
    }
}

