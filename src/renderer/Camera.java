package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Camera implements Cloneable {

    private Point p0;
    private Vector vTo;
    private Vector vUp;
    private Vector vRight;
    private double distance;
    private double width;
    private double height;

    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;

    /**
     * Default constructor for the Camera class.
     * This is a private constructor to prevent direct instantiation.
     */
    private Camera() {
    }
    
    public Camera(Point p0, Vector vTo, Vector vUp) {
        if (!isZero(vTo.dotProduct(vUp))) {
            throw new IllegalArgumentException("vTo and vUp are not orthogonal");
        }

        this.p0 = p0;
        this.vTo = vTo.normalize();
        this.vUp = vUp.normalize();
        this.vRight = vTo.crossProduct(vUp);
    }
    /**
     * Creates a new Builder for constructing Camera instances.
     *
     * @return a new Builder instance
     */
    public static Builder getBuilder() {
        return new Builder();
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
    	 private final Camera camera = new Camera();
    	 /**
          * Sets the location of the camera.
          *
          * @param location the location of the camera
          * @return this Builder instance
          */
         public Builder setLocation(Point location) {
             camera.p0 = location;
             return this;
         }

         /**
          * Sets the direction of the camera.
          *
          * @param toVector the vector pointing forward
          * @param upVector the vector pointing upwards
          * @return this Builder instance
          * @throws IllegalArgumentException if the vectors are not perpendicular or not normalized
          */
         public Builder setDirection(Vector toVector, Vector upVector) {
             if (toVector.dotProduct(upVector) != 0) {
                 throw new IllegalArgumentException("The vectors must be orthogonal");
             }
             camera.vTo = toVector.normalize();
             camera.vUp = upVector.normalize();
             camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();
             return this;
         }

         /**
          * Sets the size of the view plane.
          *
          * @param width  the width of the view plane
          * @param height the height of the view plane
          * @return this Builder instance
          * @throws IllegalArgumentException if width or height is not positive
          */
         public Builder setVpSize(double width, double height) {
             camera.width = width;
             camera.height = height;
             return this;
         }

         /**
          * Sets the distance between the camera and the view plane.
          *
          * @param distance the distance between the camera and the view plane
          * @return this Builder instance
          * @throws IllegalArgumentException if the distance is not positive
          */
         public Builder setVpDistance(double distance) {
             if (distance <= 0) {
                 throw new IllegalArgumentException("Distance must be a positive value.");
             }
             camera.distance = distance;
             return this;
         }

         /**
          * Sets the ImageWriter for the camera.
          *
          * @param imageWriter the ImageWriter to set
          * @return this Builder instance
          */
         public Builder setImageWriter(ImageWriter imageWriter) {
             camera.imageWriter = imageWriter;
             return this;
         }

         /**
          * Sets the RayTracer for the camera.
          *
          * @param rayTracer the RayTracer to set
          * @return this Builder instance
          */
         public Builder setRayTracer(RayTracerBase rayTracer) {
             camera.rayTracer = rayTracer;
             return this;
         }
         
        public Camera build() {
            if (camera.p0 == null) {
                throw new IllegalStateException("Missing rendering data: location (p0)");
            }
            if (camera.vUp == null) {
                throw new IllegalStateException("Missing rendering data: vUp");
            }
            if (camera.vTo == null) {
                throw new IllegalStateException("Missing rendering data: vTo");
            }
            if (camera.imageWriter == null) {
                throw new IllegalStateException("Missing rendering data: imageWriter");
            }
            if (camera.rayTracer == null) {
                throw new IllegalStateException("Missing rendering data: rayTracer");
            }
            if (alignZero(camera.width) == 0) {
                throw new IllegalStateException("Missing rendering data: width");
            }
            if (alignZero(camera.height) == 0) {
                throw new IllegalStateException("Missing rendering data: height");
            }
            if (alignZero(camera.distance) == 0) {
                throw new IllegalStateException("Missing rendering data: distance");
            }

            camera.vUp = camera.vUp.normalize();
            camera.vTo = camera.vTo.normalize();
            camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();
            try {
                return (Camera) camera.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }
    }

  
}