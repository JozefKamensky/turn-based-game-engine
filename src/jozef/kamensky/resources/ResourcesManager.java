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

    @Override
    public void onTurnStart() {
        resourceMap.forEach((s, resource) -> resource.onTurnStart(resourceMap));
    }

    /* for test purposes only */
    protected Map<String, BaseResource> getResourceMap() {
        return Collections.unmodifiableMap(resourceMap);
    }
}
