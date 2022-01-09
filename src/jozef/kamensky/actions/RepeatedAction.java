package jozef.kamensky.actions;

import java.util.Collections;
import java.util.List;

public class RepeatedAction extends BaseAction {

    public RepeatedAction(String actionViewId, int duration) {
        super(actionViewId, duration);
    }

    @Override
    public List<BaseAction> getFollowUpActions() {
        return Collections.singletonList(new RepeatedAction(this.getActionViewId(), getDuration()));
    }
}
