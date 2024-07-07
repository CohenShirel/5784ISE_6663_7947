package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.MissingResourceException;

/**
 * The Camera class represents a camera in the scene.
 */
public class Camera implements Cloneable{


    /**
     * The location of the camera
     */
    public static class Builder {
        private final Camera camera = new Camera();

        public Builder() {}


        /**
         * Set the location of the camera
         * @param location
         * @return this
         */
        public Builder setLocation(Point location){
            camera.location = location;
            return this;
        }

        /**
         * Set the direction of the camera
         * @param Vto
         * @param Vup
         * @return this
         */
        public Builder setDirection(Vector Vto,Vector Vup){
            if (Vto.crossProduct(Vup).length() == 0)
                throw new IllegalArgumentException("Vto and Vup are parallel");
            camera.vTo = Vto.normalize();
            camera.vUp = Vup.normalize();
            camera.vRight = Vto.crossProduct(Vup).normalize();
            return this;
        }

        /**
         * Set the size of the view plane
         * @param heightViewPlane
         * @param widthViewPlane
         * @return this
         */
        public Builder setVpSize(double heightViewPlane, double widthViewPlane){
            if (heightViewPlane < 0 || widthViewPlane < 0)
                throw new IllegalArgumentException("The height and width of the view plane must be positive");
            camera.heightViewPlane = heightViewPlane;
            camera.widthViewPlane = widthViewPlane;
            return this;
        }

        /**
         * Set the distance of the view plane
         * @param distanceFromViewPlane distance from the view plane
         * @return this
         */
        public Builder setVpDistance(double distanceFromViewPlane){
            if (distanceFromViewPlane < 0)
                throw new IllegalArgumentException("The distance from the view plane must be positive");
            camera.distanceFromViewPlane = distanceFromViewPlane;
            return this;
        }

        /**
         * Sets the image writer for the camera.
         *
         * @param  imageWriter  the image writer to be set
         * @return              the updated Builder instance
         */
        public Builder setImageWriter(ImageWriter imageWriter){
            camera.imageWriter = imageWriter;
            return this;
        }

        /**
         * Sets the ray tracer for the camera and returns the updated Builder.
         *
         * @param  rayTracer  the ray tracer to be set
         * @return            the updated Builder
         */
        public Builder setRayTracer(RayTracerBase rayTracer){
            camera.rayTracer = rayTracer;
            return this;
        }

        /**
         * Build the camera
         * @return Camera
         */
        public Camera build() {
            String missingResource = "Missing Resource";
            if(camera.location == null)
                throw new MissingResourceException(missingResource,Camera.class.getSimpleName(),"location");
            if(camera.vTo == null || camera.vUp == null)
                throw new MissingResourceException(missingResource,Camera.class.getSimpleName(),"direction");
            if(camera.heightViewPlane == 0.0 || camera.widthViewPlane == 0.0)
                throw new MissingResourceException(missingResource,Camera.class.getSimpleName(),"vpSize");
            if(camera.distanceFromViewPlane == 0.0)
                throw new MissingResourceException(missingResource,Camera.class.getSimpleName(),"vpDistance");
            if(camera.imageWriter == null)
                throw new MissingResourceException(missingResource,Camera.class.getSimpleName(),"imageWriter");
            if (camera.rayTracer == null)
                throw new MissingResourceException(missingResource,Camera.class.getSimpleName(),"rayTracer");

            if(camera.vTo.crossProduct(camera.vUp).length() == 0)
                throw new IllegalArgumentException("Vto and Vup are parallel");
            if(camera.heightViewPlane < 0.0 || camera.widthViewPlane < 0.0)
                throw new IllegalArgumentException("Negative size");
            if(camera.distanceFromViewPlane < 0.0)
                throw new IllegalArgumentException("Negative distance");

            camera.viewPlaneCenter = camera.location.add(camera.vTo.scale(camera.distanceFromViewPlane));

            return camera.clone();
        }
    }

    private Point location;
    private Vector vTo;
    private Vector vRight;
    private Vector vUp;
    private double heightViewPlane = 0.0;
    private double widthViewPlane = 0.0;
    private double distanceFromViewPlane = 0.0;
    private Point viewPlaneCenter;
    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;
    private Camera(){}

    /**
     * Get the height of the view plane
     * @return Height of the view plane
     */
    public double getHeightViewPlane() {
        return heightViewPlane;
    }

    /**
     * Get the width of the view plane
     * @return Width of the view plane
     */
    public double getWidthViewPlane() {
        return widthViewPlane;
    }

    /**
     * get Location of the camera
     * @return Distance of the view plane
     */
    public double getDistanceFromViewPlane() {
        return distanceFromViewPlane;
    }

    /**
     * Get the builder
     * @return Builder
     */
    public static Builder getBuilder(){
        return new Builder();
    }

    /**
     * Set the location of the camera
     * @param nX - width of the view plane
     * @param nY - height of the view plane
     * @param j - index in the column
     * @param i - index in the row
     * @return Ray
     */
    public Ray constructRay(int nX, int nY, int j, int i){
        double rY = heightViewPlane / nY;
        double rX = widthViewPlane / nX;
        double xJ = (j - ((nX - 1) / 2.0))* rX;
        double yI = -(i - ((nY - 1) / 2.0)) * rY;
        Point pIJ = this.viewPlaneCenter;
        if (xJ != 0)
            pIJ = pIJ.add(vRight.scale(xJ));
        if (yI != 0)
            pIJ = pIJ.add(vUp.scale(yI));
        Vector Vij = pIJ.subtract(location);
        return new Ray(location, Vij);
    }

    /**
     * Renders the image using ray casting.
     *
     * @return  the Camera object after rendering the image
     */
    public Camera renderImage(){
        int nx = imageWriter.getNx();
        int ny = imageWriter.getNy();
        for (int i = 0; i < nx; i++) {
            for (int j = 0; j < ny; j++) {
                if(i == 226 && j == 326) {
                    castRay(nx, ny, i, j);
                }
                castRay(nx, ny, i, j);
            }
        }
        return this;
    }

    /**
     * Casts a ray and writes the resulting color to the image.
     *
     * @param  nx  the x coordinate of the pixel
     * @param  ny  the y coordinate of the pixel
     * @param  i   the x coordinate of the image
     * @param  j   the y coordinate of the image
     */
    private void castRay(int nx, int ny, int i, int j) {
        imageWriter.writePixel(j, i, rayTracer.traceRay(constructRay(nx, ny, j, i)));
    }

    /**
     * Prints a grid on the imageWriter with a given color.
     *
     * @param  nX     the spacing of the grid lines
     * @param  color  the color of the grid lines
     * @return       the Camera object for method chaining
     */
    public Camera printGrid(int nX, Color color){
        int nx = imageWriter.getNx();
        int ny = imageWriter.getNy();
        for (int i = 0; i < nx; i++) {
            for (int j = 0; j < ny; j++) {
                if (i % nX == 0 || j % nX == 0)
                    imageWriter.writePixel(i, j , color);
            }
        }
        return this;
    }

    /**
     * Writes to the image.
     *
     * @param  -   No parameters
     * @return  -   No return value
     */
    public void writeToImage(){
        imageWriter.writeToImage();
    }
    @Override
    public Camera clone() {
        try {
            Camera clonedCamera = (Camera) super.clone();
            // Deep clone mutable fields
            clonedCamera.location = this.location != null ? this.location : null;
            clonedCamera.vTo = this.vTo != null ? this.vTo : null;
            clonedCamera.vRight = this.vRight != null ? this.vRight : null;
            clonedCamera.vUp = this.vUp != null ? this.vUp : null;
            clonedCamera.viewPlaneCenter = this.viewPlaneCenter != null ? this.viewPlaneCenter : null;
            clonedCamera.imageWriter = this.imageWriter != null ? this.imageWriter : null;
            clonedCamera.rayTracer = this.rayTracer != null ? this.rayTracer : null;

            return clonedCamera;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}