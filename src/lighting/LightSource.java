package lighting;

import primitives.*;

public interface LightSource {

    /**
     * Get the intensity of the light at a point
     *
     * @param p origin of the light
     * @return the intensity
     */
    public Color getIntensity(Point p);

    /**
     * Get the direction of the light from a point
     *
     * @param p the point
     * @return the direction
     */
    public Vector getL(Point p);
    
    default LightSource setSharpness(double sharpness) {
        return this;
    }
    /**
	 * Calculates the distance from the current point to the given point.
	 *
	 * @param  p  the point to calculate the distance to
	 * @return    the distance from the current point to the given point
	 */
	double getDistance(Point p);
}