package jozef.kamensky.gui;

import jozef.kamensky.TurnManager;
import jozef.kamensky.actions.ActionView;
import jozef.kamensky.resources.ResourceView;

import java.util.Collection;

public abstract class AbstractRenderer {

    final TurnManager turnManager;

    public AbstractRenderer(TurnManager turnManager) {
        this.turnManager = turnManager;
    }

    public abstract void renderResources(Collection<ResourceView> resources);
    public abstract void renderActions(Collection<ActionView> actions);
    public abstract void renderOngoingActions(Collection<ActionView> ongoingActions);
    public abstract void renderSelectActionInput();
    public abstract void renderCurrentTurn();

}
