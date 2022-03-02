package jozef.kamensky.resources;

import java.util.Map;

public class RegularResource extends BaseResource {

    public RegularResource(String id, String name, String description, int amount, int maxAmount) {
        super(id, name, description, amount, maxAmount);
    }

    @Override
    public void onTurnStart(Map<String, BaseResource> resourceMap) {}

    @Override
    public String getDescription() {
        return super.description;
    }
}
