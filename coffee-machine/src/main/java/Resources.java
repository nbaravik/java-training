public class Resources {

    // resource names
    public static final String WATER = "water";
    public static final String MILK = "milk";
    public static final String COFFEE = "coffee beans";
    public static final String SUGAR = "sugar";
    public static final String CUPS = "disposable cups";
    public static final String CASH = "money";

    // unit names
    public static final String MILLILITER = "ml";
    public static final String GRAM = "g";
    public static final String PIECE = "";
    public static final String DOLLAR = "$";

    public static Resource getWater(int amount) {
        Resource water = new Resource(WATER, amount, MILLILITER);
        return water;
    }

    public static Resource getMilk(int amount) {
        Resource milk = new Resource(MILK, amount, MILLILITER);
        return milk;
    }

    // specific formatRequest() for coffee resource
    public static Resource getCoffee(int amount) {
        Resource coffee = new Resource(COFFEE, amount, GRAM) {
            @Override
            public String formatRequest() {
                return "Write how many grams of " + getName() + " do you want to add:";
            }
        };
        return coffee;
    }

    // specific formatRequest() for sugar resource
    public static Resource getSugar(int amount) {
        Resource sugar = new Resource(SUGAR, amount, GRAM) {
            @Override
            public String formatRequest() {
                return "Write how many grams of " + getName() + " do you want to add:";
            }
        };
        return sugar;
    }

    // specific format() and formatRequest() for disposable cups resource
    public static Resource getCups(int amount) {
        Resource cups = new Resource(CUPS, amount, PIECE) {
            @Override
            public String format() {
                if (this.getAmount() == 1) {
                    return getAmount() + " disposable cup";
                }
                return getAmount() + " " + getName();
            }

            public String formatRequest() {
                return "Write how many " + getName() + " do you want to add:";
            }
        };
        return cups;
    }

    // specific format() for cash resource
    public static Resource getCash(int amount) {
        Resource cash = new Resource(CASH, amount, DOLLAR) {
            public String format() {
                return getUnit() + getAmount() + " of " + getName();
            }
        };
        return cash;
    }
}