package renderer;

import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class SimpleRayTrancer extends RayTracerBase {
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final Double3 INITIAL_K = Double3.ONE;

    public SimpleRayTrancer(Scene scene) {
        super(scene);
    }

    /**
     * Finds the color by a given GeoPoint and uses recursion to calculate its global effects
     *
     * @param point geo point
     * @param ray ray
     * @return color
     */
    private Color calcColor(GeoPoint point, Ray ray, int level, Double3 k) {Color color = calcLocalEffects(point, ray, k);
        return level == 1 ? color : color.add(calcGlobalEffects(point, ray.getDir(), level, k));
    }

    /**
     * Finds the color by a given GeoPoint and uses recursion to calculate its global effects
     *
     * @param point geo point
     * @param ray ray
     * @return color
     */
    private Color calcColor(GeoPoint point, Ray ray) {
        return calcColor(point, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.ambientLight.getIntensity());
    }

    /**
     * Calculates the local effect (diffusive and specular) by a given GeoPoint
     *
     * @param point geo point
     * @param ray ray
     * @return color of the effect
     */
    private Color calcLocalEffects(GeoPoint point, Ray ray, Double3 k) {
        Vector v = ray.getDir();
        Vector n = point.geometry.getNormal(point.point);

        double nv = alignZero(n.dotProduct(v));
        if (isZero(nv))
            return Color.BLACK;

        Material material = point.geometry.getMaterial();
        int nShininess = material.nShininess;
        Double3 kd = material.kD;
        Double3 ks = material.kS;

        Color color = point.geometry.getEmission();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(point.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) {
                Double3 ktr = transparency(point, lightSource, l, n);

                if (!k.product(ktr).lowerThan(MIN_CALC_COLOR_K)) {
                    Color lightIntensity = lightSource.getIntensity(point.point).scale(ktr);
                    color = color.add(
                            calcDiffusive(kd, nl, lightIntensity),
                            calcSpecular(ks, nShininess, n, l, nl, v, lightIntensity)
                    );
                }
            }
        }
        return color;
    }

    /**
     * Calculates the additional global effect (reflection and transparency) by a given GeoPoint using recursion
     *
     * @param point geo point
     * @param dir ray directions
     * @param level level of additional rays
     * @param k initial color
     * @return additional color
     */
    private Color calcGlobalEffects(GeoPoint point, Vector dir, int level, Double3 k) {
        Color color = Color.BLACK;
        Material material = point.geometry.getMaterial();
        Double3 ktr = k.product(material.kR);
        Vector n = point.geometry.getNormal(point.point);

        if (!ktr.lowerThan(MIN_CALC_COLOR_K))
            color = calcGlobalEffects(
                    constructReflectedRay(point.point, dir, n),
                    level,
                    material.kR,
                    ktr
            );

        Double3 ktt = k.product(material.kT);
        if (!ktt.lowerThan(MIN_CALC_COLOR_K))
            color = color.add(calcGlobalEffects(
                    new Ray(point.point, dir, n),
                    level,
                    material.kT,
                    ktt
                    )
            );

        return color;
    }

    /**
     * Calculates the additional global effect (reflection and transparency) by a given GeoPoint using recursion
     *
     * @param ray ray
     * @param level level of additional rays
     * @param kx scalar const
     * @param kxx initial color
     * @return additional color
     */
    private Color calcGlobalEffects(Ray ray, int level, Double3 kx, Double3 kxx) {
        GeoPoint point = findClosestIntersection(ray);
        return (point != null ? calcColor(point, ray, level - 1, kxx) : scene.background).scale(kx);
    }

    /**
     * Calculates the diffusive effect
     *
     * @param kd kd
     * @param nl dot product of the normal of the geometry with the light direction
     * @param lightIntensity light intensity color
     * @return diffusive effect color
     */
    private Color calcDiffusive(Double3 kd, double nl, Color lightIntensity) {
        return lightIntensity.scale(kd.scale(Math.abs(nl)));
    }

    /**
     * Calculates the specular effect
     *
     * @param ks ks
     * @param nShininess shininess
     * @param n normal vector to the geometry
     * @param l light direction vector
     * @param nl dot product of the normal of the geometry with the light direction
     * @param v vector from the camera to the geo point
     * @param lightIntensity light intensity color
     * @return specular effect color
     */
    private Color calcSpecular(Double3 ks, int nShininess, Vector n, Vector l,
                               double nl, Vector v, Color lightIntensity) {
        Vector minusR = n.scale(2 * nl).subtract(l);
        double minusVR = minusR.dotProduct(v);
        if (minusVR <= 0)
            return Color.BLACK;
        return lightIntensity.scale(ks.scale(Math.pow(minusVR, nShininess)));
    }

    /**
     * Calculates the transparency of a point
     *
     * @param point geo point
     * @param lightSource light source
     * @param l light direction
     * @param n normal to the geometry
     * @return transparency
     */
    private Double3 transparency(GeoPoint point, LightSource lightSource, Vector l, Vector n) {
        Vector lightDirection = l.scale(-1);
        double lightDistance = lightSource.getDistance(point.point);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(
                new Ray(point.point, lightDirection, n),
                lightDistance
        );

        if (intersections == null)
            return Double3.ONE;

        Double3 ktr = Double3.ONE;
        for (GeoPoint p : intersections) {
            if (alignZero(p.point.distance(point.point) - lightDistance) <= 0) {
                ktr = p.geometry.getMaterial().kT.product(ktr);

                if (ktr.lowerThan(MIN_CALC_COLOR_K))
                    return Double3.ZERO;
            }
        }

        return ktr;
    }

    /**
     * Constructs a reflected ray
     *
     * @param point start point
     * @param dir ray direction
     * @param n the normal to the geometry
     * @return reflected ray
     */
    private Ray constructReflectedRay(Point point, Vector dir, Vector n) {
        double nv = n.dotProduct(dir);
        return new Ray(point, dir.subtract(n.scale(2 * nv)), n);
    }

    /**
     * Finds the closest geo point of intersection with the ray
     *
     * @param ray ray
     * @return geo point
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = this.scene.geometries.findGeoIntersections(ray);
        return intersections != null ? ray.findClosestGeoPoint(intersections) : null;
    }

    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closest = findClosestIntersection(ray);
        return closest != null ? calcColor(closest, ray) : scene.background;
    }
}
