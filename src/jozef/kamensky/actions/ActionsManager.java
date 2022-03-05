package jozef.kamensky.actions;

import jozef.kamensky.actions.yields.ActionYield;
import jozef.kamensky.actions.yields.ActionYieldComparator;

import java.util.*;

public class ActionsManager {

    private final Map<String, ActionView> actionsMap = new LinkedHashMap<>();
    private List<BaseAction> ongoingActions = new LinkedList<>();

    public void addNewAction(ActionView action) {
        actionsMap.put(action.getId(), action);
    }
    public void addNewActions(Collection<ActionView> actions) {
        actions.forEach(a -> actionsMap.put(a.getId(), a));
    }

    /*
    * Get all actions that player can do, given unlimited amount of all necessary resources.
    * */
    public List<ActionView> getUnlockedActions() {
        return actionsMap.values().stream()
                .filter(ActionView::isVisibleByPlayer).toList();
    }

    public List<ActionView> getOngoingActions() {
        return ongoingActions.stream().map(a -> actionsMap.get(a.getActionViewId())).toList();
    }

   protected Map<String, ActionView> getActionsMap() {
        return Collections.unmodifiableMap(actionsMap);
    }

    public void startNewAction(String id) {
        ongoingActions.add(createActionFromActionView(id));
    }

    private BaseAction createActionFromActionView(String id) {
        var actionToStart = actionsMap.get(id);
        if (actionToStart.isPeriodic()) {
            return new RepeatedAction(actionToStart.getId(), actionToStart.getDuration());
        } else {
            return new OneTimeAction(actionToStart.getId(), actionToStart.getDuration());
        }
    }

    public void stopAction(String id) {
        stopAction(ongoingActions, id);
    }

    private void stopAction(List<BaseAction> actionList, String id) {
        actionList.removeIf(a -> a.getActionViewId().equals(id));
    };

    public Map<String, Integer> onTurnStart() {
        Map<String, Integer> yields = new HashMap<>();
        List<BaseAction> newOngoingActions = new LinkedList<>();
        List<ActionYield> actionYields = new ArrayList<>();
        for (BaseAction action: ongoingActions) {
            if (action.isCompleted()) {
                var actionView = actionsMap.get(action.getActionViewId());
                handleResourceYields(actionView, yields);
                actionYields.addAll(actionView.getActionYields());
                newOngoingActions.addAll(action.getFollowUpActions());
            } else {
                newOngoingActions.add(action);
            }
        }
        actionYields.sort(new ActionYieldComparator());
        handleActionYields(actionYields, newOngoingActions);
        ongoingActions = newOngoingActions;
        return yields;
    }

    private void handleResourceYields(ActionView actionView, Map<String, Integer> yields) {
        var actionYields = actionView.getResourceYields();
        actionYields.forEach(yield -> {
            yields.computeIfPresent(yield.getResourceId(), (id2, amount2) -> yield.getAmount() + amount2);
            yields.putIfAbsent(yield.getResourceId(), yield.getAmount());
        });
    }

    private void handleActionYields(List<ActionYield> actionYields, List<BaseAction> newOngoingActions) {
        for (ActionYield actionYield : actionYields) {
            switch (actionYield.getType()) {
                case ACTION_UNLOCK -> actionsMap.put(actionYield.getId(), actionsMap.get(actionYield.getId()).cloneAsUnlocked());
                case ACTION_LOCK -> actionsMap.put(actionYield.getId(), actionsMap.get(actionYield.getId()).cloneAsLocked());
                case ACTION_START -> newOngoingActions.add(createActionFromActionView(actionYield.getId()));
                case ACTION_STOP -> stopAction(newOngoingActions, actionYield.getId());
            }
        }
    }

    /* for test purposes only */
    protected List<BaseAction> getOngoingActionsTest() {
        return ongoingActions;
    }
}
