import java.util.*;

public class Application {

    public static final String BUY = "buy";
    public static final String FILL = "fill";
    public static final String TAKE = "take";
    public static final String REMAINING = "remaining";
    public static final String BACK = "back";
    public static final String EXIT = "exit";

    public static final String DRINK_ONE = "1";
    public static final String DRINK_TWO = "2";
    public static final String DRINK_THREE = "3";
    public static final String DRINK_FOUR = "4";
    public static final String DRINK_FIVE = "5";

    public static final Map<String, CoffeeDrink> drinksMenu;

    static {
        drinksMenu = new LinkedHashMap();
        drinksMenu.put(DRINK_ONE, CoffeeDrinks.ESPRESSO);
        drinksMenu.put(DRINK_TWO, CoffeeDrinks.LATTE);
        drinksMenu.put(DRINK_THREE, CoffeeDrinks.CAPPUCCINO);
        drinksMenu.put(DRINK_FOUR, CoffeeDrinks.SWEET_CAPPUCCINO);
        drinksMenu.put(DRINK_FIVE, CoffeeDrinks.SWEET_LATTE);
    }

    public static void handleBuyCommand(CoffeeMachine coffeeMachine, Scanner scanner) {
        System.out.print("What do you want to buy? ");

        drinksMenu.forEach((num, drink) -> System.out.print( num + " - " + drink.getName() + ", "));
        System.out.println("back - to main menu:");
        String nextLine = scanner.nextLine().trim();

        if (BACK.equalsIgnoreCase(nextLine)) {
            return;
        }
        if (!drinksMenu.containsKey(nextLine)) {
            System.out.println("Unknown command cannot be executed!");
            return;
        }

        try {
            coffeeMachine.buy(drinksMenu.get(nextLine));
            System.out.println("I have enough resources, making you a coffee!");
        } catch (OutOfResourceException e) {
            System.out.println("Sorry, not enough " + e.getResourceName() + "!");
        }
    }

    public static void handleFillCommand(CoffeeMachine coffeeMachine, Scanner scanner) {

        List<Resource> stuffResourcesList = coffeeMachine.getStuffResources();
        Resource[] result = new Resource[stuffResourcesList.size()];
        int i = 0;
        try {
            for (Resource entry : stuffResourcesList) {
                System.out.println(entry.formatRequest());
                int amount = Integer.parseInt(scanner.nextLine().trim());
                result[i] = entry.withAmount(amount);
                i++;
            }
            coffeeMachine.fill(result);
        } catch (NumberFormatException numberFormatException) {
            System.out.println("Resource quantity must be a number. Command cannot be executed!");
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        CoffeeMachine coffeeMachine = new CoffeeMachine(
                Resources.getCash(550),

                Resources.getWater(400),
                Resources.getMilk(540),
                Resources.getCoffee(120),
                Resources.getSugar(50),
                Resources.getCups(9)
        );

        boolean continueFlag = true;
        while (continueFlag) {
            System.out.println("Write action (buy, fill, take, remaining, exit):");
            String nextLine = scanner.nextLine();
            switch (nextLine) {
                case EXIT: {
                    continueFlag = false;
                    break;
                }
                case BUY: {
                    handleBuyCommand(coffeeMachine, scanner);  //special case
                    break;
                }
                case FILL: {
                    handleFillCommand(coffeeMachine, scanner);  //special case
                    break;
                }
                case REMAINING: {
                    System.out.println("The coffee machine has:");
                    List<Resource> list = coffeeMachine.remaining();
                    for (Resource entry : list) {
                        System.out.println(entry.format());
                    }
                    break;
                }
                case TAKE: {
                    System.out.println("I gave you $" + coffeeMachine.take());
                    break;
                }
                default: {
                    System.out.println("Command cannot be executed!");
                }
            }
        }
    }
}