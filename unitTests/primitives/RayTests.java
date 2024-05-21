package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Ray class
 */
class RayTests {

    // ============ Equivalence Partitions Tests ==============

    /**
     * Test method for {@link primitives.Ray#getPoint(double)}.
     */
    @Test
    void testGetPoint() {
    	//היה צריך לקבל את הנקודת חיתוך....ולא צלח
        // TC01: בדיקה עבור מרחק חיובי
        Ray ray1 = new Ray(new Point(1, 1, 1), new Vector(1, 0, 0));
        assertEquals(new Point(2, 1, 1), ray1.getPoint(1), "ERROR: getPoint() for positive distance does not work correctly");

        // TC02: בדיקה עבור מרחק שלילי
        try {
            ray1.getPoint(-1);
            fail("ERROR: getPoint() for negative distance does not throw an exception");
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }

        // TC03: בדיקה עבור מרחק שווה לאפס
        try {
            ray1.getPoint(0);
            fail("ERROR: getPoint() for distance of 0 does not throw an exception");
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }
}

