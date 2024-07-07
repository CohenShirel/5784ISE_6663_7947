package geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import geometries.Polygon;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Testing Polygons
 * @author Dan
 */
public class PolygonTests {
   /**
    * Delta value for accuracy when comparing the numbers of type 'double' in
    * assertEquals
    */
   private final double DELTA = 0.000001;

   /** Test method for {@link Polygon#Polygon(Point...)}. */
   @Test
   public void testConstructor() {
      // ============ Equivalence Partitions Tests ==============

      // TC01: Correct concave quadrangular with vertices in correct order
      assertDoesNotThrow(() -> new Polygon(new Point(0, 0, 1),
                                           new Point(1, 0, 0),
                                           new Point(0, 1, 0),
                                           new Point(-1, 1, 1)),
                         "Failed constructing a correct polygon");

      // TC02: Wrong vertices order
      assertThrows(IllegalArgumentException.class, //
                   () -> new Polygon(new Point(0, 0, 1), new Point(0, 1, 0), new Point(1, 0, 0), new Point(-1, 1, 1)), //
                   "Constructed a polygon with wrong order of vertices");

      // TC03: Not in the same plane
      assertThrows(IllegalArgumentException.class, //
                   () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 2, 2)), //
                   "Constructed a polygon with vertices that are not in the same plane");

      // TC04: Concave quadrangular
      assertThrows(IllegalArgumentException.class, //
                   () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                                     new Point(0.5, 0.25, 0.5)), //
                   "Constructed a concave polygon");

      // =============== Boundary Values Tests ==================

      // TC10: Vertex on a side of a quadrangular
      assertThrows(IllegalArgumentException.class, //
                   () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                                     new Point(0, 0.5, 0.5)),
                   "Constructed a polygon with vertix on a side");

      // TC11: Last point = first point
      assertThrows(IllegalArgumentException.class, //
                   () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1)),
                   "Constructed a polygon with vertice on a side");

      // TC12: Co-located points
      assertThrows(IllegalArgumentException.class, //
                   () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 1, 0)),
                   "Constructed a polygon with vertice on a side");

   }

   /** Test method for {@link Polygon#getNormal(Point)}. */
   @Test
   public void testGetNormal() {
      // ============ Equivalence Partitions Tests ==============
      // TC01: There is a simple single test here - using a quad
      Point[] pts =
         { new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1) };
      Polygon pol = new Polygon(pts);
      // ensure there are no exceptions
      assertDoesNotThrow(() -> pol.getNormal(new Point(0, 0, 1)), "");
      // generate the test result
      Vector result = pol.getNormal(new Point(0, 0, 1));
      // ensure |result| = 1
      assertEquals(1, result.length(), DELTA, "Polygon's normal is not a unit vector");
      // ensure the result is orthogonal to all the edges
      for (int i = 0; i < 3; ++i)
         assertEquals(0d, result.dotProduct(pts[i].subtract(pts[i == 0 ? 3 : i - 1])), DELTA,
                      "Polygon's normal is not orthogonal to one of the edges");
   }
   @Test
   void findIntersections() {
      Polygon polygon = new Polygon(
              new Point(1, 0, 0),
              new Point(0, -1, 0),
              new Point(1, -2, 0),
              new Point(2, -1, 0));
      // =============== Equivalence Partitions Tests ===============
      // TC01: Test with a ray that intersects the polygon at a single point
      assertEquals(List.of(new Point(1,-1.5,0)),polygon.findIntersections(new Ray(new Point(0,0,1.5),new Vector(1,-1.5,-1.5))), "ERROR: findIntersections does not return the correct point");
      // TC02: Test with a ray that does not intersect the polygon
      assertNull(polygon.findIntersections(new Ray(new Point(0,0,1),new Vector(-1,-1,-1))), "ERROR: findIntersections does not return null when there is no intersection");
      // TC03: Test with a ray that starts inside the polygon
      assertNull(polygon.findIntersections(new Ray(new Point(1,-1,0),new Vector(-1,1,1))), "ERROR: findIntersections does not return null when there is no intersection");
      // TC04: Test with a ray that starts outside the polygon
      assertNull(polygon.findIntersections(new Ray(new Point(-1,1 ,1), new Vector(-1,1,1))), "ERROR: findIntersections does not return null when the ray starts outside the polygon");

      //================ Boundary Value Analysis ===================
      // TC11: Test with a ray that intersects the polygon at an edge from the outer side
      assertNull(polygon.findIntersections(new Ray(new Point(0,0,1),new Vector(0.5,-0.5,-1))), "ERROR: findIntersections does not return null when the ray starts outside the polygon");
      // TC12: Test with a ray that intersects the polygon at a vertex from the outer side
      assertNull(polygon.findIntersections(new Ray(new Point(0,0,1),new Vector(0,-1,-1))), "ERROR: findIntersections does not return null when the ray starts outside the polygon");
      // TC13: Test with a ray that intersects the polygon at a vertex from the inner side
      assertNull(polygon.findIntersections(new Ray(new Point(1,-1,0),new Vector(-1,0,0))), "ERROR: findIntersections does not return null when the ray starts inside the polygon");
      // TC14: Test with a ray that intersects the polygon at an edge from the inner side
      assertNull(polygon.findIntersections(new Ray(new Point(1,-1,0),new Vector(0.5,-0.5,-1))), "ERROR: findIntersections does not return null when the ray starts inside the polygon");

      //Test with distance

      List<Point> result02 = polygon.findIntersections(new Ray(new Point(0,0,1.5),new Vector(1,-1.5,-1.5)),1000);
      //TC21: Test all the geometries are intersecting with max distance
      assertEquals(1,result02.size(),"ERROR: some of the geometries are not intersecting");

      List<Point> result04 = polygon.findIntersections(new Ray(new Point(0,0,30),new Vector(1,-1.5,-1.5)),0.1);
      //TC21: Test none of the geometries are intersecting because of the max distance
      assertNull(result04,"ERROR: the geometries aren't intersecting because of the max distance");
   }
}