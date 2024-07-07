package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import static java.lang.Math.max;
import static primitives.Util.isZero;

/**
 * SpotLight class represents a light source in the scene.
 */
public class SpotLight extends PointLight{
	/**
	 * @param intensity the intensity color
	 */
	private Vector direction;

	private double narrowBeam = 1d;

	/**
	 * Constructor of the class
	 * @param intensity the intensity color
	 * @param position the position
	 * @param direction the direction
	 */
	public SpotLight(Color intensity, Point position, Vector direction) {
		super(intensity, position);
		this.direction = direction.normalize();
	}
	/**
	 * Set the narrow beam value.
	 *
	 * @param  i  the value to set for the narrow beam
	 * @return    the updated LightSource object
	 */
	public SpotLight setNarrowBeam(int i) {
		this.narrowBeam = i;
		return this;
	}

	/**
	 * A method to retrieve the intensity color.
	 *
	 * @param p the point at which to calculate the intensity
	 * @return the intensity color
	 */
	@Override
	public Color getIntensity(Point p) {
		double projection = this.direction.dotProduct(getL(p));
		if (isZero(projection)) {
			return Color.BLACK;
		}
		double factor = max(0, projection);
		factor = Math.pow(factor, narrowBeam);

		return super.getIntensity(p).scale(factor);
	}

	/**
	 * Calculates the distance from the current point to the given point.
	 *
	 * @param p the point to calculate the distance to
	 * @return the distance from the current point to the given point
	 */
	@Override
	public double getDistance(Point p) {
		return super.getDistance(p);
	}
}