package jozef.kamensky.resources;

import java.util.Map;

public class NormalResource extends BaseResource {

    public NormalResource(String id, String name, String description, int amount, int maxAmount) {
        super(id, name, description, amount, maxAmount);
    }

    @Override
    public void onTurnStart(Map<String, BaseResource> resourceMap) {}
}
