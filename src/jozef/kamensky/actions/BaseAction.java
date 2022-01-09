package jozef.kamensky.actions;

import java.util.List;

public abstract class BaseAction {

    private final String actionViewId;
    private final int duration;

    private int turnsToComplete;

    public BaseAction(String actionViewId, int duration) {
        this.actionViewId = actionViewId;
        this.duration = duration;
        turnsToComplete = duration;
    }

    public String getActionViewId() {
        return actionViewId;
    }

    public int getDuration() {
        return duration;
    }

    public boolean isCompleted() {
        turnsToComplete--;
        return turnsToComplete == 0;
    };

    public abstract List<BaseAction> getFollowUpActions();
}
