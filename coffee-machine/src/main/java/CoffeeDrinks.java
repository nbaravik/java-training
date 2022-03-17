public class CoffeeDrinks {

    public static final String ESPRESSO_NAME = "espresso";
    public static final String LATTE_NAME = "latte";
    public static final String CAPPUCCINO_NAME = "cappuccino";
    public static final String SWEET_LATTE_NAME = "sweet latte";
    public static final String SWEET_CAPPUCCINO_NAME = "sweet cappuccino";

    public static final CoffeeDrink ESPRESSO = new CoffeeDrink(
            ESPRESSO_NAME,
            Resources.getCash(4),

            Resources.getWater(250),
            Resources.getCoffee(16),
            Resources.getCups(1)
    );
    public static final CoffeeDrink LATTE = new CoffeeDrink(
            LATTE_NAME,
            Resources.getCash(7),

            Resources.getWater(350),
            Resources.getMilk(75),
            Resources.getCoffee(20),
            Resources.getCups(1)
    );
    public static final CoffeeDrink CAPPUCCINO = new CoffeeDrink(
            CAPPUCCINO_NAME,
            Resources.getCash(6),

            Resources.getWater(200),
            Resources.getMilk(100),
            Resources.getCoffee(12),
            Resources.getCups(1)
    );
    public static final CoffeeDrink SWEET_LATTE = new CoffeeDrink(
            SWEET_LATTE_NAME,
            Resources.getCash(7),

            Resources.getWater(350),
            Resources.getMilk(75),
            Resources.getCoffee(20),
            Resources.getSugar(10),
            Resources.getCups(1)
    );
    public static final CoffeeDrink SWEET_CAPPUCCINO = new CoffeeDrink(
            SWEET_CAPPUCCINO_NAME,
            Resources.getCash(6),

            Resources.getWater(200),
            Resources.getMilk(100),
            Resources.getCoffee(12),
            Resources.getSugar(10),
            Resources.getCups(1)
    );
}
