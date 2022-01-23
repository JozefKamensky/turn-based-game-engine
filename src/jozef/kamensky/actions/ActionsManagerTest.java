package jozef.kamensky.actions;

import jozef.kamensky.actions.yields.ActionYield;
import jozef.kamensky.actions.yields.ResourceYield;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ActionsManagerTest {

    private final static String ID1 = "aw1";
    private final static String ID2 = "aw2";
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
        var aw = new ActionView(ID1, "test", "test", null, Collections.emptyList(), Collections.emptyList(), DURATION, isPeriodic, true);
        actionsManager.addNewAction(aw);
    }

    private void prepareLongSimpleActionView() {
        var aw = new ActionView(ID1, "test", "test", null, Collections.emptyList(), Collections.emptyList(), 2, false, true);
        actionsManager.addNewAction(aw);
    }

    private void prepareSimpleActionViewNotVisibleToUser() {
        var aw = new ActionView(ID1, "test", "test", null, Collections.emptyList(), Collections.emptyList(), DURATION, false, false);
        actionsManager.addNewAction(aw);
    }

    private void prepareSimpleActionViewResourceYields(List<ResourceYield> resourceYields) {
        var aw = new ActionView(ID1, "test", "test", null, resourceYields, Collections.emptyList(), DURATION, false, true);
        actionsManager.addNewAction(aw);
    }

    private void prepareSimpleActionViewResourceYields(List<ResourceYield> resourceYields, int duration) {
        var aw = new ActionView(ID1, "test", "test", null, resourceYields, Collections.emptyList(), duration, false, true);
        actionsManager.addNewAction(aw);
    }

    private void prepareSimpleActionViewActionYields(List<ActionYield> actionYields) {
        prepareSimpleActionViewActionYields(ID1, actionYields);
    }

    private void prepareSimpleActionViewActionYields(String id, List<ActionYield> actionYields) {
        var aw = new ActionView(id, "test", "test", null, Collections.emptyList(), actionYields, DURATION, false, true);
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
    public void shouldReturnResourceYields() {
        this.prepareSimpleActionViewResourceYields(Collections.singletonList(new ResourceYield("wood", 10)));
        actionsManager.startNewAction(ID1);
        var yields = actionsManager.onTurnStart();
        assertEquals(1, yields.size());
        assertTrue(yields.containsKey("wood"));
        assertEquals(10, yields.get("wood"));
    }

    @Test
    public void shouldReturnAggregatedResourceYields() {
        this.prepareSimpleActionViewResourceYields(Collections.singletonList(new ResourceYield("wood", 10)));
        actionsManager.startNewAction(ID1);
        actionsManager.startNewAction(ID1);
        var yields = actionsManager.onTurnStart();
        assertEquals(1, yields.size());
        assertTrue(yields.containsKey("wood"));
        assertEquals(20, yields.get("wood"));
    }

    @Test
    public void shouldHandleUnlockActionYield() {
        this.prepareSimpleActionViewNotVisibleToUser();
        assertFalse(actionsManager.getActionsMap().get(ID1).isVisibleByPlayer());
        this.prepareSimpleActionViewActionYields(ID2, List.of(new ActionYield(ActionYield.ActionYieldType.ACTION_UNLOCK, ID1)));
        actionsManager.startNewAction(ID2);
        actionsManager.onTurnStart();
        assertTrue(actionsManager.getActionsMap().get(ID1).isVisibleByPlayer());
    }

    @Test
    public void shouldHandleLockAction() {
        this.prepareSimpleActionView(false);
        this.prepareSimpleActionViewActionYields(ID2, List.of(new ActionYield(ActionYield.ActionYieldType.ACTION_LOCK, ID1)));
        assertNotNull(actionsManager.getActionsMap().get(ID1));
        actionsManager.startNewAction(ID2);
        actionsManager.onTurnStart();
        assertFalse(actionsManager.getActionsMap().get(ID1).isVisibleByPlayer());
    }

    @Test
    public void shouldCorrectlyCompleteActionEvenAfterItIsLocked() {
        this.prepareSimpleActionViewResourceYields(Collections.singletonList(new ResourceYield("wood", 10)), 2);
        this.prepareSimpleActionViewActionYields(ID2, List.of(new ActionYield(ActionYield.ActionYieldType.ACTION_LOCK, ID1)));
        assertTrue(actionsManager.getActionsMap().get(ID1).isVisibleByPlayer());
        actionsManager.startNewAction(ID1);
        actionsManager.startNewAction(ID2);
        actionsManager.onTurnStart();
        assertFalse(actionsManager.getActionsMap().get(ID1).isVisibleByPlayer());
        assertEquals(1, actionsManager.getOngoingActions().size());
        var yields = actionsManager.onTurnStart();
        assertEquals(1, yields.size());
        assertEquals(10, yields.get("wood"));
    }

    @Test
    public void shouldHandleStartAction() {
        this.prepareSimpleActionView(false);
        this.prepareSimpleActionViewActionYields(ID2, List.of(new ActionYield(ActionYield.ActionYieldType.ACTION_START, ID1)));
        assertTrue(actionsManager.getOngoingActions().isEmpty());
        actionsManager.startNewAction(ID2);
        actionsManager.onTurnStart();
        // action for actionview ID2 must end
        assertEquals(0, actionsManager.getOngoingActions().stream().filter(a -> a.getActionViewId().equals(ID2)).count());
        // action for actionview ID1 must be scheduled
        assertEquals(1, actionsManager.getOngoingActions().stream().filter(a -> a.getActionViewId().equals(ID1)).count());
    }

    @Test
    public void shouldHandleStopAction() {
        this.prepareLongSimpleActionView();
        this.prepareSimpleActionViewActionYields(ID2, List.of(new ActionYield(ActionYield.ActionYieldType.ACTION_STOP, ID1)));
        actionsManager.startNewAction(ID1);
        actionsManager.startNewAction(ID2);
        // both must be scheduled
        assertEquals(1, actionsManager.getOngoingActions().stream().filter(a -> a.getActionViewId().equals(ID1)).count());
        assertEquals(1, actionsManager.getOngoingActions().stream().filter(a -> a.getActionViewId().equals(ID2)).count());
        actionsManager.onTurnStart();
        // both must be removed - ID2 was completed, ID1 was removed before completion
        assertEquals(0, actionsManager.getOngoingActions().stream().filter(a -> a.getActionViewId().equals(ID1)).count());
        assertEquals(0, actionsManager.getOngoingActions().stream().filter(a -> a.getActionViewId().equals(ID2)).count());
    }

    @Test
    public void shouldHandleStopActionRegardlessOfOrder() {
        this.prepareLongSimpleActionView();
        this.prepareSimpleActionViewActionYields(ID2, List.of(new ActionYield(ActionYield.ActionYieldType.ACTION_STOP, ID1)));
        actionsManager.startNewAction(ID2);
        actionsManager.startNewAction(ID1);
        // both must be scheduled
        assertEquals(1, actionsManager.getOngoingActions().stream().filter(a -> a.getActionViewId().equals(ID1)).count());
        assertEquals(1, actionsManager.getOngoingActions().stream().filter(a -> a.getActionViewId().equals(ID2)).count());
        actionsManager.onTurnStart();
        // both must be removed, regardless of order
        assertEquals(0, actionsManager.getOngoingActions().stream().filter(a -> a.getActionViewId().equals(ID1)).count());
        assertEquals(0, actionsManager.getOngoingActions().stream().filter(a -> a.getActionViewId().equals(ID2)).count());
    }
}