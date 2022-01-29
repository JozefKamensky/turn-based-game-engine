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
        resources.forEach(r -> System.out.printf("%s: %d|%d%n", r.getName(), r.getAmount(), r.getMaxAmount()));
    }

    @Override
    public void renderActions(Collection<ActionView> actions) {

        actions.forEach(a -> {
            StringBuilder b = new StringBuilder();
            a.getCost().forEach((id, amount) -> b.append(String.format("%d %s,", amount, id)));
            b.deleteCharAt(b.lastIndexOf(","));
            System.out.printf("[%s] %s - %s%n",a.getId(), a.getDescription(), b.toString());
        });
    }

    @Override
    public void renderOngoingActions(Collection<ActionView> ongoingActions) {

    }

    @Override
    public void renderSelectActionInput() {
        System.out.println("Enter id of action you want to perform:");
        try {
            String input = reader.readLine();
            turnManager.startAction(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void renderCurrentTurn() {
        System.out.printf("Current turn: %d", turnManager.getCurrentTurn());
    }
}
