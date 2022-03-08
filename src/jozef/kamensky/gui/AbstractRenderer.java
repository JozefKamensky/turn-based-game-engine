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

    abstract void renderResources(Collection<ResourceView> resources);
    abstract void renderDoableActions(Collection<ActionView> actions);
    abstract void renderNotDoableActions(Collection<ActionView> actions);
    abstract void renderOngoingActions(Collection<ActionView> ongoingActions);
    abstract void renderCurrentTurn();

}
