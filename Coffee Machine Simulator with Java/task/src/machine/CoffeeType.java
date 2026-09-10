package machine;

import java.util.Arrays;
import java.util.Optional;

public enum CoffeeType {
    ESPRESSO(1, 4, 16, 0, 250),
    LATTE(2, 7, 20, 75, 350),
    CAPPUCCINO(3, 6, 12, 100, 200);

    private final int id;
    private final int price;
    private final int requiredCoffee;
    private final int requiredMilk;
    private final int requiredWater;

    CoffeeType(int id, int price, int requiredCoffee, int requiredMilk, int requiredWater) {
        this.id = id;
        this.price = price;
        this.requiredCoffee = requiredCoffee;
        this.requiredMilk = requiredMilk;
        this.requiredWater = requiredWater;
    }

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public int getRequiredCoffee() {
        return requiredCoffee;
    }

    public int getRequiredMilk() {
        return requiredMilk;
    }

    public int getRequiredWater() {
        return requiredWater;
    }

    public static Optional<CoffeeType> findById(int id) {
        return Arrays.stream(values())
                .filter(coffee -> coffee.id == id)
                .findFirst();
    }

    public static Optional<CoffeeType> fromInput(String input) {
        if (input == null || input.isBlank()) {
            return Optional.empty();
        }
        try {
            int id = Integer.parseInt(input.trim());
            return findById(id);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
