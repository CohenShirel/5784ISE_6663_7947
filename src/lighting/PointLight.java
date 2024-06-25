package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * The {@code PointLight} class extends the {@code Light} class and represents a light source located at a
 * specific position in 3D space, emitting light with a specified intensity.
 * The light intensity decreases with distance from the light source according to the attenuation factors.
 * <p>This class includes methods to set the position and the attenuation factors: kc (constant attenuation),
 * kl (linear attenuation), and kq (quadratic attenuation).</p>
 */
public class PointLight extends Light implements LightSource {

    protected Point position;
    private double kC = 1;
    private double kL = 0;
    private double kQ = 0;

    /**
     * Constructs a {@code PointLight} with a specified position and intensity.
     *
     * @param position the position of the point light
     * @param intensity the intensity of the light emitted by the point light
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    /**
     * Sets the position of the point light.
     *
     * @param position the new position of the point light
     * @return the {@code PointLight} object itself for method chaining
     */
    public PointLight setPosition(Point position) {
        this.position = position;
        return this;
    }

    /**
     * Sets the constant attenuation factor.
     *
     * @param kC the constant attenuation factor
     * @return the {@code PointLight} object itself for method chaining
     */
    public PointLight setKc(Double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * Sets the linear attenuation factor.
     *
     * @param kL the linear attenuation factor
     * @return the {@code PointLight} object itself for method chaining
     */
    public PointLight setKl(Double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * Sets the quadratic attenuation factor.
     *
     * @param kQ the quadratic attenuation factor
     * @return the {@code PointLight} object itself for method chaining
     */
    public PointLight setKq(Double kQ) {
        this.kQ = kQ;
        return this;
    }

    @Override
    public Color getIntensity(Point p) {
        double d = position.distance(p);
        return getIntensity().scale(1d / (kC + kL * d + kQ * d * d));
    }

    @Override
    public Vector getL(Point p) {
        // if the point is the same as the light source, return null
        if (p.equals(position))
            return null;
        // otherwise, return the normalized vector from the light source to the point
        return p.subtract(position).normalize();
    }
}
