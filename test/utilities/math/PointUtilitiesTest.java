import org.junit.jupiter.api.Test;

import utilities.math.MathUtilities;

import static org.junit.jupiter.api.Assertions.*;

class PointUtilities {
    @Test
    void doubleLessThanTest() {
        double dub1 = 0.0;
        double dub2 = 0.0;

        assertFalse(MathUtilities.doubleLessThan(dub1, dub2));
    }

    @Test
    void doubleGreaterThanTest() {

    }
}
