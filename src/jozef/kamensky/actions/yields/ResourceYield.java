package jozef.kamensky.actions.yields;

public class ResourceYield {

    private final String resourceId;
    private final Integer amount;

    public ResourceYield(String resourceId, Integer amount) {
        this.resourceId = resourceId;
        this.amount = amount;
    }

    public String getResourceId() {
        return resourceId;
    }

    public Integer getAmount() {
        return amount;
    }
}
