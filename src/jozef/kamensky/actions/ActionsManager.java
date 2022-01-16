package jozef.kamensky.actions;

import jozef.kamensky.actions.yields.ActionYield;

import java.util.*;

public class ActionsManager {

    private final Map<String, ActionView> actionsMap = new LinkedHashMap<>();
    private List<BaseAction> ongoingActions = new LinkedList<>();

    public void addNewAction(ActionView action) {
        actionsMap.put(action.getId(), action);
    }

    public Map<String, ActionView> getActionsMap() {
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
        for (BaseAction action: ongoingActions) {
            if (action.isCompleted()) {
                var actionView = actionsMap.get(action.getActionViewId());
                handleResourceYields(actionView, yields);
                handleActionYields(actionView, newOngoingActions);
                newOngoingActions.addAll(action.getFollowUpActions());
            } else {
                newOngoingActions.add(action);
            }
        }
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

    private void handleActionYields(ActionView actionView, List<BaseAction> newOngoingActions) {
        var actionYields = actionView.getActionYields();
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
    protected List<BaseAction> getOngoingActions() {
        return ongoingActions;
    }
}