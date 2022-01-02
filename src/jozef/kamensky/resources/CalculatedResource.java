package jozef.kamensky.resources;

import java.util.Map;

// Amount of this resource is calculated from other resource at start of turn
public class CalculatedResource extends BaseResource {

    private final String calculatedFrom;
    private final int multiplyBy;

    public CalculatedResource(String id, String name, String description, int amount, int maxAmount, String calculatedFrom, int multiplyBy) {
        super(id, name, description, amount, maxAmount);
        this.calculatedFrom = calculatedFrom;
        this.multiplyBy = multiplyBy;
    }

    @Override
    public void onTurnStart(Map<String, BaseResource> resourceMap) {
        BaseResource resource = resourceMap.get(calculatedFrom);
        if (resource == null) {
            this.setMinAmount();
            return;
        }
        setAmount(resource.getAmount() * multiplyBy);
    }
}
