package renderer;
import java.util.MissingResourceException;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;

/**
 * Class to implement Camera to render the picture
 */
public class Camera implements Cloneable {

    private Point _p0;
    private Vector _vTo;
    private Vector _vUp;
    private Vector _vRight;
    private double _distance=0.0;
    private double _width=0.0;
    private double _height=0.0;

    /**
     * Private default constructor to prevent direct instantiation.
     */
    private Camera() {
    }
    
    /**
     * Returns a new Builder instance for constructing a Camera object.
     *
     * @return a new Builder instance.
     */
    public static Builder getBuilder() {
        return new Builder();
    }
    
    /**
     * Constructs a ray from Camera location throw the center of a
     * pixel (i,j) in the view plane.
     *
     * @param Nx number of pixels in a row of view plane
     * @param Ny number of pixels in a column of view plane
     * @param j  number of the pixel in a row
     * @param i  number of the pixel in a column
     * @return The ray through pixel's center
     */
    public Ray constructRay(int Nx, int Ny, int j, int i) {
        // Ratio (pixel width & height)//
        double Ry= (double) _height /Ny;
        double Rx = (double) _width /Nx;

        //image center//
        Point Pc = _p0.add(_vTo.scale(_distance));
        Point Pij =Pc;
        double Yi = - (i-((Ny - 1)/2d))* Ry;
        double Xj = (j-((Nx - 1)/2d))* Rx;

        //move to middle of pixel i,j
        if (!isZero(Xj)) { // vRight need to be scaled with xj, so it cannot be zero
            Pij = Pij.add(_vRight.scale(Xj));
        }
        if (!isZero(Yi)) {// vUp need to be scaled with yi, so it cannot be zero
            Pij = Pij.add(_vUp.scale(Yi));
        }
        return new Ray(_p0, Pij.subtract(_p0));
    }
    
    /**
     * Gets the location of the camera.
     *
     * @return the location of the camera.
     */
    public Point getP0() {
        return _p0;
    }

    /**
     * Gets the toward direction vector of the camera.
     *
     * @return the toward direction vector.
     */
    public Vector getvT0() {
        return _vTo;
    }

    /**
     * Gets the up direction vector of the camera.
     *
     * @return the up direction vector.
     */
    public Vector getvUp() {
        return _vUp;
    }

    /**
     * Gets the right direction vector of the camera.
     *
     * @return the right direction vector.
     */
    public Vector getvRight() {
        return _vRight;
    }

    /**
     * Gets the width of the view plane.
     *
     * @return the width of the view plane.
     */
    public double getWidth() {
        return _width;
    }

    /**
     * Gets the height of the view plane.
     *
     * @return the height of the view plane.
     */
    public double getHeight() {
        return _height;
    }
    
    /**
     * Gets the distance of the view plane from the camera.
     *
     * @return the distance of the view plane from the camera.
     */
    public double getDistance() {
        return _distance;
    }
    //Chaining methods.

    /**
     * Set view plane distance
     * @param distance - the distance between the camara and view plane
     * @return The camara distance
     */
    public Camera setVPDistance(double distance) {
        _distance = distance;
        return this;
    }

    /**
     * Set view plane size
     * @param width width of the view plane
     * @param height height of the view plane
     * @return The view plane size
     */
    public Camera setVPSize(double width, double height) {
        _width = width;
        _height =height;
        return this;
    }
    
    /**
     * Builder class for constructing Camera objects.
     */
    public static class Builder {
        private static final String MISSING_RENDER_DATA = "Missing render data";
        private final Camera camera;

        /**
         * Default constructor initializes a new Camera object.
         */
        public Builder() {
            this.camera = new Camera();
        }

        /**
         * Constructor that initializes the builder with an existing Camera object.
         *
         * @param camera the Camera object to initialize the builder with.
         */
        public Builder(Camera camera) {
            this.camera = camera;
        }

        /**
         * Sets the location of the camera.
         *
         * @param location the location of the camera.
         * @return the current Builder instance.
         * @throws IllegalArgumentException if the location is null.
         */
        public Builder setLocation(Point location) {
            if (location == null) {
                throw new IllegalArgumentException("Location cannot be null");
            }
            this.camera._p0 = location;
            return this;
        }

        /**
         * Sets the direction vectors of the camera.
         *
         * @param vTo the toward direction vector.
         * @param vUp the up direction vector.
         * @return the current Builder instance.
         * @throws IllegalArgumentException if any of the vectors is null or they are not orthogonal.
         */
        public Builder setDirection(Vector vTo, Vector vUp) {
            if (vTo == null || vUp == null) {
                throw new IllegalArgumentException("Direction vectors cannot be null");
            }
            if (!isOrthogonal(vTo, vUp)) {
                throw new IllegalArgumentException("Direction vectors must be orthogonal");
            }
            this.camera._vTo = vTo. normalize();
            this.camera._vUp = vUp.normalize();
            return this;
        }
        /**
         * Sets the size of the view plane.
         *
         * @param width  the width of the view plane.
         * @param height the height of the view plane.
         * @return the current Builder instance.
         * @throws IllegalArgumentException if width or height is less than or equal to zero.
         */
        public Builder setVpSize(double width, double height) {
            if (width <= 0 || height <= 0) {
                throw new IllegalArgumentException("Width and height must be greater than zero");
            }
            this.camera._width = width;
            this.camera._height = height;
            return this;
        }

        /**
         * Sets the distance of the view plane from the camera.
         *
         * @param distance the distance of the view plane from the camera.
         * @return the current Builder instance.
         * @throws IllegalArgumentException if the distance is less than or equal to zero.
         */
        public Builder setVpDistance(double distance) {
            if (distance <= 0) {
                throw new IllegalArgumentException("Distance must be greater than zero");
            }
            this.camera._distance = distance;
            return this;
        }

        /**
         * Builds and returns the Camera object.
         *
         * @return the constructed Camera object.
         * @throws MissingResourceException if any required fields are missing or invalid.
         */
        public Camera build() {
            if (this.camera._p0 == null) {
                throw new MissingResourceException(MISSING_RENDER_DATA, Camera.class.getName(), "location");
            }
            if (this.camera._vTo == null || this.camera._vUp == null) {
                throw new MissingResourceException(MISSING_RENDER_DATA, Camera.class.getName(), "direction vectors");
            }
            if (this.camera._width == 0) {
                throw new MissingResourceException(MISSING_RENDER_DATA, Camera.class.getName(), "view plane width");
            }
            if (this.camera._height == 0) {
                throw new MissingResourceException(MISSING_RENDER_DATA, Camera.class.getName(), "view plane height");
            }
            if (this.camera._distance == 0) {
                throw new MissingResourceException(MISSING_RENDER_DATA, Camera.class.getName(), "view plane distance");
            }

            // Calculate the right direction vector
            this.camera._vRight = this.camera._vTo.crossProduct(this.camera._vUp).normalize();

            // Return a clone of the constructed camera
            try {
                return (Camera) this.camera.clone();
            } catch (CloneNotSupportedException e) {
                throw new AssertionError("Cloning not supported", e);
            }
        }

        /**
         * Checks if two vectors are orthogonal.
         *
         * @param v1 the first vector.
         * @param v2 the second vector.
         * @return true if the vectors are orthogonal, false otherwise.
         */
        private boolean isOrthogonal(Vector v1, Vector v2) {
            return v1.dotProduct(v2) == 0;
        }
    }
}