package jozef.kamensky.actions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RepeatedActionTest {

    private static final String ACTION_VIEW_ID = "aw1";
    private static final int DURATION = 3;

    @Test
    public void shouldReturnDeepCopyOfItselfAsFollowUpActions() {
        var a = new RepeatedAction(ACTION_VIEW_ID, DURATION);
        var followUpActions = a.getFollowUpActions();
        assertNotNull(followUpActions);
        assertEquals(1, followUpActions.size());
        var followUpAction = followUpActions.get(0);
        assertEquals(ACTION_VIEW_ID, followUpAction.getActionViewId());
        assertEquals(DURATION, followUpAction.getDuration());
    }

}