package geometries;

import java.util.List;

import primitives.*;
import static primitives.Util.*;

//======= Plane Class ========//

/**
 * A plane is a flat surface that extends infinitely in all directions
 */

public class Plane implements Geometry {
    final Point _q0; // a random point on the plane
    final Vector _normal; // a normal vector to the plane

    // Creating a plane from a point and a vector.
    public Plane(Point q0, Vector vector) {
        _q0 = q0;
        _normal = vector.normalize();
    }

    // Creating a plane from three points.
    public Plane(Point p1, Point p2, Point p3) {

        _q0 = p1;
        Vector U = p2.subtract(p1);
        Vector V = p3.subtract(p1);
        Vector W = U.crossProduct(V);

        _normal = W.normalize();
    }

    //Getters
    public Point getQ0() {
        return _q0;
    }

    /**
     * getter for the normal vector of the plane
     *
     * @return The normal vector of the plane.
     */
    public Vector getNormal() {
        return _normal;
    }


    @Override
    public String toString() {
        return "Plane{" +
                "_q0=" + _q0 +
                ", _normal=" + _normal +
                '}';
    }

    /**
     * overriding getNormal of Geometry
     *
     * @param point referred point to the normal
     * @return normal to the Geometry
     */
    @Override
    public Vector getNormal(Point point) {
        return getNormal();
    }

    @Override
    public List<Point> findIntersections(Ray ray) {

        Point P0= ray.getP0(); 
        Vector v = ray.getDir(); 

        //בדיקה אם נקודת המוצא של הקרן היא על המישור
        if(_q0.equals(P0)){ 
            return null; 
        }

        Vector n = _normal; 
        double nv = n.dotProduct(v);//מכפלה סקלרית
        // בדיקה אם הקרן מקבילה למישור
        if (isZero(nv)){ 
            return null;
        }

        Vector Q0_P0 = _q0.subtract(P0);//וקטור מנקודת המוצא של הקרן לנקודה על המישור
        double nP0Q0= alignZero(n.dotProduct(Q0_P0));//המכפלה הסקלרית של הנורמל עם וקטור זה

        // בדיקה אם נקודת המוצא של הקרן נמצאת על המישור
        if(isZero(nP0Q0)){
            return null;
        }
        //בדיקה שנקודת החיתוך נמצאת בכיוון החיובי 
        //והקרן חוצה את המישור
        double t =alignZero(nP0Q0 / nv);
        if(t<=0){
            return null;
        }

        return List.of(ray.getPoint(t));
    }
    //האפשרויות הקיימות:
    //הקרן מקבילה למישור,
    //הקרן נמצאת על המישור,
    //הקרן מתחילה על המישור ומתקדמת על המישור,
    //והקרן חוצה את המישור בנקודה אחת
}
