package fi.gemwars.gameobjects;

import static org.junit.Assert.assertEquals;

import java.awt.Point;

import org.junit.Test;

public class DirectionTest {

    @Test
    public void testScanDirection_Stationary_ShouldReturn_0_0() throws Exception {
        Point result = Direction.STATIONARY.scanDirection();
        assertEquals(0, result.x);
        assertEquals(0, result.y);
    }

    @Test
    public void testScanDirection_Left_ShouldReturn_n1_0() throws Exception {
        Point result = Direction.LEFT.scanDirection();
        assertEquals(-1, result.x);
        assertEquals(0, result.y);
    }

    @Test
    public void testScanDirection_Right_ShouldReturn_1_0() throws Exception {
        Point result = Direction.RIGHT.scanDirection();
        assertEquals(1, result.x);
        assertEquals(0, result.y);
    }

    @Test
    public void testScanDirection_Up_ShouldReturn_0_n1() throws Exception {
        Point result = Direction.UP.scanDirection();
        assertEquals(0, result.x);
        assertEquals(-1, result.y);
    }

    @Test
    public void testScanDirection_Down_ShouldReturn_0_1() throws Exception {
        Point result = Direction.DOWN.scanDirection();
        assertEquals(0, result.x);
        assertEquals(1, result.y);
    }

}