package jozef.kamensky.actions;

import jozef.kamensky.actions.yields.ActionYield;
import jozef.kamensky.actions.yields.ResourceYield;

import java.util.List;
import java.util.Map;

public record ActionView(String id, String name, String description,
                         Map<String, Integer> cost,
                         List<ResourceYield> resourceYields,
                         List<ActionYield> actionYields,
                         Integer duration, boolean isPeriodic, boolean isVisibleByPlayer, int maxParallel) {

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

    public boolean isVisibleByPlayer() {
        return isVisibleByPlayer;
    }

    public int maxParallel() {
        return maxParallel;
    }

    public ActionView cloneAsUnlocked() {
        return new ActionView(id, name, description, cost, resourceYields, actionYields, duration, isPeriodic, true, maxParallel);
    }

    public ActionView cloneAsLocked() {
        return new ActionView(id, name, description, cost, resourceYields, actionYields, duration, isPeriodic, false, maxParallel);
    }

}
