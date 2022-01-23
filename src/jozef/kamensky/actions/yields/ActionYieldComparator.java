package jozef.kamensky.actions.yields;

import java.util.Comparator;

public class ActionYieldComparator implements Comparator<ActionYield> {
    // order action yields by their type, in order as defined in enum
    @Override
    public int compare(ActionYield o1, ActionYield o2) {
        return o1.getType().compareTo(o2.getType());
    }
}
