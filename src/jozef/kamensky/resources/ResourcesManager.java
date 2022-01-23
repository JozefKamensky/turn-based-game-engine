package jozef.kamensky.resources;

import jozef.kamensky.OnTurnStart;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class ResourcesManager implements OnTurnStart {

    private final Map<String, BaseResource> resourceMap = new LinkedHashMap<>();

    public void insertResource(BaseResource resource) {
        resourceMap.put(resource.getId(), resource);
    }

    /*
    * Check whether player has enough resources.
    * */
    public boolean hasEnoughResources(Map<String, Integer> cost) {
        for(Map.Entry<String, Integer> entry : cost.entrySet()) {
            var resource = resourceMap.get(entry.getKey());
            if (resource == null) {
                return false;
            }
            if (resource.getAmount() < entry.getValue()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onTurnStart() {
        resourceMap.forEach((s, resource) -> resource.onTurnStart(resourceMap));
    }

    /* for test purposes only */
    protected Map<String, BaseResource> getResourceMap() {
        return Collections.unmodifiableMap(resourceMap);
    }
}
