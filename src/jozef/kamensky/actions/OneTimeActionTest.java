package jozef.kamensky.actions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OneTimeActionTest {

    private static final String ACTION_VIEW_ID = "aw1";

    @Test
    public void shouldNotBeCompletedPrematurely() {
        var a = new OneTimeAction(ACTION_VIEW_ID, 3);
        assertFalse(a.isCompleted());
    }

    @Test
    public void shouldBeCompletedOnTime() {
        var a = new OneTimeAction(ACTION_VIEW_ID, 1);
        assertTrue(a.isCompleted());
    }

    @Test
    public void shouldReturnEmptyFollowUpActions() {
        var a = new OneTimeAction(ACTION_VIEW_ID, 3);
        assertNotNull(a.getFollowUpActions());
        assertEquals(0, a.getFollowUpActions().size());
    }

}