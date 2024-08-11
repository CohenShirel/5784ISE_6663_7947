package renderer;

import primitives.*;
import primitives.Point;

import java.util.List;
import java.util.MissingResourceException;
import java.util.stream.IntStream;

import static primitives.Util.*;

public class Camera {
    private Point position;
    private Vector vTo;
    private Vector vUp;
    private Vector vRight;
    private int height;
    private int width;
    private double distance;
    private Point viewPlaneCenter;
    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;
    private final SuperSampling antiAliasing = new SuperSampling();

    /**
     * Amount of threads for multi threading, if not set is 0, so no multi threading is done
     */
    private int threads = 0;

    /**
     * Camera object constructor
     *
     * @param position the Point on which the camera is based
     * @param vTo the Vector of where the camera is looking at
     * @param vUp the Vector of the upwards direction of the camera
     * @throws IllegalArgumentException if vTo and vUp are not orthogonal
     */
    public Camera(Point position, Vector vTo, Vector vUp) throws IllegalArgumentException {
        if (!isZero(vTo.dotProduct(vUp)))
            throw new IllegalArgumentException("vTo and vUp must be orthogonal to each other");

        this.position = position;
        this.vTo = vTo.normalize();
        this.vUp = vUp.normalize();
        this.vRight = this.vTo.crossProduct(this.vUp);
    }


    /**
     * Ray tracing color result
     *
     * @param ray ray
     * @return color
     */
    private Color castRay(Ray ray) {
        return this.rayTracer.traceRay(ray);
    }


    /**
     * Get the position of the camera
     *
     * @return the position Point of the camera
     */
    public Point getPosition() {
        return this.position;
    }

    /**
     * Get the Vector of where the camera is looking at (vTo)
     * @return the vTo Vector
     */
    public Vector getVTo() {
        return this.vTo;
    }

    /**
     * Get the Vector of the upwards direction of the camera (vUp)
     *
     * @return the vUp Vector
     */
    public Vector getVUp() {
        return this.vUp;
    }

    /**
     * Get the Vector of the rightwards direction of the camera (vRight)
     *
     * @return the vRight Vector
     */
    public Vector getVRight() {
        return this.vRight;
    }

    /**
     * Get the View Plane height
     *
     * @return View Plane height
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Get the View Plane width
     *
     * @return View Plane width
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Get the distance of the camera from the View Plane
     *
     * @return the distance of the camera from the View Plane
     */
    public double getDistance() {
        return this.distance;
    }


    /**
     * Set the View Plane size
     *
     * @param width View Plane width
     * @param height View Plane height
     * @return the updated Camera object
     */
    public Camera setVPSize(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    /**
     * Set the View Plane distance from the camera
     *
     * @param distance distance from the camera
     * @return the updated Camera object
     */
    public Camera setVPDistance(double distance) {
        this.distance = distance;
        return this;
    }

    /**
     * Set the Image Writer
     *
     * @param imageWriter Image Writer
     * @return the updated Camera object
     */
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    /**
     * Set the Ray Tracer Base
     *
     * @param rayTracer Ray Tracer Base
     * @return the updated Camera object
     */
    public Camera setRayTracer(RayTracerBase rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    /**
     * Set the anti aliasing rays grid
     *
     * @param amount of rays
     * @return the updated Camera object
     */
    public Camera setAntiAliasing(int amount) {
        this.antiAliasing.setSize(amount);
        return this;
    }

     /**
     * Creates the rays that pass at the center of the requested pixel on the View Plane
     *
     * @param nX amount of column in the View Plane
     * @param nY amount of rows in the View Plane
     * @param j pixel column index
     * @param i pixel row index
     * @return the ray that passes at the center of the requested pixel on the View Plane
     */
    public List<Ray> constructRay(int nX, int nY, int j, int i) {
        // Image Center
        Point pIJ = this.position.add(this.vTo.scale(this.distance));

        // Ratio (pixel width & height)
        double rX = (double) this.width / nX;
        double rY = (double) this.height / nY;

        // Construct rays
        double xJ = (j - ((double) (nX - 1) / 2)) * rX;
        double yI = -(i - ((double) (nY - 1) / 2)) * rY;

        if (xJ != 0)
            pIJ = pIJ.add(vRight.scale(xJ));
        if (yI != 0)
            pIJ = pIJ.add(vUp.scale(yI));

        return this.antiAliasing.constructRaysThroughGrid(rX, rY,position, pIJ, this.vUp, this.vRight);
    }

    /**
     * Set the location of the camera
     * @param nX - width of the view plane
     * @param nY - height of the view plane
     * @param j - index in the column
     * @param i - index in the row
     * @return Ray
     */
    public Ray constructRay2(int nX, int nY, int j, int i){
        double rY = height / nY;
        double rX = width / nX;
        double xJ = (j - ((nX - 1) / 2.0))* rX;
        double yI = -(i - ((nY - 1) / 2.0)) * rY;
        Point pIJ = this.viewPlaneCenter;
        if (xJ != 0)
            pIJ = pIJ.add(vRight.scale(xJ));
        if (yI != 0)
            pIJ = pIJ.add(vUp.scale(yI));
        Vector Vij = pIJ.subtract(position);
        return new Ray(position, Vij);
    }
    /**
     * Renders an image using the camera's settings.
     *
     * @throws MissingResourceException      if any of the required camera settings are missing
     * @throws UnsupportedOperationException if the method has not been implemented yet
     */
    public ImageWriter renderImage() {
        try {

            //place has not been set
            if (position == null)
                throw new MissingResourceException("missing point place for camera", "Camera", "place");

            //vTo has not been set
            if (vTo == null)
                throw new MissingResourceException("missing vTo place for camera", "Camera", "vTo");

            //vUp has not been set
            if (vUp == null)
                throw new MissingResourceException("missing vUp place for camera", "Camera", "vUp");

            //vRight has not been set
            if (vRight == null)
                throw new MissingResourceException("missing vRight place for camera", "Camera", "vRight");

            //imageWriter has not been set
            if (imageWriter == null)
                throw new MissingResourceException("missing imageWriter place for camera", "Camera", "imageWriter");

            //rayTracerBase has not been set
            if (rayTracer == null)
                throw new MissingResourceException("missing RayTracerBase place for camera", "Camera", "rayTracerBase");

            //width has not been set
            if (width == 0.0)
                throw new MissingResourceException("missing width place for camera", "Camera", "width");

            //height has not been set
            if (height == 0.0)
                throw new MissingResourceException("missing height place for camera", "Camera", "height");


            //distance has not been set
            if (distance == 0.0)
                throw new MissingResourceException("missing distance place for camera", "Camera", "distance");

            int nY = imageWriter.getNy();
            int nX = imageWriter.getNx();
            //if not using multi threads
            if (threads < 1) {
                //goes through every pixel in view plane  and casts ray, meaning creates a ray for every pixel and sets the color
                for (int row = 0; row < nY; row++) {
                    for (int column = 0; column < nX; column++) {
                        castRay(nX, nY, row, column);
                    }
                }
                return imageWriter;

            }
            //if using multi threads
            Pixel.initialize(nY, nX, 1);
            while (threads-- > 0) {
                new Thread(() ->
                {
                    for (Pixel pixel = new Pixel();
                         pixel.nextPixel();
                         Pixel.pixelDone()) {
                        imageWriter.writePixel(pixel.col, pixel.row, castRay(nX, nY, pixel.row, pixel.col));
                    }
                }).start();
            }
            Pixel.waitToFinish();
            return imageWriter;
        }
        //if one of the resources was not set
        catch (MissingResourceException e) {
            throw new UnsupportedOperationException("renderImage - value not set yet" + e.getKey());
        }
    }
    /**
     * Casts a ray from the camera through a pixel in the image, and writes the color of the intersection point to the
     * corresponding pixel in the image.
     *
     * @param nX     the number of pixels in the x-direction of the image
     * @param nY     the number of pixels in the y-direction of the image
     * @param column the column number of the pixel to cast the ray through
     * @param row    the row number of the pixel to cast the ray through
     * @throws MissingResourceException if the imageWriter or rayTracerBase have not been set
     */
    private Color castRay(int nX, int nY, int column, int row) {

        //create the ray
        Ray ray = constructRay2(nX, nY, row, column);
        //calculates the color of pixel in ray using traceRay method from Class TraceRay
        Color pixelColor = rayTracer.traceRay(ray);
        //writes the color of the pixel to image
        imageWriter.writePixel(row, column, pixelColor);
        return pixelColor;
    }

    /**
     * Renders the image
     *
     * @throws MissingResourceException if not all fields are initialized
     */
    public Camera renderImage2() throws MissingResourceException {
        if (this.position == null ||
            this.vTo == null ||
            this.vUp == null ||
            this.vRight == null ||
            this.height <= 0 ||
            this.width <= 0 ||
            this.distance <= 0 ||
            this.imageWriter == null ||
            this.rayTracer == null)
            throw new MissingResourceException("Missing resources", "Camera", "");

        int nx = this.imageWriter.getNx();
        int ny = this.imageWriter.getNy();

        IntStream.range(0, ny).parallel().forEach(i -> {
            IntStream.range(0, nx).parallel().forEach(j -> {
                List<Ray> rays = constructRay(nx, ny, j, i);
                Color color = Color.BLACK;
                for (Ray ray : rays)
                    color = color.add(this.rayTracer.traceRay(ray));

                this.imageWriter.writePixel(j, i, color.reduce(rays.size()));
            });
        });

        return this;
    }

    /**
     * Draws the grid into the image with the provided color
     *
     * @param interval grid slot size
     * @param color color
     * @throws MissingResourceException if the Image Writer field is uninitialized
     */
    public void printGrid(int interval, Color color) throws MissingResourceException {
        if (this.imageWriter == null)
            throw new MissingResourceException("Missing ImageWriter", "Camera", "");

        int nx = this.imageWriter.getNx();
        int ny = this.imageWriter.getNy();

        for (int i = 0; i < ny; i++) {
            for (int j = 0; j < nx; j++) {
                if (i % interval == 0 || j % interval == 0)
                    this.imageWriter.writePixel(i, j, color);
            }
        }
    }

    /**
     * Writes the image to a file
     *
     * @throws MissingResourceException if the Image Writer field is uninitialized
     */
    public void writeToImage() throws MissingResourceException {
        if (this.imageWriter == null)
            throw new MissingResourceException("Missing ImageWriter", "Camera", "");

        this.imageWriter.writeToImage();
    }

    /**
     * Moves the camera by a Vector
     *
     * @param moveDirection the movement vector
     * @return the updated Camera object
     */
    public Camera moveCamera(Vector moveDirection) {
        this.position = this.position.add(moveDirection);
        return this;
    }

    /**
     * Moves the camera to another Point
     *
     * @param newPosition new position point
     * @return the updated Camera object
     */
    public Camera moveCamera(Point newPosition) {
        this.position = newPosition;
        return this;
    }

    /**
     * Rotates the camera on the X axis
     *
     * @param angle rotation angle in degrees
     * @return the updated Camera object
     */
    public Camera rotateX(double angle) {
        this.vUp = this.vUp.rotateX(angle);
        this.vRight = this.vRight.rotateX(angle);
        return this;
    }

    /**
     * Rotates the camera on the Y axis
     *
     * @param angle rotation angle in degrees
     * @return the updated Camera object
     */
    public Camera rotateY(double angle) {
        this.vTo = this.vTo.rotateY(angle);
        this.vUp = this.vUp.rotateY(angle);
        return this;
    }

    /**
     * Rotates the camera on the Z axis
     *
     * @param angle rotation angle in degrees
     * @return the updated Camera object
     */
    public Camera rotateZ(double angle) {
        this.vTo = this.vTo.rotateZ(angle);
        this.vRight = this.vRight.rotateZ(angle);
        return this;
    }
    /**
     * set function for thread - builder design pattern
     *
     * @param threads sent threads to set
     * @return this camera that function was called from
     */
    public Camera setThreads(int threads) {
        this.threads = threads;
        return this;
    }
}
