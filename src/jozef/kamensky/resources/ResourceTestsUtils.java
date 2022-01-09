package jozef.kamensky.resources;

public class ResourceTestsUtils {

    public static final String ID = "testMaterial1";
    private static final String NAME = "Test Material #1";
    private static final String DESC = "This is material for test purposes.";

    public static BaseResource createRegularResource(int amount, int max) {
        return new RegularResource(ID, NAME, DESC, amount, max);
    }

    public static BaseResource createRegularResource(String id, int amount, int max) {
        return new RegularResource(id, id, id, amount, max);
    }

    public static BaseResource createRenewableResource(int amount, int max) {
        return new RenewableResource(ID, NAME, DESC, amount, max);
    }

    public static BaseResource createRenewableResource(String id, int amount, int max) {
        return new RenewableResource(id, id, id, amount, max);
    }

    public static CalculatedResource createCalculatedResource(int amount, int max, String calculateFrom, int multiplyBy) {
        return new CalculatedResource(ID, NAME, DESC, amount, max, calculateFrom, multiplyBy);
    }

    public static CalculatedResource createCalculatedResource(String id, int amount, int max, String calculateFrom, int multiplyBy) {
        return new CalculatedResource(id, id, id, amount, max, calculateFrom, multiplyBy);
    }

}
