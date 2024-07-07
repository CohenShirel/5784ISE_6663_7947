package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * This class is an abstract class of a ray tracer.
 */
public abstract class RayTracerBase {
    protected Scene scene;

    /**
     * Constructor for RayTracerBase
     *
     * @param scene the scene
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * @param ray the ray to trace
     * @return color of the point at the intersection finded
     */
    public abstract Color traceRay(Ray ray);
}