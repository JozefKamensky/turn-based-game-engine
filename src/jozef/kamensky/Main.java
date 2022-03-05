package jozef.kamensky;

import jozef.kamensky.actions.ActionView;
import jozef.kamensky.actions.OneTimeAction;
import jozef.kamensky.actions.yields.ActionYield;
import jozef.kamensky.actions.yields.ResourceYield;
import jozef.kamensky.gui.ConsoleRenderer;
import jozef.kamensky.resources.RegularResource;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {
        final String WOOD_ID = "wood";
        final String COLLECT_WOOD_ID_1 = "collect_wood_1";
        final String COLLECT_WOOD_ID_2 = "collect_wood_2";
        final String UNLOCK_COLLECT_WOOD_ID_2 = "unlock_collect_wood_2";
        RegularResource wood = new RegularResource(WOOD_ID, "Wood", "Basic construction material. Also used as fuel.", 0, 100);

        ActionView collectWood1 = new ActionView(
                COLLECT_WOOD_ID_1,
                "Collect Wood (1)",
                "Collect 10 units of wood.",
                Collections.emptyMap(),
                List.of(new ResourceYield(WOOD_ID, 10)),
                Collections.emptyList(),
                1,
                false,
                true
                );
        ActionView collectWood2 = new ActionView(
                COLLECT_WOOD_ID_2,
                "Collect Wood (2)",
                "Collect 25 units of wood.",
                Collections.emptyMap(),
                List.of(new ResourceYield(WOOD_ID, 25)),
                Collections.emptyList(),
                1,
                false,
                false
        );
        ActionView unlockCollectWood2 = new ActionView(
                UNLOCK_COLLECT_WOOD_ID_2,
                "Upgrade Wood collection",
                "Upgrade wood production from 10 to 25.",
                Map.of(WOOD_ID, 50),
                Collections.emptyList(),
                List.of(
                        new ActionYield(ActionYield.ActionYieldType.ACTION_LOCK, COLLECT_WOOD_ID_1),
                        new ActionYield(ActionYield.ActionYieldType.ACTION_UNLOCK, COLLECT_WOOD_ID_2)
                ),
                1,
                false,
                true
        );

        TurnManager turnManager = new TurnManager(List.of(wood), List.of(collectWood1, collectWood2, unlockCollectWood2));
        ConsoleRenderer consoleRenderer = new ConsoleRenderer(turnManager);
        while(true) {
            consoleRenderer.render();
        }
    }
}
