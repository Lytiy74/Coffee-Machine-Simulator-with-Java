package machine;

public enum CoffeeType {
    ESPRESSO(4, 16, 0, 250),
    LATTE(7, 20, 75, 350),
    CAPPUCCINO(6, 12, 100, 200);

    private final int requiredWater;
    private final int requiredCoffee;
    private final int requiredMilk;
    private final int price;

    CoffeeType(int price, int requiredCoffee, int requiredMilk, int requiredWater) {
        this.price = price;
        this.requiredCoffee = requiredCoffee;
        this.requiredMilk = requiredMilk;
        this.requiredWater = requiredWater;
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
}
