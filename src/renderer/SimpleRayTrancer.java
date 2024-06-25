package renderer;

import geometries.Intersectable;
import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;
import static primitives.Util.alignZero;

import java.util.List;

public class SimpleRayTrancer extends RayTracerBase {
    public SimpleRayTrancer(Scene scene) {
        super(scene);
    }

    /**
     * Calculates the color of a point in the scene.
     *
     * @param geoPoint The point on the geometry in the scene.
     * @param ray The ray from the camera to the intersection.
     * @return The color of the point.
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        return scene.ambientLight.getIntensity().add(geoPoint.geometry.getEmission())
                .add(calcLocalEffects(geoPoint, ray));
    }

    /**
     * Get color of the intersection of the ray with the scene
     * @param ray Ray to trace
     * @return Color of intersection
     */
    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersections = this.scene.geometries.findGeoIntersections(ray);
        if (intersections == null)
            return this.scene.background;

        GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
        return calcColor(closestPoint, ray);
    }

    /**
     * Calculates the effect of different light sources on a point in the scene
     * according to the Phong model.
     *
     * @param intersection The point on the geometry in the scene.
     * @param ray The ray from the camera to the intersection.
     * @return The color of the point affected by local light sources.
     */
    private Color calcLocalEffects(GeoPoint intersection, Ray ray) {
        Vector v = ray.getDir();
        Vector n = intersection.geometry.getNormal(intersection.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0)
            return Color.BLACK;

        int nShininess = intersection.geometry.getMaterial().nShininess;
        Double3 kd = intersection.geometry.getMaterial().kD;
        Double3 ks = intersection.geometry.getMaterial().kS;

        Color color = Color.BLACK;
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(intersection.point);
            double nl = alignZero(n.dotProduct(l));

            if (nl * nv > 0) { // sign(nl) == sign(nv)
                Color lightIntensity = lightSource.getIntensity(intersection.point);
                color = color.add(calcDiffuse(kd, nl, lightIntensity),
                        calcSpecular(ks, l, n, nl, v, nShininess, lightIntensity));
            }
        }
        return color;
    }

    /**
     * Calculates the diffuse component of light reflection.
     *
     * @param kd The diffuse reflection coefficient.
     * @param nl The dot product between the normal vector and the light vector.
     * @param lightIntensity The intensity of the light source.
     * @return The color contribution from the diffuse reflection.
     */
    private Color calcDiffuse(Double3 kd, double nl, Color lightIntensity) {
        return lightIntensity.scale(kd.scale(Math.abs(nl)));
    }

    /**
     * Calculates the specular component of light reflection.
     *
     * @param ks The specular reflection coefficient.
     * @param l The light vector.
     * @param n The normal vector.
     * @param nl The dot product between the normal vector and the light vector.
     * @param v The view vector.
     * @param nShininess The shininess coefficient.
     * @param lightIntensity The intensity of the light source.
     * @return The color contribution from the specular reflection.
     */
    private Color calcSpecular(Double3 ks, Vector l, Vector n, double nl, Vector v, int nShininess,
                               Color lightIntensity) {
        Vector r = l.add(n.scale(-2 * nl)); // nl must not be zero!
        double minusVR = -alignZero(r.dotProduct(v));
        if (minusVR <= 0) {
            return Color.BLACK; // View from direction opposite to r vector
        }
        return lightIntensity.scale(ks.scale(Math.pow(minusVR, nShininess)));
    }
}

