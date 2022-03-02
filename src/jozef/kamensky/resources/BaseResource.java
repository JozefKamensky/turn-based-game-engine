package jozef.kamensky.resources;

import java.util.Map;

public abstract class BaseResource {

    private static final int MIN_AMOUNT = 0;
    private final String id;
    private final String name;
    final String description;
    private int amount;
    private final int maxAmount;

    public BaseResource(String id, String name, String description, int amount, int maxAmount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.maxAmount = maxAmount;
        clipAmountToRange();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public abstract String getDescription();

    public int getMaxAmount() {
        return maxAmount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
        clipAmountToRange();
    }

    public void addAmount(int toAdd) {
        this.amount += toAdd;
        clipAmountToRange();
    }

    public void setMinAmount() {
        this.amount = MIN_AMOUNT;
    }
    private void clipAmountToRange() {
        if (this.amount < MIN_AMOUNT) {
            this.amount = MIN_AMOUNT;
        }
        if (this.amount > this.maxAmount) {
            this.amount = this.maxAmount;
        }
    }

    public abstract void onTurnStart(Map<String, BaseResource> resourceMap);
}
