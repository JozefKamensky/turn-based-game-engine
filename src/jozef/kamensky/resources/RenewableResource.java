package jozef.kamensky.resources;

import java.util.Map;

public class RenewableResource extends BaseResource {

    public RenewableResource(String id, String name, String description, int amount, int maxAmount) {
        super(id, name, description, amount, maxAmount);
    }

    @Override
    public void onTurnStart(Map<String, BaseResource> resourceMap) {
        this.setAmount(getMaxAmount());
    }
}
