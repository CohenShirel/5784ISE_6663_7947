package geometries;
import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry {
    final Point origin;
    final Vector normal;

 // Constructor that computes the normal from three points
    public Plane(Point point1, Point point2, Point point3) {
        this.origin = point1;
        Vector U = point2.subtract(point1);
        Vector V = point3.subtract(point1);
        Vector W = U.crossProduct(V);
        normal = W.normalize();
    }

    // Constructor that accepts a point and a normal vector
    public Plane(Point origin, Vector normalVector) {
        this.origin = origin;
        this.normal = normalVector.normalize();
    }

    @Override
    public String toString() {
        return "Plane{" +
                "origin=" + origin +
                ", normal=" + normal +
                '}';
    }

    @Override
    public Vector getNormal(Point point) {
        return null;
    }
    
    public Vector getNormal() {
        return normal;
    }
}

