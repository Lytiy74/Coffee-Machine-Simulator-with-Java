package machine;

import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

public class CoffeeMachine {
    private final CoffeeMachineService service;
    private final Scanner scanner;

    public static void main(String[] args) {
        CoffeeMachineService service = new CoffeeMachineService();
        CoffeeMachine coffeeMachine = new CoffeeMachine(service, new Scanner(System.in));
        coffeeMachine.run();
    }

    public CoffeeMachine(CoffeeMachineService service, Scanner scanner) {
        this.service = Objects.requireNonNull(service, "service must not be null");
        this.scanner = Objects.requireNonNull(scanner, "scanner must not be null");
    }

    public void run() {
        boolean isExitPressed = false;
        while (!isExitPressed) {
            printMenuSelection();
            String userMenuSelection = scanner.nextLine();
            Optional<MachineAction> actionOpt = MachineAction.from(userMenuSelection);

            if (actionOpt.isEmpty()) {
                continue;
            }

            switch (actionOpt.get()) {
                case BUY -> handleBuyOption();
                case FILL -> handleFillOption();
                case CLEAN -> handleCleanOption();
                case TAKE -> handleTakeOption();
                case REMAINING -> printRemainingSupplies();
                case EXIT -> isExitPressed = true;
            }
        }
    }

    private void handleCleanOption() {
        service.clean();
        System.out.println("I have been cleaned!");
    }

    private void handleBuyOption() {
        if (service.needsCleaning()) {
            System.out.println("I need cleaning!");
            return;
        }

        printCoffeeOption();
        String choice = scanner.nextLine().trim();
        if ("back".equalsIgnoreCase(choice)) {
            return;
        }

        Optional<CoffeeType> coffeeTypeOpt = CoffeeType.fromInput(choice);
        if (coffeeTypeOpt.isPresent()) {
            BrewResult result = service.makeCoffee(coffeeTypeOpt.get());
            System.out.println(result.getMessage());
        }
    }

    private void handleFillOption() {
        System.out.println("Write how many ml of water you want to add:");
        int water = readInt();

        System.out.println("Write how many ml of milk you want to add:");
        int milk = readInt();

        System.out.println("Write how many grams of coffee beans you want to add:");
        int coffee = readInt();

        System.out.println("Write how many disposable cups you want to add:");
        int cups = readInt();

        service.fillSupplies(water, milk, coffee, cups);
    }

    private void handleTakeOption() {
        int moneyTaken = service.takeMoney();
        System.out.printf("I gave you $%d%n", moneyTaken);
    }

    private void printCoffeeOption() {
        StringBuilder sb = new StringBuilder("What do you want to buy? ");
        CoffeeType[] coffeeTypes = CoffeeType.values();
        for (CoffeeType type : coffeeTypes) {
            sb.append(type.getId())
                    .append(" - ")
                    .append(type.name().toLowerCase())
                    .append(", ");
        }
        sb.append("back - to main menu:");
        System.out.println(sb);
    }

    private void printMenuSelection() {
        StringBuilder sb = new StringBuilder("Write action (");
        MachineAction[] availableStates = MachineAction.values();
        for (int i = 0; i < availableStates.length - 1; i++) {
            sb.append(availableStates[i].getCommand()).append(", ");
        }
        sb.append(availableStates[availableStates.length - 1].getCommand())
                .append("):");
        System.out.println(sb);
    }

    private void printRemainingSupplies() {
        Inventory inventory = service.getInventory();
        System.out.printf("""
                
                The coffee machine has:
                %d ml of water
                %d ml of milk
                %d g of coffee beans
                %d disposable cups
                $%d of money
                
                """,
                inventory.getWater(),
                inventory.getMilk(),
                inventory.getCoffeeBeans(),
                inventory.getCups(),
                inventory.getMoney()
        );
    }

    private int readInt() {
        String line = scanner.nextLine();
        try {
            return Integer.parseInt(line.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}