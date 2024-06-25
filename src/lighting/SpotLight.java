package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;
import static primitives.Util.alignZero;

/**
 * The {@code SpotLight} class extends the {@code PointLight} class and represents a light source that has
 * a specific direction and position, emitting light with a specified intensity.
 * The light intensity decreases with distance from the light source according to the attenuation factors.
 * The direction vector is normalized upon initialization.
 * <p>This class includes methods to set the attenuation factors: kc (constant attenuation),
 * kl (linear attenuation), and kq (quadratic attenuation).</p>
 */
public class SpotLight extends PointLight {

    private final Vector direction;
    private double narrowBeam = 1;

    /**
     * Constructs a {@code SpotLight} with a specified direction, position, and intensity.
     *
     * @param direction the direction of the spotlight
     * @param position  the position of the spotlight
     * @param intensity the intensity of the light emitted by the spotlight
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    @Override
    public SpotLight setKc(Double kC) {
        return (SpotLight) super.setKc(kC);
    }

    @Override
    public SpotLight setKl(Double kL) {
        return (SpotLight) super.setKl(kL);
    }

    @Override
    public SpotLight setKq(Double kQ) {
        return (SpotLight) super.setKq(kQ);
    }

    /**
     * Get the intensity of the light at a given point.
     *
     * @param point the point at which to calculate the intensity
     * @return the intensity of the light at point
     */
    @Override
    public Color getIntensity(Point point) {
        double cos = alignZero(direction.dotProduct(getL(point)));
        return narrowBeam != 1
                ? super.getIntensity(point).scale(Math.pow(Math.max(0, direction.dotProduct(getL(point))), narrowBeam))
                : super.getIntensity(point).scale(Math.max(0, direction.dotProduct(getL(point))));
    }

    /**
     * Set the narrow beam factor.
     * The narrow beam factor adjusts the concentration of the light beam.
     *
     * @param narrowBeam the narrow beam factor
     * @return the SpotLight object
     */
    public SpotLight setNarrowBeam(double narrowBeam) {
        this.narrowBeam = narrowBeam;
        return this;
    }
}
