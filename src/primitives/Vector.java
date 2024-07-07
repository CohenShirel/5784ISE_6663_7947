package primitives;

import static java.lang.Math.sqrt;

/**
 * Class Vector is the basic class representing a vector of Euclidean geometry in Cartesian
 */
public final class Vector extends Point {
    public static final Vector Y = new Vector(0,1,0);
    public static final Vector X = new Vector(1,0,0);
    public static final Vector Z = new Vector(0,0,1);

    /**
     * Constructs a new Vector object with the given x, y, and z components.
     *
     * @param  x      the x component of the vector
     * @param  y      the y component of the vector
     * @param  z      the z component of the vector
     * @throws IllegalArgumentException if the vector is the zero vector
     */
    public Vector(double x, double y, double z) {
        super(x,y,z);
        if (Double3.ZERO.equals(xyz))
        { throw new IllegalArgumentException("the vector is the zero vector");}
    }

    /**
     * Constructs a new Vector object from a Double3 object.
     *
     * @param  xyz    the Double3 object representing the vector's x, y, and z components
     * @throws IllegalArgumentException if the vector is the zero vector
     */
    public Vector(Double3 xyz) {
        super(xyz);
        if (xyz.equals(Double3.ZERO))
        { throw new IllegalArgumentException("the vector is the zero vector");}

    }

    /**
     * Adds a vector to this vector and returns the result as a new vector.
     *
     * @param  v  the vector to be added
     * @return    a new vector that is the sum of this vector and the given vector
     */
    public Vector add (Vector v){
        return new Vector(super.add(v).xyz);
    }

    /**
     * Scales the vector by a given factor.
     *
     * @param d the scaling factor
     * @return a new vector that is the result of scaling the current vector
     */
    public Vector scale (double d){
        return new Vector(xyz.scale(d));
    }

    /**
     * Calculates the dot product between this vector and another vector.
     *
     * @param  v The vector to calculate the dot product with.
     * @return   The dot product of the two vectors.
     */
    public double dotProduct(Vector v){
        return xyz.d1 * v.xyz.d1
                + xyz.d2 * v.xyz.d2
                + xyz.d3 * v.xyz.d3;
    }

    /**
     * Calculate the cross product of this vector and the given vector.
     *
     * @param  v   the vector to calculate the cross product with
     * @return     a new vector representing the cross product
     */
    public Vector crossProduct(Vector v){
        return new Vector(
                xyz.d2 * v.xyz.d3 - xyz.d3 * v.xyz.d2,
                xyz.d3 * v.xyz.d1 - xyz.d1 * v.xyz.d3,
                xyz.d1 * v.xyz.d2 - xyz.d2 * v.xyz.d1);

    }

    /**
     * Calculates the length squared of the vector.
     *
     * @return the length squared of the vector
     */
    public double lengthSquared(){
        return new Vector(xyz).dotProduct(new Vector(xyz));
    }

    /**
     * Calculates the length of the vector.
     *
     * @return the length of the vector
     */
    public double length(){
        return sqrt(lengthSquared());
    }

    /**
     * Normalize the Vector.
     *
     * @return  a new Vector representing the normalized Vector.
     */
    public Vector normalize(){
        return new Vector(xyz.reduce(length()));
    }


    /**
     * Class Vector is the basic class representing a vector of Euclidean geometry in Cartesian
     * 3-Dimensional coordinate system.
     * @author Ariel and Yayir
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Vector other)) return false;
        return super.equals(other);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * Returns a string representation of the Java function.
     *
     * @return  a string representation of the Java function
     */
    @Override
    public String toString() { return "->" + super.toString(); }


}