package jozef.kamensky.resources;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NormalResourceTest {
    
    @Test
    public void constructor_shouldClipToMin() {
        var r = ResourceTestsUtils.createNormalResource( -10, 100);
        assertEquals(0, r.getAmount());
    }

    @Test
    public void constructor_shouldClipToMax() {
        var r = ResourceTestsUtils.createNormalResource(200, 100);
        assertEquals(100, r.getAmount());
    }

    @Test
    public void setAmount_shouldSetValue() {
        var r =ResourceTestsUtils.createNormalResource( 0, 100);
        assertEquals(0, r.getAmount());
        r.setAmount(20);
        assertEquals(20, r.getAmount());
    }

    @Test
    public void setAmount_shouldClipToMin() {
        var r =ResourceTestsUtils.createNormalResource( 0, 100);
        assertEquals(0, r.getAmount());
        r.setAmount(-10);
        assertEquals(0, r.getAmount());
    }

    @Test
    public void setAmount_shouldClipToMax() {
        var r =ResourceTestsUtils.createNormalResource( 10, 100);
        assertEquals(10, r.getAmount());
        r.setAmount(200);
        assertEquals(100, r.getAmount());
    }

    @Test
    public void addAmount_shouldAdd() {
        var r =ResourceTestsUtils.createNormalResource( 50, 100);
        assertEquals(50, r.getAmount());
        r.addAmount(20);
        assertEquals(70, r.getAmount());
    }

    @Test
    public void addAmount_shouldSubtract() {
        var r =ResourceTestsUtils.createNormalResource( 50, 100);
        assertEquals(50, r.getAmount());
        r.addAmount(-20);
        assertEquals(30, r.getAmount());
    }

    @Test
    public void addAmount_shouldClipToMax() {
        var r =ResourceTestsUtils.createNormalResource( 50, 100);
        assertEquals(50, r.getAmount());
        r.addAmount(200);
        assertEquals(100, r.getAmount());
    }

    @Test
    public void addAmount_shouldClipToMin() {
        var r =ResourceTestsUtils.createNormalResource( 50, 100);
        assertEquals(50, r.getAmount());
        r.addAmount(-200);
        assertEquals(0, r.getAmount());
    }
}