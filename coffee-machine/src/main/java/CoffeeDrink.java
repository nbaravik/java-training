public class CoffeeDrink {

    private int water;
    private int milk;
    private int coffee;
    private int price;

    CoffeeDrink(int water, int milk, int coffee, int price) {
        this.water = water;
        this.milk = milk;
        this.coffee = coffee;
        this.price = price;
    }

    public int getWater() {
        return this.water;
    }

    public int getMilk() {
        return this.milk;
    }

    public int getCoffee() {
        return this.coffee;
    }

    public int getPrice() {
        return this.price;
    }
}