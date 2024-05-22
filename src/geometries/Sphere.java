package geometries;
import java.util.ArrayList;
import java.util.Comparator;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;
import java.util.Objects;

public class Sphere implements Geometry{
    private final Point _center;
    private final double _radius;

    public Sphere(Point center, double radius) {
        _center = center;
        _radius = radius;
    }

    public Point getCenter() {
        return _center;
    }

    public double getRadius() {
        return _radius;
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "_center=" + _center +
                ", _radius=" + _radius +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sphere sphere = (Sphere) o;
        return Double.compare(sphere._radius, _radius) == 0 && _center.equals(sphere._center);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_center, _radius);
    }

    /**
     * Return the normal to the sphere in the receiving point
     * @param point Point on the sphere
     * @return Normal to the sphere in the receiving point (Vector)
     */
    @Override
    public Vector getNormal(Point point) {
        Vector v = point.subtract(_center);
        return v.normalize(); //Return normalize normal vector.
    }

    @Override
    //3 בדיקות לנק' חיתוך
    public List<Point> findIntersections(Ray ray) {

    	Point p0 = ray.getP0(); // נקודת המוצא של הקרן
    	Point O = _center; // נקודת מרכז הכדור
    	Vector V = ray.getDir(); // וקטור הכיוון של הקרן

       // נקודת המוצא של הקרן היא במרכז הכדור=נק' חיתוך אחת
        if (O.equals(p0)) {
            Point newPoint = p0.add(ray.getDir().scale(_radius));
            return List.of(newPoint);
        }
        //חישוב המרחק הקטן ביותר בין הקרן למרכז הכדור (d) ומשווה אותו לרדיוס הכדור.
        Vector U = O.subtract(p0);
        double tm = V.dotProduct(U);
        double d = Math.sqrt(U.lengthSquared() - tm * tm);
        //אם המרחק גדול מהרדיוס=>הקרן לא חותכת
        if (d >= _radius) {
            return null;
        }

        double th = Math.sqrt(_radius * _radius - d * d); // מחושב המרחק בין הנקודות שבהן הקרן חוצה את הכדור
        double t1 = tm - th; // המרחק מנקודת המוצא לנקודת החיתוך הראשונה
        double t2 = tm + th; // המרחק מנקודת המוצא לנקודת החיתוך השנייה

        List<Point> intersections = new ArrayList<>();
//אם שניהם חויוביות יש לי שני נקודות חיתוך
        if (t1 > 0 && t2 > 0) {
            Point p1 = ray.getPoint(t1);
            Point p2 = ray.getPoint(t2);
            intersections.add(p1);
            intersections.add(p2);
            // מיון הנקודות לפי המרחק מנקודת המוצא של הקרן
            intersections.sort(Comparator.comparingDouble(p -> p.distance(p0)));
            return intersections;
        }
//אחד חיובי=נק' חיתוך אחת חיובית
        if (t1 > 0) {
            Point p1 = ray.getPoint(t1);
            return List.of(p1);
        }

        if (t2 > 0) {
            Point p2 = ray.getPoint(t2);
            return List.of(p2);
        }
        //אף אחד לא חיובי=אין נק' חיתוך
        return null;
    }
}
