package jozef.kamensky.actions;

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
        var actionToStart = actionsMap.get(id);
        if (actionToStart.isPeriodic()) {
            ongoingActions.add(new RepeatedAction(actionToStart.getId(), actionToStart.getDuration()));
        } else {
            ongoingActions.add(new OneTimeAction(actionToStart.getId(), actionToStart.getDuration()));
        }
    }

    public Map<String, Integer> onTurnStart() {
        Map<String, Integer> yields = new HashMap<>();
        List<BaseAction> newOngoingActions = new LinkedList<>();
        for (BaseAction action: ongoingActions) {
            if (action.isCompleted()) {
                var actionView = actionsMap.get(action.getActionViewId());
                var actionYields = actionView.getYield();
                actionYields.forEach((id1, amount1) -> {
                    yields.computeIfPresent(id1, (id2, amount2) -> amount1 + amount2);
                    yields.putIfAbsent(id1, amount1);
                });
                newOngoingActions.addAll(action.getFollowUpActions());
            } else {
                newOngoingActions.add(action);
            }
        }
        ongoingActions = newOngoingActions;
        return yields;
    }

    /* for test purposes only */
    protected List<BaseAction> getOngoingActions() {
        return ongoingActions;
    }
}
