package jozef.kamensky.resources;

public class ResourceView {

    private final String name;
    private final String description;
    private int amount;
    private final int maxAmount;

    public ResourceView(String name, String description, int amount, int maxAmount) {
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.maxAmount = maxAmount;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getAmount() {
        return amount;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public static ResourceView fromResource(BaseResource resource) {
        return new ResourceView(resource.getName(), resource.getDescription(), resource.getAmount(), resource.getMaxAmount());
    }
}
