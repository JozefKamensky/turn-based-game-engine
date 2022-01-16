package jozef.kamensky.actions.yields;

public class ActionYield {

    public enum ActionYieldType {
        ACTION_UNLOCK,
        ACTION_LOCK,
        ACTION_START,
        ACTION_STOP,
    }

    private final ActionYieldType type;
    private final String id;

    public ActionYield(ActionYieldType type, String id) {
        this.type = type;
        this.id = id;
    }

    public ActionYieldType getType() {
        return type;
    }

    public String getId() {
        return id;
    }
}
