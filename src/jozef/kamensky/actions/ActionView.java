package jozef.kamensky.actions;

import jozef.kamensky.actions.yields.ActionYield;
import jozef.kamensky.actions.yields.ResourceYield;

import java.util.List;
import java.util.Map;

public class ActionView {

    private final String id;
    private final String name;
    private final String description;
    private final Map<String, Integer> cost;
    private final List<ResourceYield> resourceYields;
    private final List<ActionYield> actionYields;
    private final Integer duration;
    private final boolean isPeriodic;


    public ActionView(String id, String name, String description, Map<String, Integer> cost, List<ResourceYield> resourceYields, List<ActionYield> actionYields, Integer duration, boolean isPeriodic) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.resourceYields = resourceYields;
        this.actionYields = actionYields;
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

    public List<ResourceYield> getResourceYields() {
        return resourceYields;
    }

    public List<ActionYield> getActionYields() {
        return actionYields;
    }

    public Integer getDuration() {
        return duration;
    }

    public boolean isPeriodic() {
        return isPeriodic;
    }
}
