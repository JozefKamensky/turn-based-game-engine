package jozef.kamensky.resources;

import jozef.kamensky.OnTurnStart;

import java.util.*;

public class ResourcesManager implements OnTurnStart {

    private final Map<String, BaseResource> resourceMap = new LinkedHashMap<>();

    public void insertResource(BaseResource resource) {
        resourceMap.put(resource.getId(), resource);
    }

    public void insertResources(Collection<BaseResource> resources) {
       resources.forEach(r -> resourceMap.put(r.getId(), r));
    }

    public List<ResourceView> getResources() {
        return resourceMap.values().stream().map(ResourceView::fromResource).toList();
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

    public void addResource(String id, Integer amount) {
        var r = resourceMap.get(id);
        if (r == null) return;
        r.addAmount(amount);
    }

    /* for test purposes only */
    protected Map<String, BaseResource> getResourceMap() {
        return Collections.unmodifiableMap(resourceMap);
    }
}
