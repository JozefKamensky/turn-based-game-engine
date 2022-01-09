package jozef.kamensky.actions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ActionsManagerTest {

    private final static String ID1 = "aw1";
    private final static int DURATION = 1;

    private ActionsManager actionsManager;

    @BeforeEach
    public void beforeEach() {
        actionsManager = new ActionsManager();
    }

    @Test
    public void newActionsManagerShouldNotHaveAnyActionViews() {
        assertTrue(actionsManager.getActionsMap().isEmpty());
    }

    @Test
    public void newActionsManagerShouldNotHaveAnyOngoingActions() {
        assertTrue(actionsManager.getOngoingActions().isEmpty());
    }

    private void prepareSimpleActionView(boolean isPeriodic) {
        var aw = new ActionView(ID1, "test", "test", null, Collections.emptyMap(), DURATION, isPeriodic);
        actionsManager.addNewAction(aw);
    }

    private void prepareSimpleActionView(Map<String, Integer> yield, boolean isPeriodic) {
        var aw = new ActionView(ID1, "test", "test", null, yield, DURATION, isPeriodic);
        actionsManager.addNewAction(aw);
    }

    @Test
    public void shouldAddNewActionView() {
        prepareSimpleActionView(false);
        assertEquals(1, actionsManager.getActionsMap().size());
    }

    @Test
    public void shouldStartNewOneTimeAction() {
        prepareSimpleActionView(false);
        actionsManager.startNewAction(ID1);
        assertEquals(1, actionsManager.getOngoingActions().size());
        assertTrue(actionsManager.getOngoingActions().get(0) instanceof OneTimeAction);
    }

    @Test
    public void shouldStartNewRepeatedAction() {
        prepareSimpleActionView(true);
        actionsManager.startNewAction(ID1);
        assertEquals(1, actionsManager.getOngoingActions().size());
        assertTrue(actionsManager.getOngoingActions().get(0) instanceof RepeatedAction);
    }

    @Test
    public void shouldNotStartOneTimeActionAfterCompletion() {
        prepareSimpleActionView(false);
        actionsManager.startNewAction(ID1);
        actionsManager.onTurnStart();
        assertTrue(actionsManager.getOngoingActions().isEmpty());
    }

    @Test
    public void shouldStartRepeatedActionAfterCompletion() {
        prepareSimpleActionView(true);
        actionsManager.startNewAction(ID1);
        var originalAction = actionsManager.getOngoingActions().get(0);
        actionsManager.onTurnStart();
        assertEquals(1, actionsManager.getOngoingActions().size());
        var newAction = actionsManager.getOngoingActions().get(0);
        assertNotSame(originalAction, newAction);
        assertEquals(originalAction.getActionViewId(), newAction.getActionViewId());
        assertEquals(originalAction.getDuration(), newAction.getDuration());
    }

    @Test
    public void shouldReturnYields() {
        prepareSimpleActionView(Map.of("wood", 10), false);
        actionsManager.startNewAction(ID1);
        var yields = actionsManager.onTurnStart();
        assertEquals(1, yields.size());
        assertTrue(yields.containsKey("wood"));
        assertEquals(10, yields.get("wood"));
    }

    @Test
    public void shouldReturnAggregatedYields() {
        prepareSimpleActionView(Map.of("wood", 10), false);
        actionsManager.startNewAction(ID1);
        actionsManager.startNewAction(ID1);
        var yields = actionsManager.onTurnStart();
        assertEquals(1, yields.size());
        assertTrue(yields.containsKey("wood"));
        assertEquals(20, yields.get("wood"));
    }
}