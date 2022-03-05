package jozef.kamensky;

import jozef.kamensky.actions.ActionView;
import jozef.kamensky.actions.ActionsManager;
import jozef.kamensky.resources.BaseResource;
import jozef.kamensky.resources.ResourceView;
import jozef.kamensky.resources.ResourcesManager;

import java.util.List;
import java.util.Map;

public class TurnManager {

    private final ResourcesManager resourcesManager;
    private final ActionsManager actionsManager;
    private int turn = 1;

    public TurnManager() {
        resourcesManager = new ResourcesManager();
        actionsManager = new ActionsManager();
    }

    public TurnManager(List<BaseResource> resources, List<ActionView> actions) {
        resourcesManager = new ResourcesManager();
        resourcesManager.insertResources(resources);
        actionsManager = new ActionsManager();
        actionsManager.addNewActions(actions);
    }

    public List<ResourceView> getResourceInfo() {
        return resourcesManager.getResources();
    }

    public List<ActionView> getActionsInfo() {
        return actionsManager.getUnlockedActions();
    }

    public List<ActionView> getDoableActionsInfo() {
        return actionsManager.getUnlockedActions().stream().filter(this::isActionDoable).toList();
    }

    public List<ActionView> getNotDoableActionsInfo() {
        return actionsManager.getUnlockedActions().stream().filter(a -> !isActionDoable(a)).toList();
    }

    private boolean isActionDoable(ActionView action) {
        boolean hasEnoughResources = resourcesManager.hasEnoughResources(action.getCost());
        boolean lessThanMaxParallel = actionsManager.numberOfOngoingActionsForId(action.id()) < action.maxParallel();
        return hasEnoughResources && lessThanMaxParallel;
    }

    public List<ActionView> getOngoingActionsInfo() {
        return actionsManager.getOngoingActions();
    }

    public void nextTurn() {
        Map<String, Integer> yields = actionsManager.onTurnStart();
        resourcesManager.onTurnStart();
        yields.forEach(resourcesManager::addResource);
        turn++;
    }

    public int getCurrentTurn() {
        return turn;
    }

    public void startAction(String id) {
        var doableActions = getDoableActionsInfo();
        var matches = doableActions.stream().filter(a -> a.getId().equals(id)).toList();
        if (matches.isEmpty()) {
            // TODO: exception
            return;
        }
        if (matches.size() > 1) {
            // TODO: exception
            return;
        }
        var actionToStart = matches.get(0);
        if (!isActionDoable(actionToStart)) {
            // TODO: exception
            return;
        }
        actionToStart.getCost().forEach((key, amount) -> resourcesManager.addResource(key, -amount));
        actionsManager.startNewAction(id);
    }
}
