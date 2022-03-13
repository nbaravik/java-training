import java.util.LinkedHashMap;
import java.util.Map;

public class CoffeeMachine {

    public static final String WATER = "water";
    public static final String MILK = "milk";
    public static final String COFFEE = "coffee beans";
    public static final String CUPS = "disposable cups";
    public static final String CASH = "money";

    private int waterCapacity;
    private int milkCapacity;
    private int coffeeCapacity;
    private int cupsAmount;
    private int cashAmount;

    public CoffeeMachine(int water, int milk, int coffee, int cups, int cash) {
        this.waterCapacity = water;
        this.milkCapacity = milk;
        this.coffeeCapacity = coffee;
        this.cupsAmount = cups;
        this.cashAmount = cash;
    }

    public static String resourceOutputFormat(String resource, int amount) {
        switch (resource) {
            case WATER:
            case MILK: {
                return amount + "ml of " + resource;
            }
            case COFFEE: {
                return amount + "g of " + resource;
            }
            case CUPS: {
                return amount + " of " + resource;
            }
            case CASH: {
                return "$" + amount + " of " + resource;
            }
        }
        return "Unknown resource name!";
    }

    public void fill(int water, int milk, int coffee, int cups) {
        addWater(water);
        addMilk(milk);
        addCoffee(coffee);
        addCups(cups);
    }

    public int take() {
        int cashAmount = getCashAmount();
        resetCash();
        return cashAmount;
    }

    public Map remaining() {

        Map<String, Integer> map = new LinkedHashMap<>();
        map.put(WATER, this.getWaterCapacity());
        map.put(MILK, this.getMilkCapacity());
        map.put(COFFEE, this.getCoffeeCapacity());
        map.put(CUPS, this.getCupsAmount());
        map.put(CASH, this.getCashAmount());
        return map;
    }

    // return missing resource if purchase is not possible
    // return "" if purchase completed
    public String buy(CoffeeDrink coffeeDrink) {

        if (coffeeDrink.getWater() > this.getWaterCapacity()) {
            return WATER;
        }
        if (coffeeDrink.getMilk() > this.getMilkCapacity()) {
            return MILK;
        }
        if (coffeeDrink.getCoffee() > this.getCoffeeCapacity()) {
            return COFFEE;
        }
        if (this.getCupsAmount() <= 0) {
            return CUPS;
        }
        this.decreaseWater(coffeeDrink.getWater());
        this.decreaseMilk(coffeeDrink.getMilk());
        this.decreaseCoffee(coffeeDrink.getCoffee());
        this.decreaseCups(1);
        this.addCash(coffeeDrink.getPrice());
        return "";
    }


    public int getWaterCapacity() {
        return this.waterCapacity;
    }

    public int getMilkCapacity() {
        return this.milkCapacity;
    }

    public int getCoffeeCapacity() {
        return this.coffeeCapacity;
    }

    public int getCupsAmount() {
        return this.cupsAmount;
    }

    public int getCashAmount() {
        return this.cashAmount;
    }


    private void addWater(int water) {
        this.waterCapacity += water;
    }

    private void decreaseWater(int water) {
        this.waterCapacity -= water;
    }

    private void addMilk(int milk) {
        this.milkCapacity += milk;
    }

    private void decreaseMilk(int milk) {
        this.milkCapacity -= milk;
    }

    private void addCoffee(int coffee) {
        this.coffeeCapacity += coffee;
    }

    private void decreaseCoffee(int coffee) {
        this.coffeeCapacity -= coffee;
    }

    private void addCups(int cups) {
        this.cupsAmount += cups;
    }

    private void decreaseCups(int cups) {
        this.cupsAmount -= cups;
    }

    private void addCash(int cash) {
        this.cashAmount += cash;
    }

    private void resetCash() {
        this.cashAmount = 0;
    }
}