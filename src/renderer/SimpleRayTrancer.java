package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.List;

public class SimpleRayTrancer extends RayTracerBase {
    public SimpleRayTrancer(Scene scene) {
        super(scene);
    }

    /**
     * Get color of the intersection of the ray with the scene
     * @param ray Ray to trace
     * @return Color of intersection
     */
    @Override
    public Color traceRay(Ray ray) {
        List<Point> intersections = this.scene.geometries.findIntersections(ray);

        if (intersections == null)
            return this.scene.background;

        Point closestPoint = ray.findClosestPoint(intersections);

        return calcColor(closestPoint);
    }

    /**
     * Get the color of an intersection point
     * @param point point of intersection
     * @return Color of the intersection point
     */
    private Color calcColor(Point point) {
        return this.scene.ambientLight.getIntensity();
    }
}

