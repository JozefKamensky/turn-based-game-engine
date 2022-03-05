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
        actionsManager.startNewAction(id);
    }
}
