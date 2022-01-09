package jozef.kamensky.actions;

import java.util.Map;

public class ActionView {

    private final String id;
    private final String name;
    private final String description;
    private final Map<String, Integer> cost;
    private final Map<String, Integer> yield;
    private final Integer duration;
    private final boolean isPeriodic;

    public ActionView(String id, String name, String description, Map<String, Integer> cost, Map<String, Integer> yield, Integer duration, boolean isPeriodic) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.yield = yield;
        this.duration = duration;
        this.isPeriodic = isPeriodic;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Map<String, Integer> getCost() {
        return cost;
    }

    public Map<String, Integer> getYield() {
        return yield;
    }

    public Integer getDuration() {
        return duration;
    }

    public boolean isPeriodic() {
        return isPeriodic;
    }
}
