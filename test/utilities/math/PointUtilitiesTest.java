package utilities.math;

import static org.junit.Assert.*;
import org.junit.Test;

import utilities.math.MathUtilities;

class PointUtilities {
    @Test
    void doubleLessThanTest() {
        double dub1 = 0.0;
        double dub2 = 0.0;

        assertFalse(MathUtilities.doubleLessThan(dub1, dub2));
        assertFalse(MathUtilities.doubleLessThan(dub2, dub1));

        assertFalse(MathUtilities.doubleLessThan(dub1, dub2 - 0.01));
        assertTrue(MathUtilities.doubleLessThan(dub1, dub2 + 0.01));

        assertFalse(MathUtilities.doubleLessThan(dub1, dub2 - 0.0000000001));
        assertFalse(MathUtilities.doubleLessThan(dub1, dub2 + 0.0000000001));

        assertFalse(MathUtilities.doubleLessThan(dub1, dub2 - 0.000001));
        assertTrue(MathUtilities.doubleLessThan(dub1, dub2 + 0.000001));
    }

    @Test
    void doubleGreaterThanTest() {
        double dub1 = 0.0;
        double dub2 = 0.0;

        assertFalse(MathUtilities.doubleGreaterThan(dub1, dub2));
        assertFalse(MathUtilities.doubleGreaterThan(dub2, dub1));

        assertTrue(MathUtilities.doubleGreaterThan(dub1, dub2 - 0.01));
        assertFalse(MathUtilities.doubleGreaterThan(dub1, dub2 + 0.01));

        assertFalse(MathUtilities.doubleGreaterThan(dub1, dub2 - 0.0000000001));
        assertFalse(MathUtilities.doubleGreaterThan(dub1, dub2 + 0.0000000001));

        assertTrue(MathUtilities.doubleGreaterThan(dub1, dub2 - 0.000001));
        assertFalse(MathUtilities.doubleGreaterThan(dub1, dub2 + 0.000001));
    }
}
