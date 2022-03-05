package jozef.kamensky.gui;

import jozef.kamensky.TurnManager;
import jozef.kamensky.actions.ActionView;
import jozef.kamensky.resources.ResourceView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;

public class ConsoleRenderer extends AbstractRenderer {

    public ConsoleRenderer(TurnManager turnManager) {
        super(turnManager);
    }

    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public void renderResources(Collection<ResourceView> resources) {
        System.out.println("\nResources:");
        resources.forEach(r -> System.out.printf("%s: %d|%d\n", r.getName(), r.getAmount(), r.getMaxAmount()));
    }

    @Override
    public void renderDoableActions(Collection<ActionView> actions) {
        System.out.println("\nAvailable Actions:");
        actions.forEach(this::renderAction);
    }

    @Override
    public void renderNotDoableActions(Collection<ActionView> actions) {
        System.out.println("\nNot Available Actions:");
        actions.forEach(this::renderAction);
    }

    private void renderAction(ActionView a) {
        StringBuilder b = new StringBuilder();
        if (!a.getCost().isEmpty()) {
            a.getCost().forEach((id, amount) -> b.append(String.format("%d %s,", amount, id)));
            b.deleteCharAt(b.lastIndexOf(","));
        } else {
            b.append("no costs");
        }
        System.out.printf("[%s] %s - %s\n",a.getId(), a.getDescription(), b.toString());
    }

    @Override
    public void renderOngoingActions(Collection<ActionView> ongoingActions) {
        System.out.println("\nOngoing Actions:");
        ongoingActions.forEach(a -> System.out.printf("[%s] %s\n",a.getId(), a.getDescription()));
    }

    @Override
    public void renderSelectActionInput() {
        System.out.println("\nEnter id of action you want to perform, EXIT to exit or NEXT to end turn:\n");
        try {
            String input = reader.readLine();
            if ("NEXT".equals(input)) {
                turnManager.nextTurn();
            } else if ("EXIT".equals(input)) {
                System.exit(0);
            } else {
                turnManager.startAction(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void renderCurrentTurn(int turn) {
        System.out.printf("Current turn: %d\n", turn);
    }
}
