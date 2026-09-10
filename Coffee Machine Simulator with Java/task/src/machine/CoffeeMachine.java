package machine;

import java.util.Scanner;

public class CoffeeMachine {
    public Scanner scanner;

    private int water;
    private int milk;
    private int coffee;
    private int cups;
    private int money;
    private int coffeesSinceClean;


    public static void main(String[] args) {
        CoffeeMachine coffeeMachine = new CoffeeMachine(
                400,
                120,
                9,
                540,
                550,
                new Scanner(System.in));
        coffeeMachine.run();
    }


    public CoffeeMachine(int water,
                         int coffee,
                         int cups,
                         int milk,
                         int money,
                         Scanner scanner) {
        this.coffee = coffee;
        this.cups = cups;
        this.milk = milk;
        this.money = money;
        this.scanner = scanner;
        this.water = water;
        this.coffeesSinceClean = 0;
    }

    private void run() {
        MachineAction actionCommand;
        boolean isExitPressed = false;
        while (!isExitPressed) {
            printManuSelection();
            String userMenuSelection = scanner.nextLine();
            actionCommand = MachineAction.valueOf(userMenuSelection.toUpperCase());

            switch (actionCommand) {
                case BUY -> handleBuyOption();
                case FILL -> handleFillOption();
                case CLEAN -> cleanMachine();
                case TAKE -> handleTakeOption();
                case REMAINING -> printRemainingSupplies();
                case EXIT -> isExitPressed = true;
            }

        }

    }

    private void cleanMachine() {
        coffeesSinceClean = 0;
        System.out.println("I have been cleaned!");
    }

    private void handleBuyOption() {
        if (coffeesSinceClean >= 10) {
            System.out.println("I need cleaning!");
            return;
        }
        printCoffeeOption();
        String s = scanner.nextLine();
        if ("back".equals(s)) {
            return;
        }
        final int userAnswer = Integer.parseInt(s);
        CoffeeType coffeeType = CoffeeType.values()[userAnswer - 1];
        makeCoffee(coffeeType);
    }

    private void handleFillOption() {
        System.out.println("Write how many ml of water you want to add:");
        water += Integer.parseInt(scanner.nextLine());
        System.out.println("Write how many ml of milk you want to add:");
        milk += Integer.parseInt(scanner.nextLine());
        System.out.println("Write how many grams of coffee beans you want to add:");
        coffee += Integer.parseInt(scanner.nextLine());
        System.out.println("Write how many disposable cups you want to add:");
        cups += Integer.parseInt(scanner.nextLine());

    }

    private void handleTakeOption() {
        System.out.printf("I gave you $%d\n", money);
        money = 0;
    }

    private void makeCoffee(CoffeeType coffeeType) {
        if (canPrepareCoffee(coffeeType)) {
            System.out.println("I have enough resources, making you a coffee!");
            --cups;
            water -= coffeeType.getRequiredWater();
            milk -= coffeeType.getRequiredMilk();
            coffee -= coffeeType.getRequiredCoffee();
            money += coffeeType.getPrice();
            ++coffeesSinceClean;
        }
    }

    private void printCoffeeOption() {
        StringBuilder sb = new StringBuilder("What do you want to buy? ");
        CoffeeType[] coffeeTypes = CoffeeType.values();
        for (int i = 0; i < coffeeTypes.length - 1; i++) {
            sb.append(i + 1)
                    .append(" - ")
                    .append(coffeeTypes[i].name().toLowerCase())
                    .append(", ");
        }

        sb.append(coffeeTypes.length)
                .append(" - ")
                .append(coffeeTypes[coffeeTypes.length - 1].name().toLowerCase())
                .append(", back to main menu:");

        System.out.println(sb);
    }

    private void printManuSelection() {
        StringBuilder sb = new StringBuilder("Write action (");
        MachineAction[] availableStates = MachineAction.values();
        for (int i = 0; i < availableStates.length - 1; i++) {
            sb.append(availableStates[i].name().toLowerCase())
                    .append(", ");
        }
        sb.append(availableStates[availableStates.length - 1].name().toLowerCase())
                .append("):");
        System.out.println(sb);
    }

    private void printRemainingSupplies() {
        System.out.printf("""
                
                The coffee machine has:
                %d ml of water
                %d ml of milk
                %d g of coffee beans
                %d disposable cups
                $%d of money
                
                """, water, milk, coffee, cups, money);

    }
    private boolean canPrepareCoffee(CoffeeType coffeeType) {
        if (this.water < coffeeType.getRequiredWater()) {
            System.out.println("Sorry, not enough water!");
            return false;
        }
        if (this.milk < coffeeType.getRequiredMilk()) {
            System.out.println("Sorry, not enough milk!");
            return false;
        }
        if (this.coffee < coffeeType.getRequiredCoffee()) {
            System.out.println("Sorry, not enough coffee beans!");
            return false;
        }
        if (this.cups < 1) {
            System.out.println("Sorry, not enough disposable cups!");
            return false;
        }
        return true;
    }
}