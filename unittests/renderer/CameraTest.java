package renderer;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

import renderer.Camera;
import primitives.*;

/**
 * Testing Camera Class
 *
 * @author Dan
 *
 */
class CameraTest {
    static final Point ZERO_POINT = new Point(0, 0, 0);

    /**
	 * Test method for
	 * {@link renderer.Camera(primitives.Point, primitives.Vector, primitives.Vector)}
	 */
	@Test
	void testConstructor() {
		Vector v = new Vector(1, 0, 0);

		// =============== Boundary Values Tests ==================
		// BVA01: vTo and vUp are not orthogonal
		assertThrows(
				IllegalArgumentException.class,
				() -> new Camera(ZERO_POINT, v, new Vector(1, 1, 0)),
				"BVA01: ctor() does not throw an exception when vTo and vUp are not orthogonal"
		);

		// BVA02: vTo and vUp are the same vector
		assertThrows(
				IllegalArgumentException.class,
				() -> new Camera(ZERO_POINT, v, v),
				"BVA02: ctor() does not throw an exception when vTo and vUp are the same vector"
		);
	}
    /**
     * Test method for
     * {@link Camera#constructRay(int, int, int, int)}.
     */
    @Test
    void testConstructRay() {
        Camera camera = new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setVPDistance(10);
        String badRay = "Bad ray";

        // ============ Equivalence Partitions Tests ==============
        // EP01: 4X4 Inside (1,1)
        assertEquals(new Ray(ZERO_POINT, new Vector(1, -1, -10)),
                camera.setVPSize(8, 8)
                        .constructRay(4, 4, 1, 1), badRay);

        // =============== Boundary Values Tests ==================
        // BV01: 3X3 Center (1,1)
        assertEquals(new Ray(ZERO_POINT, new Vector(0, 0, -10)),
                camera.setVPSize(6, 6)
                        .constructRay(3, 3, 1, 1), badRay);

        // BV02: 3X3 Center of Upper Side (0,1)
        assertEquals(new Ray(ZERO_POINT, new Vector(0, -2, -10)),
                camera.setVPSize(6, 6)
                        .constructRay(3, 3, 1, 0), badRay);

        // BV03: 3X3 Center of Left Side (1,0)
        assertEquals(new Ray(ZERO_POINT, new Vector(2, 0, -10)),
                camera.setVPSize(6, 6)
                        .constructRay(3, 3, 0, 1), badRay);

        // BV04: 3X3 Corner (0,0)
        assertEquals(new Ray(ZERO_POINT, new Vector(2, -2, -10)),
                camera.setVPSize(6, 6)
                        .constructRay(3, 3, 0, 0), badRay);

        // BV05: 4X4 Corner (0,0)
        assertEquals(new Ray(ZERO_POINT, new Vector(3, -3, -10)),
                camera.setVPSize(8, 8)
                        .constructRay(4, 4, 0, 0), badRay);

        // BV06: 4X4 Side (0,1)
        assertEquals(new Ray(ZERO_POINT, new Vector(1, -3, -10)),
                camera.setVPSize(8, 8)
                        .constructRay(4, 4, 1, 0), badRay);

    }
}