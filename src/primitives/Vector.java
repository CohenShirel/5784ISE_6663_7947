package primitives;

//Create a class for representation Vector
public class Vector extends Point {
	 // Adding a constant for the unit vector in the Y direction
	  public static final Vector Y = new Vector(0,1,0);
	  public static final Vector X = new Vector(1,0,0);
	  public static final Vector Z = new Vector(0,0,1);
 // Creating a constructor for the class Vector.
 public Vector(Double3 xyz) {
     super(xyz);

     //Check if the coordinates create ZERO vector.
     if (_xyz.equals(Double3.ZERO)) {
         throw new IllegalArgumentException("ZERO vector not allowed");
     }
 }

 // Creating a constructor for the class Vector.
 public Vector(double x, double y, double z) {
     super(x, y, z);
     if (_xyz.equals(Double3.ZERO)) {
         throw new IllegalArgumentException("ZERO vector is not allowed");
     }
 }

 /**
  * Add the vector to this vector and return the result
  *
  * @param vector The vector to add to this vector.
  * @return A new Vector object.
  */
 public Vector add(Vector vector) {
     return new Vector(_xyz.add(vector._xyz));
 }

 /**
  * This function returns a new vector that is the result of multiplying the current vector by a scalar
  *
  * @param scalar the scalar to multiply the vector by
  * @return A new Vector object.
  */
 public Vector scale(double scalar) {
     return new Vector(_xyz.scale(scalar));
 }

 // Calculating the dot product of the current vector and the vector passed as a parameter.
 public double dotProduct(Vector vector) {

     double u1 = _xyz._d1;
     double u2 = _xyz._d2;
     double u3 = _xyz._d3;

     double v1 = vector._xyz._d1;
     double v2 = vector._xyz._d2;
     double v3 = vector._xyz._d3;

     return (u1 * v1 + u2 * v2 + u3 * v3);
 }

 // Creating a new vector that is the result of the cross product of the current vector and the vector passed as a
 // parameter.
 public Vector crossProduct(Vector vector) {

     double u1 = _xyz._d1;
     double u2 = _xyz._d2;
     double u3 = _xyz._d3;

     double v1 = vector._xyz._d1;
     double v2 = vector._xyz._d2;
     double v3 = vector._xyz._d3;

     return new Vector((u2 * v3 - v2 * u3), -(u1 * v3 - v1 * u3), (u1 * v2 - v1 * u2));
 }

 // Calculating the length of the vector squared.
 public double lengthSquared() {
     double u1 = _xyz._d1;
     double u2 = _xyz._d2;
     double u3 = _xyz._d3;

     return u1 * u1 + u2 * u2 + u3 * u3;
 }

 // Calculating the length of the vector.
 public double length() {
     return Math.sqrt(lengthSquared());
 }

 // Creating a new vector that is the result of the normalization of the current vector.
 public Vector normalize() {
     double len = length();
     return new Vector(_xyz.reduce(len));
 }
 /**
  * Rotates this Vector on the X axis
  *
  * @param angle angle of rotation
  * @return the rotated vector
  */
 public Vector rotateX(double angle) {
     double sin = Math.sin(Math.toRadians(angle));
     double cos = Math.cos(Math.toRadians(angle));

     double x = this._xyz._d1;
     double y = cos * this._xyz._d2 - sin * this._xyz._d3;
     double z = sin * this._xyz._d2 + cos * this._xyz._d3;

     return new Vector(x, y, z);
 }

 /**
  * Rotates this Vector on the Y axis
  *
  * @param angle angle of rotation
  * @return the rotated vector
  */
 public Vector rotateY(double angle) {
     double sin = Math.sin(Math.toRadians(angle));
     double cos = Math.cos(Math.toRadians(angle));

     double x = cos * this._xyz._d1 + sin * this._xyz._d3;
     double y = this._xyz._d2;
     double z = -sin * this._xyz._d1 + cos * this._xyz._d3;

     return new Vector(x, y, z);
 }

 /**
  * Rotates this Vector on the Z axis
  *
  * @param angle angle of rotation
  * @return the rotated vector
  */
 public Vector rotateZ(double angle) {
     double sin = Math.sin(Math.toRadians(angle));
     double cos = Math.cos(Math.toRadians(angle));

     double x = cos * this._xyz._d1 - sin * this._xyz._d2;
     double y = sin * this._xyz._d1 + cos * this._xyz._d2;
     double z = this._xyz._d3;

     return new Vector(x, y, z);
 }

 @Override
 public boolean equals(Object o) {
     return super.equals(o);
 }

 @Override
 public int hashCode() {
     return super.hashCode();
 }

 @Override
 public String toString() {
     return "Vector" + _xyz;
 }
}