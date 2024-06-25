package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * The AmbientLight class represents ambient light in a scene.
 * Ambient light is the overall light that is present everywhere
 * in a scene, illuminating all objects equally.
 */
public class AmbientLight extends Light {

    /** A predefined AmbientLight object representing no ambient light. */
    public static final AmbientLight NONE = new AmbientLight(Color.BLACK, 0);

    /**
     * Constructs an AmbientLight object with the specified intensity and coefficient.
     *
     * @param Ia the intensity of the ambient light
     * @param Ka the coefficient of the ambient light
     */
    public AmbientLight(Color Ia, Double3 Ka) {
        super(Ia.scale(Ka));
    }

    /**
     * Constructs an AmbientLight object with the specified intensity and coefficient.
     *
     * @param Ia the intensity of the ambient light
     * @param Ka the coefficient of the ambient light
     */
    public AmbientLight(Color Ia, double Ka) {
        super(Ia.scale(Ka));
    }
}
