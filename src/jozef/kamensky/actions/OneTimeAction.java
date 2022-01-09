package jozef.kamensky.actions;

import java.util.Collections;
import java.util.List;

public class OneTimeAction extends BaseAction {

    public OneTimeAction(String actionViewId, int duration) {
        super(actionViewId, duration);
    }

    @Override
    public List<BaseAction> getFollowUpActions() {
        return Collections.emptyList();
    }
}
