package jozef.kamensky.resources;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RenewableResourceTest {

    @Test
    public void shouldSetToMaxAmount() {
        var r = ResourceTestsUtils.createRenewableResource(0, 20);
        assertEquals(0, r.getAmount());
        r.onTurnStart(null);
        assertEquals(20, r.getMaxAmount());
    }

}