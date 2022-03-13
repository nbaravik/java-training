import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

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

    public static final Map<String, CoffeeDrink> drinksMenu;

    static {
        drinksMenu = new LinkedHashMap();
        drinksMenu.put(DRINK_ONE, CoffeeDrinks.ESPRESSO);
        drinksMenu.put(DRINK_TWO, CoffeeDrinks.LATTE);
        drinksMenu.put(DRINK_THREE, CoffeeDrinks.CAPPUCCINO);

    }

    public static boolean toBuyCommandMethod(CoffeeMachine coffeeMachine, Scanner scanner) {
        System.out.print("What do you want to buy? ");
        System.out.println("1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
        String nextLine = scanner.nextLine().trim();

        if (BACK.equalsIgnoreCase(nextLine)) {
            return true;
        }
        if (EXIT.equalsIgnoreCase(nextLine)) {
            return false;
        }

        if (drinksMenu.containsKey(nextLine)) {
            String missingResource = coffeeMachine.buy(drinksMenu.get(nextLine));
            if ("".equalsIgnoreCase(missingResource)) {
                System.out.println("I have enough resources, making you a coffee!");
            } else {
                System.out.println("Sorry, not enough " + missingResource + "!");
            }
        } else {
            System.out.println("Unknown command cannot be executed!");
        }
        return true;
    }

    public static boolean toFillCommandMethod(CoffeeMachine coffeeMachine, Scanner scanner) {

        try {
            System.out.println("Write how many ml of " + CoffeeMachine.WATER + " do you want to add:");
            int water = Integer.parseInt(scanner.nextLine().trim());
            System.out.println("Write how many ml of " + CoffeeMachine.MILK + " do you want to add:");
            int milk = Integer.parseInt(scanner.nextLine().trim());
            System.out.println("Write how many grams of " + CoffeeMachine.COFFEE + " do you want to add:");
            int coffee = Integer.parseInt(scanner.nextLine().trim());
            System.out.println("Write how many " + CoffeeMachine.CUPS + " do you want to add:");
            int cups = Integer.parseInt(scanner.nextLine().trim());
            coffeeMachine.fill(water, milk, coffee, cups);
        } catch (NumberFormatException numberFormatException) {
            System.out.println("Resource quantity must be a number. Command cannot be executed!");
        }
        return true;
    }

    public static boolean otherCommandsMethod(CoffeeMachine coffeeMachine, String command) {

        switch (command) {

            case REMAINING: {
                System.out.println("The coffee machine has:");
                Map<String, Integer> remaining = coffeeMachine.remaining();
                Iterator iterator = remaining.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, Integer> entry = (Map.Entry) iterator.next();
                    System.out.println(CoffeeMachine.resourceOutputFormat(entry.getKey(), entry.getValue()));
                }
                return true;
            }
            case TAKE: {
                System.out.println("I gave you $" + coffeeMachine.take());
                return true;
            }
            case EXIT: {
                return false;
            }
            default: {
                System.out.println("Command cannot be executed!");
            }
        }
        return true;
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        CoffeeMachine coffeeMachine = new CoffeeMachine(400, 540, 120, 9, 550);

        boolean continueFlag = true;
        while (continueFlag) {
            System.out.println("Write action (buy, fill, take, remaining, exit):");
            String nextLine = scanner.nextLine();
            switch (nextLine) {
                case BUY: {
                    continueFlag = toBuyCommandMethod(coffeeMachine, scanner);  //special case
                    break;
                }
                case FILL: {
                    continueFlag = toFillCommandMethod(coffeeMachine, scanner);  //special case
                    break;
                }
                default: {
                    continueFlag = otherCommandsMethod(coffeeMachine, nextLine.trim());
                }
            }
        }
    }
}