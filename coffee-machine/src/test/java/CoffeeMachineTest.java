import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

public class CoffeeMachineTest {

    @Test
    public void testResourceOutputFormat() {
        Assert.assertEquals("100ml of water", CoffeeMachine.resourceOutputFormat(CoffeeMachine.WATER, 100));
        Assert.assertEquals("100ml of milk", CoffeeMachine.resourceOutputFormat(CoffeeMachine.MILK, 100));
        Assert.assertEquals("100g of coffee beans", CoffeeMachine.resourceOutputFormat(CoffeeMachine.COFFEE, 100));
        Assert.assertEquals("10 of disposable cups", CoffeeMachine.resourceOutputFormat(CoffeeMachine.CUPS, 10));
    }

    @Test
    public void testFill() {

        CoffeeMachine coffeeMachine = new CoffeeMachine(100, 100, 100, 20, 10);
        coffeeMachine.fill(100, 100, 70, 5);
        Assert.assertEquals(200, coffeeMachine.getWaterCapacity());
        Assert.assertEquals(200, coffeeMachine.getMilkCapacity());
        Assert.assertEquals(170, coffeeMachine.getCoffeeCapacity());
        Assert.assertEquals(25, coffeeMachine.getCupsAmount());
    }

    @Test
    public void testTake() {

        CoffeeMachine coffeeMachine = new CoffeeMachine(100, 100, 100, 20, 10);
        Assert.assertEquals(10, coffeeMachine.getCashAmount());
        Assert.assertEquals(10, coffeeMachine.take());
        Assert.assertEquals(0, coffeeMachine.getCashAmount());
    }

    @Test
    public void testRemaining() {

        CoffeeMachine coffeeMachine = new CoffeeMachine(100, 100, 150, 20, 10);
        Map<String, Integer> remaining = new LinkedHashMap<>() {{
            put(CoffeeMachine.WATER, 100);
            put(CoffeeMachine.MILK, 100);
            put(CoffeeMachine.COFFEE, 150);
            put(CoffeeMachine.CUPS, 20);
            put(CoffeeMachine.CASH, 10);
        }};
        Assert.assertEquals(remaining, coffeeMachine.remaining());
    }

    @Test
    public void testBuy() {
        CoffeeMachine coffeeMachine = new CoffeeMachine(1000, 1000, 500, 20, 0);

        Assert.assertEquals("", coffeeMachine.buy(CoffeeDrinks.ESPRESSO));
        Assert.assertEquals(750, coffeeMachine.getWaterCapacity());
        Assert.assertEquals(1000, coffeeMachine.getMilkCapacity());
        Assert.assertEquals(484, coffeeMachine.getCoffeeCapacity());
        Assert.assertEquals(19, coffeeMachine.getCupsAmount());
        Assert.assertEquals(4, coffeeMachine.getCashAmount());

        Assert.assertEquals("", coffeeMachine.buy(CoffeeDrinks.LATTE));
        Assert.assertEquals(400, coffeeMachine.getWaterCapacity());
        Assert.assertEquals(925, coffeeMachine.getMilkCapacity());
        Assert.assertEquals(464, coffeeMachine.getCoffeeCapacity());
        Assert.assertEquals(18, coffeeMachine.getCupsAmount());
        Assert.assertEquals(11, coffeeMachine.getCashAmount());

        Assert.assertEquals("", coffeeMachine.buy(CoffeeDrinks.CAPPUCCINO));
        Assert.assertEquals(200, coffeeMachine.getWaterCapacity());
        Assert.assertEquals(825, coffeeMachine.getMilkCapacity());
        Assert.assertEquals(452, coffeeMachine.getCoffeeCapacity());
        Assert.assertEquals(17, coffeeMachine.getCupsAmount());
        Assert.assertEquals(17, coffeeMachine.getCashAmount());

        Assert.assertEquals(CoffeeMachine.WATER, coffeeMachine.buy(CoffeeDrinks.LATTE));
    }
}