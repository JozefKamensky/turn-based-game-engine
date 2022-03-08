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
        startGame();
    }

    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    @Override
    void renderResources(Collection<ResourceView> resources) {
        System.out.println("Resources:");
        resources.forEach(r -> System.out.printf("%s: %d|%d\n", r.getName(), r.getAmount(), r.getMaxAmount()));
    }

    @Override
    void renderDoableActions(Collection<ActionView> actions) {
        System.out.println("Available Actions:");
        actions.forEach(this::renderAction);
    }

    @Override
    void renderNotDoableActions(Collection<ActionView> actions) {
        System.out.println("Not Available Actions:");
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
    void renderOngoingActions(Collection<ActionView> ongoingActions) {
        System.out.println("Ongoing Actions:");
        ongoingActions.forEach(a -> System.out.printf("[%s] %s\n",a.getId(), a.getDescription()));
    }

    public void startGame() {
        System.out.println("Enter command you want to perform (HELP shows list of commands):\n");
        handleInput();
    }
    private void handleInput() {
        try {
            String input = reader.readLine();
            String command;
            String id = "";
            int spaceIndex = input.indexOf(" ");
            if (spaceIndex != -1) {
                command = input.substring(0, spaceIndex);
                id = input.substring(spaceIndex + 1);
            } else {
                command = input;
            }
            command = command.toUpperCase();
            switch (command) {
                case "HELP" -> renderHelp();
                case "EXIT" -> System.exit(0);
                case "NEXT" -> nextTurn();
                case "RESOURCES" -> renderResources(turnManager.getResourceInfo());
                case "ACTIONS" -> {
                    renderDoableActions(turnManager.getDoableActionsInfo());
                    renderNotDoableActions(turnManager.getNotDoableActionsInfo());
                }
                case "ONGOING" -> renderOngoingActions(turnManager.getOngoingActionsInfo());
                case "START" -> startAction(id);
                case "TURN" -> renderCurrentTurn();
                default -> System.out.println("Unknown command");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        handleInput();
    }

    @Override
    public void renderCurrentTurn() {
        System.out.printf("Current turn: %d\n", turnManager.getCurrentTurn());
    }

    private void nextTurn() {
        turnManager.nextTurn();
        renderCurrentTurn();
    }

    private void startAction(String id) {
        var result = turnManager.startAction(id);
        System.out.println(result ? "action started." : "action could not be started");
    }

    private void renderHelp() {
        System.out.println("this is help");
    }
}
