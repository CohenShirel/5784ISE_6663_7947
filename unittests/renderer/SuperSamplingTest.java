package renderer;

import primitives.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link renderer.SuperSampling}.
 *
 * @author 
 */
class SuperSamplingTest {
    SuperSampling superSampling = new SuperSampling();

    /**
     * Test method for {@link renderer.SuperSampling#constructRaysThroughGrid(
     * double, double, primitives.Point, primitives.Point, primitives.Vector, primitives.Vector)}
     */
    @Test
    void testConstructRaysThroughGrid() {
        Point source = new Point(0,0,-1);
        Point gridCenter = new Point(0,0,1);
        Vector vUp = new Vector(0,1,0);
        Vector vRight = new Vector(-1,0,0);

        // EP01: The grid's length is even
        double width = 2;
        double height = 2;
        superSampling.setSize(2);

        List<Ray> expected1 = List.of (
                new Ray(new Point(0,0,-1), new Vector(0.5,-0.5,2)),
                new Ray(new Point(0,0,-1), new Vector(-0.5,-0.5,2)),
                new Ray(new Point(0,0,-1), new Vector(0.5,0.5,2)),
                new Ray(new Point(0,0,-1), new Vector(-0.5,0.5,2))
        );

        List<Ray> actual1 = superSampling.constructRaysThroughGrid(width, height, source, gridCenter, vUp, vRight);
        assertEquals(expected1,actual1, "EP01: Wrong rays");

        // EP02: The grid's length is odd
        width = 3;
        height = 3;
        superSampling.setSize(3);

        List<Ray> expected2 = List.of(
                new Ray(new Point(0,0,-1), new Vector(1,-1,2)),
                new Ray(new Point(0,0,-1), new Vector(0,-1,2)),
                new Ray(new Point(0,0,-1), new Vector(-1,-1,2)),
                new Ray(new Point(0,0,-1), new Vector(1,0,2)),
                new Ray(new Point(0,0,-1), new Vector(0,0,2)),
                new Ray(new Point(0,0,-1), new Vector(-1,0,2)),
                new Ray(new Point(0,0,-1), new Vector(1,1,2)),
                new Ray(new Point(0,0,-1), new Vector(0,1,2)),
                new Ray(new Point(0,0,-1), new Vector(-1,1,2))
        );

        List<Ray> actual2 = superSampling.constructRaysThroughGrid(width, height, source, gridCenter, vUp, vRight);
        assertEquals(expected2,actual2, "EP02: Wrong rays");
    }
}